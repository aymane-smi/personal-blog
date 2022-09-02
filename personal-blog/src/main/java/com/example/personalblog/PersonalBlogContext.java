package com.example.personalblog;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class PersonalBlogContext implements ApplicationContextAware{
	private static ApplicationContext Context;
	
	public void setApplicationContext(ApplicationContext context) throws BeansException{
		Context = context;
	}
	
	public static Object getBean(String BeanName) {
		return Context.getBean(BeanName);
	}
	
}
