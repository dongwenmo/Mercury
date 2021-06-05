package com.cn.momo.system.code.service.impl;

import com.cn.momo.common.BaseServiceImpl;
import com.cn.momo.util.sql.config.DBConfig;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.code.mapper.SysCodeMapper;
import com.cn.momo.system.code.pojo.SysCode;
import com.cn.momo.system.code.service.ISysCodeService;
import com.cn.momo.util.DBUtil;
import com.cn.momo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * auto 2021-02-02 09:58:16
 */
@Service
public class SysCodeServiceImpl extends BaseServiceImpl<SysCode> implements ISysCodeService {
    @Autowired
    private SysCodeMapper sysCodeMapper;

    @Override
    public List<Map<String, Object>> getSysCodeNames(SysCode sysCode) {
        StringBuffer sqlBF = new StringBuffer();
        List<Map<String, Object>> list;

        String codeKey = sysCode.getCodeKey();
        String codeGroup = sysCode.getCodeGroup();

        sqlBF.setLength(0);
        sqlBF.append("  select code_group codeGroup,code_key codeKey,name  ");
        sqlBF.append("    from sys_code                                    ");
        sqlBF.append("   where code_key like ?                             ");
        sqlBF.append("     and code_group like ?                           ");
        sqlBF.append("   group by code_group,code_key,name                 ");
        sqlBF.append("   order by code_key,code_group                      ");

        list = DBUtil.query(DBConfig.LOCALHOST, sqlBF.toString(),
                StringUtil.supperQueryLike(codeKey), StringUtil.queryLike(codeGroup));

        return list;
    }

    @Override
    public List<String> getGroups() {
        List<String> list = new ArrayList<>();
        StringBuffer sqlBF = new StringBuffer();

        sqlBF.setLength(0);
        sqlBF.append("  select distinct code_group from sys_code order by code_group  ");

        List<Map<String, Object>> requestList = DBUtil.query(DBConfig.LOCALHOST, sqlBF.toString());
        for (Map<String, Object> i : requestList) {
            list.add((String) i.get("code_group"));
        }

        return list;
    }

    @Override
    public void move(int codeId, String moveType) throws BusinessException {
        SysCode sysCode = sysCodeMapper.selectByPrimaryKey(codeId);
        SysCode vCode = new SysCode();
        vCode.setCodeGroup(sysCode.getCodeGroup());
        vCode.setCodeKey(sysCode.getCodeKey());
        List<SysCode> list = sort(sysCodeMapper.select(vCode));

        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (sysCode.getCodeId().equals(list.get(i).getCodeId())) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new BusinessException("获取元素位置失败，请重试");
        }


        if ("top".equals(moveType)) {
            list.remove(index);
            list.add(0, sysCode);
        } else if ("bottom".equals(moveType)) {
            list.remove(index);
            list.add(sysCode);
        } else if ("up".equals(moveType)) {
            if (index == 0) {
                throw new BusinessException("移动失败：已在顶部");
            }
            SysCode temp = list.get(index);
            list.set(index, list.get(index - 1));
            list.set(index - 1, temp);
        } else if ("down".equals(moveType)) {
            if (index == list.size() - 1) {
                throw new BusinessException("移动失败：已在底部");
            }
            SysCode temp = list.get(index);
            list.set(index, list.get(index + 1));
            list.set(index + 1, temp);
        }

        for(int i=0;i<list.size();i++){
            if(!list.get(i).getPriority().equals(i+1)){
                list.get(i).setPriority(i+1);
                sysCodeMapper.updateByPrimaryKeySelective(list.get(i));
            }
        }
    }

    @Override
    public List<SysCode> sort(List<SysCode> list) {
        int len = list.size();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - 1 - i; j++) {
                if (list.get(j).getPriority() > list.get(j + 1).getPriority()) {
                    SysCode temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        return list;
    }
}
