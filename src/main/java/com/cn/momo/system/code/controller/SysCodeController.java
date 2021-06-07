package com.cn.momo.system.code.controller;

import com.cn.momo.annotation.CallLog;
import com.cn.momo.system.code.pojo.SysCode;
import com.cn.momo.system.code.service.ISysCodeService;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.user.pojo.User;
import com.cn.momo.util.CheckUtil;
import com.cn.momo.util.ResultUtil;
import com.cn.momo.util.StringUtil;
import com.cn.momo.util.TransUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * auto 2021-02-02 09:58:16
 */
@Controller
@CrossOrigin("*")
@RequestMapping("/sysCode")
public class SysCodeController {
    private static Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Autowired
    private ISysCodeService iSysCodeService;

    @PostMapping("/select")
    @ResponseBody
    @CallLog(name = "select", desc = "根据实体中的属性值进行查询，查询条件使用等号")
    public String select(User user, SysCode sysCode) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (sysCode == null) {
                throw new BusinessException("查询条件不能为空");
            }
            List<SysCode> sysCodes = iSysCodeService.select(sysCode);
            if (!StringUtil.isNull(sysCode.getCodeGroup()) && !StringUtil.isNull(sysCode.getCodeKey())) {
                sysCodes = iSysCodeService.sort(sysCodes);
            }
            map.put("sysCodes", sysCodes);
            map.put("flag", 0);
            map.put("msg", "");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/selectByPrimaryKey")
    @ResponseBody
    @CallLog(name = "selectByPrimaryKey", desc = "根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号")
    public String selectByPrimaryKey(User user, Integer key) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (key == null) {
                throw new BusinessException("字段key不能为空");
            }
            SysCode sysCode = iSysCodeService.selectByPrimaryKey(key);
            map.put("sysCode", sysCode);
            map.put("flag", 0);
            map.put("msg", "");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/selectAll")
    @ResponseBody
    @CallLog(name = "selectAll", desc = "查询全部结果")
    public String selectAll(User user) {
        HashMap<String, Object> map = new HashMap<>();
        List<SysCode> sysCodes = iSysCodeService.selectAll();
        map.put("sysCodes", sysCodes);
        map.put("flag", 0);
        map.put("msg", "");
        return gson.toJson(map);
    }

    @PostMapping("/selectOne")
    @ResponseBody
    @CallLog(name = "selectOne", desc = "根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号")
    public String selectOne(User user, SysCode sysCode) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (sysCode == null) {
                throw new BusinessException("查询条件不能为空");
            }
            sysCode = iSysCodeService.selectOne(sysCode);
            map.put("sysCode", sysCode);
            map.put("flag", 0);
            map.put("msg", "");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/selectCount")
    @ResponseBody
    @CallLog(name = "selectCount", desc = "根据实体中的属性查询总数，查询条件使用等号")
    public String selectCount(User user, SysCode sysCode) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (sysCode == null) {
                throw new BusinessException("查询条件不能为空");
            }
            int count = iSysCodeService.selectCount(sysCode);
            map.put("count", count);
            map.put("flag", 0);
            map.put("msg", "");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/insert")
    @ResponseBody
    @CallLog(name = "insert", desc = "保存一个实体，null的属性也会保存，不会使用数据库默认值")
    public String insert(User user, SysCode sysCode) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (sysCode == null) {
                throw new BusinessException("新增对象不能为空");
            }
            iSysCodeService.insert(sysCode);
            map.put("sysCode", sysCode);
            map.put("flag", 0);
            map.put("msg", "新增成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/insertSelective")
    @ResponseBody
    @CallLog(name = "insertSelective", desc = "保存一个实体，null的属性不会保存，会使用数据库默认值")
    public String insertSelective(User user, SysCode sysCode) {
        HashMap<String, Object> map = new HashMap<>();
        try {

            CheckUtil.isNull(sysCode.getCodeGroup(), "组");
            CheckUtil.isNull(sysCode.getCodeKey(), "键");
            CheckUtil.isNull(sysCode.getName(), "参数名");
            CheckUtil.isNull(sysCode.getCodeValue(), "值");
            CheckUtil.isNull(sysCode.getContent(), "显示内容");

            SysCode vCode = new SysCode();
            vCode.setCodeGroup(sysCode.getCodeGroup());
            vCode.setCodeKey(sysCode.getCodeKey());
            int count = iSysCodeService.selectCount(vCode);

            sysCode.setPriority(count + 1);

            iSysCodeService.insertSelective(sysCode);
            map.put("sysCode", sysCode);
            map.put("flag", 0);
            map.put("msg", "新增成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/updateByPrimaryKey")
    @ResponseBody
    @CallLog(name = "updateByPrimaryKey", desc = "根据主键更新实体全部字段，null值会被更新")
    public String updateByPrimaryKey(User user, SysCode sysCode) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (sysCode == null) {
                throw new BusinessException("更新对象不能为空");
            }
            int count = iSysCodeService.updateByPrimaryKey(sysCode);
            map.put("count", count);
            map.put("flag", 0);
            map.put("msg", "更新成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/updateByPrimaryKeySelective")
    @ResponseBody
    @CallLog(name = "updateByPrimaryKeySelective", desc = "根据主键更新属性不为null的值")
    public String updateByPrimaryKeySelective(User user, SysCode sysCode) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (sysCode == null) {
                throw new BusinessException("更新对象不能为空");
            }
            int count = iSysCodeService.updateByPrimaryKeySelective(sysCode);
            map.put("count", count);
            map.put("flag", 0);
            map.put("msg", "更新成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/delete")
    @ResponseBody
    @CallLog(name = "delete", desc = "根据实体属性作为条件进行删除，查询条件使用等号")
    public String delete(User user, SysCode sysCode) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (sysCode == null) {
                throw new BusinessException("删除条件不能为空");
            }
            int count = iSysCodeService.delete(sysCode);
            map.put("count", count);
            map.put("flag", 0);
            map.put("msg", "删除成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/deleteByPrimaryKey")
    @ResponseBody
    @CallLog(name = "deleteByPrimaryKey", desc = "根据主键字段进行删除，方法参数必须包含完整的主键属性")
    public String deleteByPrimaryKey(User user, Integer key) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (key == null) {
                throw new BusinessException("主键不能为空");
            }
            int count = iSysCodeService.deleteByPrimaryKey(key);
            map.put("count", count);
            map.put("flag", 0);
            map.put("msg", "删除成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/deleteByPrimaryKeys")
    @ResponseBody
    @CallLog(name = "deleteByPrimaryKeys", desc = "根据主键字段进行删除，方法参数必须包含完整的主键属性（批量删除）")
    public String deleteByPrimaryKeys(User user, String keys) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (StringUtil.isNull(keys)) {
                throw new BusinessException("主键不能为空");
            }
            int[] key = TransUtil.string2ints(keys);
            int count = 0;
            for (int i : key) {
                count += iSysCodeService.deleteByPrimaryKey(i);
            }
            map.put("count", count);
            map.put("flag", 0);
            map.put("msg", "删除成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/getSysCodeNames")
    @ResponseBody
    @CallLog(name = "getSysCodeNames", desc = "获取数据字典名称列表")
    public String getSysCodeNames(User user, SysCode sysCode) throws BusinessException {
        ResultUtil result = new ResultUtil();
        result.put("sysCodeNames", iSysCodeService.getSysCodeNames(sysCode));
        return result.success();
    }

    @PostMapping("/getGroups")
    @ResponseBody
    @CallLog(name = "getGroups", desc = "获取数据字典组列表")
    public String getGroups(User user) throws BusinessException {
        ResultUtil result = new ResultUtil();
        result.put("codeGroups", iSysCodeService.getGroups());
        return result.success();
    }

    @PostMapping("/move")
    @ResponseBody
    @CallLog(name = "move", desc = "数据字典移动")
    public String move(User user, Integer codeId, String moveType) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            CheckUtil.isNull(codeId, "字典id");
            CheckUtil.isIn(moveType, "移动类型", "top", "bottom", "up", "down");
            iSysCodeService.move(codeId, moveType);
            map.put("flag", 0);
            map.put("msg", "移动成功");
        } catch (BusinessException e) {
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

}
