package com.cn.momo.util;

import com.cn.momo.config.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dongwenmo
 * @create 2020-07-20 11:47
 */
public class DBUtil {

	// 查询的通用方法
	public static List<Map<String, Object>> query(DBConfig db, String sql, Object... args) {
		List<Map<String, Object>> requestList = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 注册 JDBC 驱动
			Class.forName(db.getDriver());

			// 打开链接
			String url = "jdbc:mysql://" + db.getUrl() + "?Unicode=true&characterEncoding=UTF-8&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";

			if("oracle.jdbc.OracleDriver".equals(db.getDriver())){
				url = db.getUrl();
			}

			conn = DriverManager.getConnection(url, db.getUser(), db.getPass());

			// 执行查询
			stmt = conn.createStatement();

			//预编译SQL，减少sql执行
			PreparedStatement ptmt = conn.prepareStatement(sql);
			//传参
			/* 有可能有参数 */
			for (int i = 0; i < args.length; i++) {
				ptmt.setObject(i + 1, args[i]);
			}
			//执行
			rs = ptmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			List<String> columnNames = new ArrayList<>();
			List<String> columnTypes = new ArrayList<>();
			for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
				String columnName = rsmd.getColumnLabel(i);
				if("oracle.jdbc.OracleDriver".equals(db.getDriver())){
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
				requestList.add(requestMap);
			}
		} catch (SQLException se) {
			// 处理 JDBC 错误
			se.printStackTrace();
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		}  finally {
			// 关闭资源
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return requestList;
	}

	// 增删改的通用方法
	public static int update(DBConfig db, String sql, Object... args) {
		int count = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName(db.getDriver());
			String url = "jdbc:mysql://" + db.getUrl() + "?Unicode=true&characterEncoding=UTF-8&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";
			conn = DriverManager.getConnection(url, db.getUser(), db.getPass());

			ps = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			count = ps.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return count;
	}
}
