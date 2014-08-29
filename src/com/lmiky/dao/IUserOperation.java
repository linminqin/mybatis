package com.lmiky.dao;

import java.util.List;

import com.lmiky.pojo.Article;
import com.lmiky.pojo.User;

public interface IUserOperation {
	public User selectUserByID(int id);
	
	public List<User> selectUsers(String userName); 
	
	public List<Article> getUserArticles(int userid);
}
