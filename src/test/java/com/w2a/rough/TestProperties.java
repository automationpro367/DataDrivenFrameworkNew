package com.w2a.rough;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) throws IOException {
	System.out.println(System.getProperty("user.dir"));
		Properties configue=new Properties();
		Properties OR=new Properties();
		
		FileInputStream fis= new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Configue.properties");
		configue.load(fis);
		System.out.println(configue.getProperty("browser"));
		
		fis= new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
		OR.load(fis);
		System.out.println(OR.getProperty("BtmLogin"));
		

	}

}
