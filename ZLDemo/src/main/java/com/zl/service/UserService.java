package com.zl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zl.dao.DBDao;
import com.zl.model.UserInfo;

public class UserService {

	public static boolean login(String userName, String password) {
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(userName);
		params.add(password);
		
		return DBDao.select("select user_id from tb_user where user_name=? and user_password=?", params).size() > 0;
	}
	
	public static boolean register(String userName, String password) {
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(userName);
		params.add(password);
		
		return DBDao.execute("insert into tb_user(user_name, user_password) values(?,?)", params);
	}
	
	public static List<UserInfo> getUsers() {
		return DBDao.selectMore("select * from tb_user", null, UserInfo.class);
	}
	
	public static boolean uploadPortrait(String userId, String portrait) {
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(portrait);
		params.add(userId);
		
		return DBDao.execute("update tb_user set user_portrait=? where user_id=?", params);
	}
	
	public static boolean update(String userId, String userName, String password) {
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(userName);
		params.add(password);
		params.add(userId);
		
		return DBDao.execute("update tb_user set user_name=?, user_password=? where user_id=?", params);
	}
	
	public static boolean delete(String userId) {
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(userId);
		
		return DBDao.execute("delete from tb_user where user_id=?", params);
	}
	
	public static String getPortrait(String userId) {
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(userId);
		
		Map<String, Object> user = DBDao.select("select user_portrait from tb_user where user_id=?", params);
		
		return user.get("user_portrait").toString();
	}
	
}
