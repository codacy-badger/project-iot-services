package com.ibm.crl.mv.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

/**
 * <pre>
 * 
 *   1. apache commons-codec support base64
 *   2. jdk sum.misc.BASE64* support
 * </pre>
 *
 */
public final class Base64Utils {


	public static java.util.Optional<byte[]> decodeStrToByteArray(String inputStr) throws IOException {

		// byte[] byteBuffer = new sun.misc.BASE64Decoder().decodeBuffer(inputStr);

		byte[] byteBuffer = Base64.decodeBase64(inputStr);

		return java.util.Optional.ofNullable(byteBuffer);

	}

	public static java.util.Optional<String> encodeFileToString(String filePath) {

		java.util.Optional<byte[]> optional = fileToByteArray(filePath);

		if (optional.isPresent()) {

			byte[] buff = optional.get();

			String base64Str = Base64.encodeBase64String(buff);

			return java.util.Optional.ofNullable(base64Str);

		}

		return java.util.Optional.empty();

	}

	public static String encodeByteArrayToStr(byte[] buff) {

		String base64Str = Base64.encodeBase64String(buff);
		return base64Str;
	}

	private static java.util.Optional<byte[]> fileToByteArray(String filePath) {

		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
				ByteArrayOutputStream baos = new ByteArrayOutputStream();) {

			byte[] buff = new byte[8192];

			int len = 0;

			while ((len = bis.read(buff)) != -1) {

				baos.write(buff, 0, len);
			}

			baos.flush();

			byte[] byteBuffer = baos.toByteArray();

			return java.util.Optional.ofNullable(byteBuffer);

		} catch (IOException e) {
			e.printStackTrace();
			String msg = "Happen exception when read file content !!!";
			System.err.println(msg);
			return java.util.Optional.empty();
		}
	}
}
