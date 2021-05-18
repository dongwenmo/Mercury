package com.cn.momo.util;

import com.cn.momo.exception.BusinessException;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

import java.io.*;

/**
 * 文件操作工具
 * dongwenmo 2021-01-07
 */
public class FileUtil {
    /**
     * @param dirPath: 文件夹磁盘路径
     * @desc: 判断指定路径文件夹是否存在, 若不存在则创建新的文件夹
     */
    public static void isChartPathExist(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 判断文件是否存在
     * dongwenmo 2021-04-17
     */
    public static boolean isExists(String path) {
        File f = new File(path);
        return f.exists();
    }

    /**
     * 删除文件或文件夹
     */
    public static void deleteIfExists(File file) {
        if (file.exists()) {
            if (!file.isFile()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {
                    for (File temp : files) {
                        deleteIfExists(temp);
                    }
                }
            }
            if (!file.delete()) {
            }
        }
    }

    /**
     * 删除文件或文件夹
     */
    public static void deleteIfExists(String path) {
        deleteIfExists(new File(path));
    }

    /**
     * 保存文件到本地
     * dongwenmo 2021-03-15
     */
    public static void saveFile(InputStream inputStream, String fileName, String path) throws BusinessException {
        OutputStream os = null;
        try {
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(path);
            if (!tempFile.exists()) {
                if (!tempFile.mkdirs()) {
                    throw new BusinessException("创建文件路径失败");
                }
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (Exception e) {
            throw new BusinessException("存储文件异常：" + e.getMessage());
        } finally {
            // 完毕，关闭所有链接
            try {
                if (os != null) {
                    os.close();
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取html文件的charset
     * dongwenmo 2021-03-17
     */
    public static String getCharsetByHtml(String path) {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            while (s != null)//如果当前行不为空
            {
                s = s.toLowerCase();
                if (s.contains("gbk")) {
                    return "gbk";
                } else if (s.contains("utf")) {
                    return "utf";
                } else if (s.contains("gb2312")) {
                    return "gbk";
                }
                s = br.readLine();//读取下一行
            }
            br.close();//关闭BufferReader流
            fr.close(); //关闭文件流
        } catch (IOException e) {
            System.out.println("指定文件不存在");//处理异常
        }
        return "";
    }

    /**
     * word转换为pdf
     * dongwenmo 2021-04-17
     */
    public static void word2pdf(String wordFile, String pdfFile) {//wordFile word 的路径  //pdfFile pdf 的路径
        ActiveXComponent app = null;
        System.out.println("开始转换...");
        // 开始时间
        // long start = System.currentTimeMillis();
        try {
            // 打开word
            app = new ActiveXComponent("Word.Application");
            // 获得word中所有打开的文档
            Dispatch documents = app.getProperty("Documents").toDispatch();
            System.out.println("打开文件: " + wordFile);
            // 打开文档
            Dispatch document = Dispatch.call(documents, "Open", wordFile, false, true).toDispatch();
            // 如果文件存在的话，不会覆盖，会直接报错，所以我们需要判断文件是否存在
            File target = new File(pdfFile);
            if (target.exists()) {
                target.delete();
            }
            System.out.println("另存为: " + pdfFile);
            Dispatch.call(document, "SaveAs", pdfFile, 17);
            // 关闭文档
            Dispatch.call(document, "Close", false);
        } catch (Exception e) {
            System.out.println("转换失败" + e.getMessage());
        } finally {
            // 关闭office
            if(app != null){
                app.invoke("Quit", 0);
            }
        }
    }

}
