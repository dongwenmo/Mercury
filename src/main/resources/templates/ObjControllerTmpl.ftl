package com.cn.momo.${path1}.controller;

import com.cn.momo.annotation.CallLog;
import com.cn.momo.annotation.Permission;
import com.cn.momo.${path1}.pojo.${className};
import com.cn.momo.${path1}.service.I${className}Service;
import com.cn.momo.util.CheckUtil;
import com.cn.momo.util.ResultUtil;
import com.cn.momo.util.TransUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * auto ${curTime}
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/${className?uncap_first}")
public class ${className}Controller {
    @Autowired
    private I${className}Service i${className}Service;
    
    @Permission
    @PostMapping("/select")
    @CallLog(name = "select", desc = "根据实体中的属性值进行查询，查询条件使用等号")
    public String select(${className} ${className?uncap_first}) {
        ResultUtil result = new ResultUtil();
        result.put("${className?uncap_first}s", i${className}Service.select(${className?uncap_first}));
        return result.success();
    }
    
    @Permission
    @PostMapping("/selectByPrimaryKey")
    @CallLog(name = "selectByPrimaryKey", desc = "根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号")
    public String selectByPrimaryKey(Integer key) throws Exception {
        ResultUtil result = new ResultUtil();
        CheckUtil.isNull(key, "主键");
        result.put("${className?uncap_first}", i${className}Service.selectByPrimaryKey(key));
        return result.success();
    }
    
    @Permission
    @PostMapping("/selectAll")
    @CallLog(name = "selectAll", desc = "查询全部结果")
    public String selectAll() {
        ResultUtil result = new ResultUtil();
        result.put("${className?uncap_first}s", i${className}Service.selectAll());
        return result.success();
    }
    
    @Permission
    @PostMapping("/selectOne")
    @CallLog(name = "selectOne", desc = "根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号")
    public String selectOne(${className} ${className?uncap_first}) {
        ResultUtil result = new ResultUtil();
        result.put("${className?uncap_first}", i${className}Service.selectOne(${className?uncap_first}));
        return result.success();
    }
    
    @Permission
    @PostMapping("/selectCount")
    @CallLog(name = "selectCount", desc = "根据实体中的属性查询总数，查询条件使用等号")
    public String selectCount(${className} ${className?uncap_first}) {
        ResultUtil result = new ResultUtil();
        result.put("${className?uncap_first}s", i${className}Service.selectCount(${className?uncap_first}));
        return result.success();
    }
    
    @Permission
    @PostMapping("/insert")
    @CallLog(name = "insert", desc = "保存一个实体，null的属性也会保存，不会使用数据库默认值")
    public String insert(${className} ${className?uncap_first}) {
        ResultUtil result = new ResultUtil();
        i${className}Service.insert(${className?uncap_first});
        return result.success("新增成功");
    }
    
    @Permission
    @PostMapping("/insertSelective")
    @CallLog(name = "insertSelective", desc = "保存一个实体，null的属性不会保存，会使用数据库默认值")
    public String insertSelective(${className} ${className?uncap_first}) {
        ResultUtil result = new ResultUtil();
        i${className}Service.insertSelective(${className?uncap_first});
        return result.success("新增成功");
    }
    
    @Permission
    @PostMapping("/updateByPrimaryKey")
    @CallLog(name = "updateByPrimaryKey", desc = "根据主键更新实体全部字段，null值会被更新")
    public String updateByPrimaryKey(${className} ${className?uncap_first}) {
        ResultUtil result = new ResultUtil();
        i${className}Service.updateByPrimaryKey(${className?uncap_first});
        return result.success("更新成功");
    }
    
    @Permission
    @PostMapping("/updateByPrimaryKeySelective")
    @CallLog(name = "updateByPrimaryKeySelective", desc = "根据主键更新属性不为null的值")
    public String updateByPrimaryKeySelective(${className} ${className?uncap_first}) {
        ResultUtil result = new ResultUtil();
        i${className}Service.updateByPrimaryKeySelective(${className?uncap_first});
        return result.success("更新成功");
    }
    
    @Permission
    @PostMapping("/delete")
    @CallLog(name = "delete", desc = "根据实体属性作为条件进行删除，查询条件使用等号")
    public String delete(${className} ${className?uncap_first}) {
        ResultUtil result = new ResultUtil();
        i${className}Service.delete(${className?uncap_first});
        return result.success("删除成功");
    }
    
    @Permission
    @PostMapping("/deleteByPrimaryKey")
    @CallLog(name = "deleteByPrimaryKey", desc = "根据主键字段进行删除，方法参数必须包含完整的主键属性")
    public String deleteByPrimaryKey(Integer key) throws Exception {
        ResultUtil result = new ResultUtil();
        CheckUtil.isNull(key, "主键");
        i${className}Service.deleteByPrimaryKey(key);
        return result.success("删除成功");
    }
    
    @Permission
    @PostMapping("/deleteByPrimaryKeys")
    @CallLog(name = "deleteByPrimaryKeys", desc = "根据主键字段进行删除，方法参数必须包含完整的主键属性（批量删除）")
    public String deleteByPrimaryKeys(String keys) throws Exception {
        ResultUtil result = new ResultUtil();
        CheckUtil.isNull(keys, "主键");
        int[] key = TransUtil.string2ints(keys);
        int count = 0;
        for (int i : key) {
            count += i${className}Service.deleteByPrimaryKey(i);
        }
        result.put("count", count);
        return result.success("删除成功");
    }
}
