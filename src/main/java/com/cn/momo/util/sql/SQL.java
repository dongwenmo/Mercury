package com.cn.momo.util.sql;

import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.dbms.mapper.DbmsDatasourceMapper;
import com.cn.momo.util.StringUtil;
import com.cn.momo.util.sql.config.DBConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

/**
 * dongwenmo 2021-05-31
 */
public class SQL {
    private static Map<String, DBConfig> dsCache = new HashMap<>();
    private StringBuffer sqlBF;
    private DBConfig dataSource;
    private List<Object> paras;

    private Connection conn;
    private PreparedStatement ptmt;
    private ResultSet rs;

    public SQL() {
        this.sqlBF = new StringBuffer();
        this.paras = new ArrayList<>();
        this.dataSource = DBConfig.LOCALHOST;
    }

    public SQL(DBConfig dbConfig) {
        this.sqlBF = new StringBuffer();
        this.paras = new ArrayList<>();
        this.dataSource = dbConfig;
    }

    // 添加sql
    public void addSql(String sqlstmt) {
        if (sqlstmt != null) {
            this.sqlBF.append(sqlstmt);
        }
    }

    // 清除
    public void clear() {
        this.clearSql();
        this.clearPara();
    }

    // 清除sql
    public void clearSql() {
        this.sqlBF.setLength(0);
    }

    // 清除参数
    public void clearPara() {
        this.paras = new ArrayList<>();
    }

    // 设置参数
    public void setPara(Object... args) {
        this.paras.addAll(Arrays.asList(args));
    }

    // 查询
    public List<Map<String, Object>> query() throws BusinessException {
        List<Map<String, Object>> queryList = new ArrayList<>();
        // 创建连接
        createConn();
        // 设置参数
        setParas();
        // 执行查询
        try {
            rs = ptmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            List<String> columnNames = new ArrayList<>();
            List<String> columnTypes = new ArrayList<>();
            for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                String columnName = rsmd.getColumnLabel(i);
                if ("oracle.jdbc.OracleDriver".equals(dataSource.getDriver())) {
                    columnName = columnName.toLowerCase();
                }
                columnNames.add(columnName);
                columnTypes.add(rsmd.getColumnTypeName(i).toLowerCase());
//				System.out.println(rsmd.getColumnLabel(i) + "  " + rsmd.getColumnTypeName(i).toLowerCase());
            }

            // 展开结果集数据库
            while (rs.next()) {
                // 输出数据
                Map<String, Object> requestMap = new HashMap<>();
                for (int i = 0; i < columnNames.size(); i++) {
                    String columnName = columnNames.get(i);
                    String columnType = columnTypes.get(i);

                    if ("varchar".equals(columnType)) {
                        requestMap.put(columnName, rs.getString(columnName));
                    } else if ("int".equals(columnType)) {
                        requestMap.put(columnName, rs.getInt(columnName));
                    } else if ("datetime".equals(columnType)) {
                        requestMap.put(columnName, rs.getTimestamp(columnName));
                    } else if ("double".equals(columnType)) {
                        requestMap.put(columnName, rs.getDouble(columnName));
                    } else if ("char".equals(columnType)) {
                        requestMap.put(columnName, rs.getString(columnName));
                    } else if ("varchar2".equals(columnType)) {
                        requestMap.put(columnName, rs.getString(columnName));
                    } else if ("number".equals(columnType)) {
                        requestMap.put(columnName, rs.getInt(columnName));
                    } else if ("date".equals(columnType)) {
                        requestMap.put(columnName, rs.getTimestamp(columnName));
                    } else if ("mediumblob".equals(columnType)) {
                        requestMap.put(columnName, rs.getBlob(columnName));
                    } else if ("decimal".equals(columnType)) {
                        requestMap.put(columnName, rs.getInt(columnName));
                    } else if ("longtext".equals(columnType)) {
                        requestMap.put(columnName, rs.getString(columnName));
                    } else if ("mediumtext".equals(columnType)) {
                        requestMap.put(columnName, rs.getString(columnName));
                    } else if ("bigint".equals(columnType)) {
                        requestMap.put(columnName, rs.getInt(columnName));
                    }
                }
                queryList.add(requestMap);
            }
        } catch (SQLException throwables) {
            throw new BusinessException(5, "查询失败[" + throwables.getSQLState() + "]：" + throwables.getMessage());
        } finally {
            // 关闭资源
            close();
        }

        return queryList;
    }

    public int update() throws BusinessException {
        int count;
        // 创建连接
        createConn();
        // 设置参数
        setParas();

        // 增删改
        try {
            count = ptmt.executeUpdate();
        } catch (SQLException throwables) {
            throw new BusinessException(6, "增删改失败[" + throwables.getSQLState() + "]：" + throwables.getMessage());
        } finally {
            close();
        }
        return count;
    }

    // 创建连接
    private void createConn() throws BusinessException {
        // 注册 JDBC 驱动
        try {
            Class.forName(dataSource.getDriver());
        } catch (ClassNotFoundException e) {
            throw new BusinessException(1, "注册JDBC驱动失败：" + e.getMessage());
        }

        // 打开链接
        String url = "jdbc:mysql://" + dataSource.getUrl() + "?Unicode=true&characterEncoding=UTF-8&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";
        if ("oracle.jdbc.OracleDriver".equals(dataSource.getDriver())) {
            url = dataSource.getUrl();
        }

        try {
            conn = DriverManager.getConnection(url, dataSource.getUser(), dataSource.getPass());
            ptmt = conn.prepareStatement(sqlBF.toString());
        } catch (SQLException throwables) {
            throw new BusinessException(2, "创建数据库连接失败[" + throwables.getSQLState() + "]：" + throwables.getMessage());
        }
    }

    // 设置参数
    private void setParas() throws BusinessException {
        for (int i = 0; i < paras.size(); i++) {
            try {
                ptmt.setObject(i + 1, paras.get(i));
            } catch (SQLException throwables) {
                throw new BusinessException(4, "设置参数错误[" + throwables.getSQLState() + "]：" + throwables.getMessage());
            }
        }
    }

    private void close() throws BusinessException {
        try {
            if (ptmt != null) {
                ptmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException se) {
            throw new BusinessException(6, "关闭资源失败[" + se.getSQLState() + "]：" + se.getMessage());
        }
    }
}
