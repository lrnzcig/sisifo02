package com.sisifo.almadraba_server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.SessionFactory;

import com.sisifo.almadraba_server.hbm.HibernateUtil;

public class AlmadrabaContextListener implements ServletContextListener {
	
	// TODO put this as a startup argument?
	// TODO does this work?
	private static boolean errorIfNotConnected = true;
	private static SessionFactory factory;

	@Override
	public void contextDestroyed(final ServletContextEvent arg0) {
		if (factory == null && ! errorIfNotConnected) {
			return;
		}
		factory.close();
	}

	@Override
	public void contextInitialized(final ServletContextEvent arg0) {
		try { 
			factory = HibernateUtil.getSessionFactory();
		} catch (ExceptionInInitializerError e) {
			if (errorIfNotConnected) {
				throw e;
			}
		}
	}

	public static SessionFactory getSessionFactory() {
		if (factory == null && ! errorIfNotConnected) {
			return null;
		}
		return factory;
	}

}
