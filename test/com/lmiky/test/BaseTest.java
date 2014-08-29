package com.lmiky.test;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.lmiky.dao.IUserOperation;
import com.lmiky.pojo.Article;
import com.lmiky.pojo.User;

public class BaseTest {
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;

	{
		try {
			reader = Resources.getResourceAsReader("Configuration.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}

	@Test
	public void testSlect() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			User user = (User) session.selectOne("com.lmiky.pojo.UserMapper.selectUserByID", 1);
			System.out.println(user.getUserAddress());
			System.out.println(user.getUserName());
		} finally {
			session.close();
		}
	}

	@Test
	public void testSlect2() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			IUserOperation userOperation = session.getMapper(IUserOperation.class);
			System.out.println(userOperation);
			User user = userOperation.selectUserByID(1);
			System.out.println(user.getUserAddress());
			System.out.println(user.getUserName());
		} finally {
			session.close();
		}
	}
	
	@Test
	public void getUserList(){
		String userName = "%";
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);           
            List<User> users = userOperation.selectUsers(userName);
            for(User user:users){
                System.out.println(user.getId()+":"+user.getUserName()+":"+user.getUserAddress());
            }
            
        } finally {
            session.close();
        }
    }
	
	@Test
	public void getUserArticles(){
		int userid = 1;
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);           
            List<Article> articles = userOperation.getUserArticles(userid);
            for(Article article:articles){
                System.out.println(article.getTitle()+":"+article.getContent()+
                        ":作者是:"+article.getUser().getUserName()+":地址:"+
                         article.getUser().getUserAddress());
            }
        } finally {
            session.close();
        }
    }
}
