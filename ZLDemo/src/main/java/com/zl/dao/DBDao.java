package com.zl.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBDao {

	private static final String USERNAME = "root";
	private static final String PASSWORD = "123456";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/ZLDemo";
	private static Connection connection = null;
	/**
	 * 获得数据库的连接
	 * */
	public static Connection conn() {
		if (connection != null) {
			return connection;
		}
		
		System.out.println("---连接数据库---");
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	/**
	 * 增加、删除、修改
	 * */
	public static boolean execute(String sql, List<Object> params) {
		boolean flag = false;
		int result = -1;
		int index = 1;
		try {
			PreparedStatement pstmt = DBDao.conn().prepareStatement(sql);
			if (params != null && !params.isEmpty()) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(index ++, params.get(i));
				}
			}
			
			result = pstmt.executeUpdate();
			pstmt.close();
			
			flag = result > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * 查询单条记录
	 * */
	public static Map<String, Object> select(String sql, List<Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		int index = 1;
		try {
			PreparedStatement pstmt = DBDao.conn().prepareStatement(sql);
			if (params != null && !params.isEmpty()) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(index ++, params.get(i));
				}
			}
			
			// 返回查询结果
			ResultSet resultSet = pstmt.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int col_len = metaData.getColumnCount();
			while (resultSet.next()) {
				for (int i = 0; i < col_len; i++) {
					String cols_name = metaData.getColumnName(i + 1);
					Object cols_value = resultSet.getObject(cols_name);
					if (cols_value == null) {
						cols_value = "";
					}
					
					map.put(cols_name, cols_value);
				}
			}
			
			resultSet.close();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**
	 * 查询多条记录
	 * */
	public static List<Map<String, Object>> selectMore(String sql, List<Object> params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		try {
			PreparedStatement pstmt = DBDao.conn().prepareStatement(sql);
			if (params != null && !params.isEmpty()) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(index ++, params.get(i));
				}
			}
			
			ResultSet resultSet = pstmt.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int cols_len = metaData.getColumnCount();
			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = metaData.getColumnName(i + 1);
					Object cols_value = resultSet.getObject(cols_name);
					if (cols_value == null) {
						cols_value = "";
					}
					
					map.put(cols_name, cols_value);
				}
				
				list.add(map);
			}
			
			resultSet.close();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 通过反射机制查询单条记录
	 * */
	public static <T> T select(String sql, List<Object> params, Class<T> cls) {
		T resultObject = null;
		int index = 1;
		try {
			PreparedStatement pstmt = DBDao.conn().prepareStatement(sql);
			if (params != null && !params.isEmpty()) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(index ++, params.get(i));
				}
			}
			
			ResultSet resultSet = pstmt.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int cols_len = metaData.getColumnCount();
			while (resultSet.next()) {
				// 通过反射机制创建一个实例
//				resultObject = cls.newInstance();
				resultObject = cls.getDeclaredConstructor().newInstance();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = metaData.getColumnName(i + 1);
					Object cols_value = resultSet.getObject(cols_name);
					if (cols_value == null) {
						cols_value = "";
					}
					
					Field field = cls.getDeclaredField(cols_name);
					// 打开javabean的访问权限
					field.setAccessible(true);
					field.set(resultObject, cols_value);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultObject;
	}
	
	/**
	 * 通过反射机制查询多条记录
	 * */
	public static <T> List<T> selectMore(String sql, List<Object> params, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		int index = 1;
		try {
			PreparedStatement pstmt = DBDao.conn().prepareStatement(sql);
			if (params != null && !params.isEmpty()) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(index ++, params.get(i));
				}
			}
			
			ResultSet resultSet = pstmt.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int cols_len = metaData.getColumnCount();
			while (resultSet.next()) {
				// 通过反射机制创建一个实例
				T resultObject = cls.getDeclaredConstructor().newInstance();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = metaData.getColumnName(i + 1);
					Object cols_value = resultSet.getObject(cols_name);
					if (cols_value == null) {
						cols_value = "";
					}
					
					Field field = cls.getDeclaredField(cols_name);
					// 打开javabean的访问权限
					field.setAccessible(true);
					field.set(resultObject, cols_value);
				}
				
				list.add(resultObject);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
