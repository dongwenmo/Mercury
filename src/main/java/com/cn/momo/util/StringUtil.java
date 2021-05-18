package com.cn.momo.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.*;

/**
 * 对字符串操作的工具类
 *
 * @author dongwenmo
 * @create 2020-07-06 10:53
 */
public class StringUtil {
    // 判断字符串为空字符串或者是null
    public static boolean isNull(String s) {
        return (s == null || "".equals(s));
    }

    // 获取UUID
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    // 下划线命名转驼峰命名
    public static String getCamelCase(String world) {
        String[] worlds = world.split("_");
        String oWorld = "";
        for (int i = 0; i < worlds.length; i++) {
            if (i == 0) {
                oWorld = worlds[0];
            } else {
                String iWorld = worlds[i];
                if (iWorld.length() > 0) {
                    oWorld += iWorld.substring(0, 1).toUpperCase() + iWorld.substring(1);
                }
            }
        }
        return oWorld;
    }

    // 单词首字母大写
    public static String toUpperCaseFirst(String world) {
        int len = world.length();
        String oWorld = "";
        if (len > 0) {
            String first = world.substring(0, 1);
            String other = world.substring(1);
            oWorld = first.toUpperCase() + other;
        }
        return oWorld;
    }

    // 超级模糊查询
    public static String supperQueryLike(String world) {
        if (isNull(world)) {
            return "%";
        }
        String oWorld = "%";
        for (int i = 0; i < world.length(); i++) {
            oWorld += world.charAt(i) + "%";
        }
        return oWorld;
    }

    // 模糊查询
    public static String queryLike(String world) {
        String oWorld = "%";
        if (isNull(world)) {
            return "%";
        }
        return oWorld + world + oWorld;
    }

    // 获取中文汉字拼音 默认输出
    public static String getPinyin(String chinese) {
        return getPinyinZh_CN(makeStringByStringSet(chinese));
    }

    // 拼音大写输出
    public static String getPinyinToUpperCase(String chinese) {
        return getPinyinZh_CN(makeStringByStringSet(chinese)).toUpperCase();
    }

    // 拼音小写输出
    public static String getPinyinToLowerCase(String chinese) {
        return getPinyinZh_CN(makeStringByStringSet(chinese)).toLowerCase();
    }

    // 拼音简拼输出
    public static String getPinyinJianPin(String chinese) {
        return getPinyinConvertJianPin(getPinyin(chinese));
    }

    // 首字母大写输出
    public static String getPinyinFirstToUpperCase(String chinese) {
        return getPinyin(chinese);
    }

    // 字符集转换
    public static Set<String> makeStringByStringSet(String chinese) {
        char[] chars = chinese.toCharArray();
        if (chinese != null && !chinese.trim().equalsIgnoreCase("")) {
            char[] srcChar = chinese.toCharArray();
            String[][] temp = new String[chinese.length()][];
            for (int i = 0; i < srcChar.length; i++) {
                char c = srcChar[i];

                // 是中文或者a-z或者A-Z转换拼音
                if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {

                    try {
                        temp[i] = PinyinHelper.toHanyuPinyinStringArray(
                                chars[i], getDefaultOutputFormat());

                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else if (((int) c >= 65 && (int) c <= 90)
                        || ((int) c >= 97 && (int) c <= 122)) {
                    temp[i] = new String[]{String.valueOf(srcChar[i])};
                } else {
                    temp[i] = new String[]{""};
                }
            }
            String[] pingyinArray = Exchange(temp);
            Set<String> zhongWenPinYin = new HashSet<String>();
            for (int i = 0; i < pingyinArray.length; i++) {
                zhongWenPinYin.add(pingyinArray[i]);
            }
            return zhongWenPinYin;
        }
        return null;
    }

    // 默认输出格式
    public static HanyuPinyinOutputFormat getDefaultOutputFormat() {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 没有音调数字
        format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);// u显示
        return format;
    }

    /***************************************************************************
     *
     * @Name: Pinyin4jUtil.java
     * @Description: TODO
     * @author: wang_chian@foxmail.com
     * @version: Jan 13, 2012 9:39:54 AM
     * @param strJaggedArray
     * @return
     */
    public static String[] Exchange(String[][] strJaggedArray) {
        String[][] temp = DoExchange(strJaggedArray);
        return temp[0];
    }

    /***************************************************************************
     *
     * @Name: Pinyin4jUtil.java
     * @Description: TODO
     * @author: wang_chian@foxmail.com
     * @version: Jan 13, 2012 9:39:47 AM
     * @param strJaggedArray
     * @return
     */
    private static String[][] DoExchange(String[][] strJaggedArray) {
        int len = strJaggedArray.length;
        if (len >= 2) {
            int len1 = strJaggedArray[0].length;
            int len2 = strJaggedArray[1].length;
            int newlen = len1 * len2;
            String[] temp = new String[newlen];
            int Index = 0;
            for (int i = 0; i < len1; i++) {
                for (int j = 0; j < len2; j++) {
                    temp[Index] = capitalize(strJaggedArray[0][i])
                            + capitalize(strJaggedArray[1][j]);
                    Index++;
                }
            }
            String[][] newArray = new String[len - 1][];
            for (int i = 2; i < len; i++) {
                newArray[i - 1] = strJaggedArray[i];
            }
            newArray[0] = temp;
            return DoExchange(newArray);
        } else {
            return strJaggedArray;
        }
    }

    /***************************************************************************
     * 首字母大写
     *
     * @Name: Pinyin4jUtil.java
     * @Description: TODO
     * @author: wang_chian@foxmail.com
     * @version: Jan 13, 2012 9:36:18 AM
     * @param s
     * @return
     */
    public static String capitalize(String s) {
        char ch[];
        ch = s.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        String newString = new String(ch);
        return newString;
    }

    /***************************************************************************
     * 字符串集合转换字符串(逗号分隔)
     *
     * @Name: Pinyin4jUtil.java
     * @Description: TODO
     * @author: wang_chian@foxmail.com
     * @version: Jan 13, 2012 9:37:57 AM
     * @param stringSet
     * @return
     */
    public static String getPinyinZh_CN(Set<String> stringSet) {
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (String s : stringSet) {
            if (i == stringSet.size() - 1) {
                str.append(s);
            } else {
                str.append(s + ",");
            }
            i++;
        }
        return str.toString();
    }

    /***************************************************************************
     * 获取每个拼音的简称
     *
     * @Name: Pinyin4jUtil.java
     * @Description: TODO
     * @author: wang_chian@foxmail.com
     * @version: Jan 13, 2012 11:05:58 AM
     * @param chinese
     * @return
     */
    public static String getPinyinConvertJianPin(String chinese) {
        String[] strArray = chinese.split(",");
        String strChar = "";
        for (String str : strArray) {
            char arr[] = str.toCharArray(); // 将字符串转化成char型数组
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] >= 65 && arr[i] < 91) { // 判断是否是大写字母
                    strChar += new String(arr[i] + "");
                }
            }
            strChar += ",";
        }
        return strChar;
    }

    // 打印int型数组
    public static String printArray(int[] array) {
        String output = "";
        for (int i = 0; i < array.length; i++) {
            output += "," + array[i];
        }
        if (output.length() > 0) {
            return output.substring(1);
        }
        return "";
    }

    // 获取字符串中相同字符的个数
    public static Map<String, Integer> sameCharNum(String text) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            String ch = c + "";
            if (map.containsKey(ch)) {
                map.put(ch, map.get(ch) + 1);
            } else {
                map.put(ch, 1);
            }
        }
        return map;
    }

    // 获取字符串中指定字符的个数
    public static int sameCharNumByChar(String text, String c) {
        Map<String, Integer> map = sameCharNum(text);
        if (map.containsKey(c)) {
            return map.get(c);
        }
        return 0;
    }

    /**
     * 获取项目路径
     * dongwenmo 2021-03-15
     */
    public static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    /**
     * 去除字符串后空格
     * dongwenmo 2021-03-24
     */
    public static String trimSuffix(String text) {
        String str = "";
        if (!isNull(text)) {
            int num = 0;
            for (int i = text.length() - 1; i >= 0; i--) {
                String c = text.charAt(i) + "";
                if (" \n\t".contains(c)) {
                    num++;
                } else {
                    break;
                }
            }
            str = text.substring(0, text.length() - num);
        }
        return str;
    }

    public static String repeatStr(String str, int count) {
        if (str == null) {
            str = "";
        }
        if (count < 0) {
            count = 0;
        }
        String s = "";
        for (int i = 0; i < count; i++) {
            s += str;
        }
        return s;
    }
}
