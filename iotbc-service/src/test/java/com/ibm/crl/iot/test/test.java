package com.ibm.crl.iot.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Date date = new Date();
		System.out.println(date);
		
		
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd-HH/mm/ss");
		System.out.println(dFormat.format(date));
		
		
	}

}
