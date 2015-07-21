package org.br.maymone.projetococacola.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoaderInfo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 System.out.println("Boot class path: " + System.getProperty("sun.boot.class.path"));  
	      System.out.println("Extension class path: " + System.getProperty("java.ext.dirs"));  
	      System.out.println("AppClassPath: " + System.getProperty("java.class.path"));  
	  
	      ClassLoaderInfo i=new ClassLoaderInfo();  
	      System.out.println("\nBoot CL: " + java.lang.Object.class.getClassLoader());  
	      System.out.println("App ClassLoader: " + i.getClass().getClassLoader());  
	      


	

	
	}

}
