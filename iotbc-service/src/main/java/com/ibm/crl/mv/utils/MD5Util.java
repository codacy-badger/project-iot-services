package com.ibm.crl.mv.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.ibm.cloud.objectstorage.internal.SdkFilterInputStream;


public class MD5Util {

	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };
	private static RandomAccessFile randomAccessFile;

	
	//通过文件路径获取MD5
	public static String getFileMD5(String filePath) {
		try {
			randomAccessFile = new RandomAccessFile(filePath, "rw");
			byte[] bytebuff = new byte[(int)randomAccessFile.length()];
			randomAccessFile.readFully(bytebuff);	
			String md5f = md5(ByteBuffer.wrap(bytebuff));
			return md5f;
		} catch (Exception e) {
			return "error";
		}
	}
	
	//通过文件字符串穿
	public static String getStreamMD5(SdkFilterInputStream stream) {
		ByteArrayOutputStream  baos  = null;
		try {	
			 baos = new ByteArrayOutputStream();	
			byte[] bytebuff = new byte[8192];
			int len = 0;
			while((len = stream.read(bytebuff)) != -1) {
				baos.write(bytebuff, 0, len);		
			}
			byte[] buff = baos.toByteArray();
			String md5 = md5(ByteBuffer.wrap(buff));
			return md5;
			
		} catch (IOException e1) {
			e1.printStackTrace();
		System.out.println(e1.toString());
			return "error";
		}finally {
			try {
				if(baos != null)
					baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	public static String getFileMD5(byte[] array)  {
		String md5 = md5(ByteBuffer.wrap(array));
		return md5;
	}
	

	/**
	 * MD5校验字符串
	 * 
	 * @param s
	 *            String to be MD5
	 * @return 'null' if cannot get MessageDigest
	 */

	public static String getStringMD5(String s) {
		MessageDigest mdInst;
		try {
			// 获得MD5摘要算法的 MessageDigest 对象
			mdInst = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}

		byte[] btInput = s.getBytes();
		// 使用指定的字节更新摘要
		mdInst.update(btInput);
		// 获得密文
		byte[] md = mdInst.digest();
		// 把密文转换成十六进制的字符串形式
		int length = md.length;
		char str[] = new char[length * 2];
		int k = 0;
		for (byte b : md) {
			str[k++] = hexDigits[b >>> 4 & 0xf];
			str[k++] = hexDigits[b & 0xf];
		}
		return new String(str);
	}

	@SuppressWarnings("unused")
	private static String getSubStr(String str, int subNu, char replace) {
		int length = str.length();
		if (length > subNu) {
			str = str.substring(length - subNu, length);
		} else if (length < subNu) {
			// NOTE: padding字符填充在字符串的右侧，和服务器的算法是一致的
			str += createPaddingString(subNu - length, replace);
		}
		return str;
	}

	private static String createPaddingString(int n, char pad) {
		if (n <= 0) {
			return "";
		}

		char[] paddingArray = new char[n];
		Arrays.fill(paddingArray, pad);
		return new String(paddingArray);
	}

	/**
	 * 计算MD5校验
	 * 
	 * @param buffer
	 * @return 空串，如果无法获得 MessageDigest实例
	 */

	private static String md5(ByteBuffer buffer) {
		String s = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(buffer);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, >>>,
				// 逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return s;
	}
}
