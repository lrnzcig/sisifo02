package com.sisifo.almadraba_server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.SessionFactory;

import com.sisifo.almadraba_server.hbm.HibernateUtil;

public class AlmadrabaContextListener implements ServletContextListener {
	

	private static SessionFactory factory;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		factory.close();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		factory = HibernateUtil.getSessionFactory();		
	}

	public static SessionFactory getSessionFactory() {
		return factory;
	}

}
