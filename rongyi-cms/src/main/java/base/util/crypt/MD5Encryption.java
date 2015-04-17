/*
 *
 * Identity/MD5Encryption.java	 2005-9-6
 *
 * Copyright 2004 Hintsoft, LTD. All rights reserved.
 * HINTSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * 
 */
package base.util.crypt;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import sun.misc.BASE64Encoder;


public class MD5Encryption {
	private static BASE64Encoder encoder = new BASE64Encoder();

	protected MD5Encryption() {
	}

	public static String encrypt(String password) {
		if (password == null)
			return null;
		try {
			byte[] bytes = password.getBytes("UTF8");
			return encrypt(bytes);

		} catch (Exception e) {
			return null;
		}
	}

	public static String encrypt(byte[] password) {
		if (password == null)
			return null;
		if (password.length == 0)
			return "";
		byte[] md5 = MD5(password);
		if (md5 == null)
			return null;
		return encoder.encode(md5);
	}

	private static final byte[] MD5(byte[] source) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(source);
			return md5.digest();
		} catch (Exception e) {
			return null;
		}
	}

	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

	public static void main(String[] args) {
//		File file=new File("D:/JQuery_1.4_API.CHM");
//		System.out.println(MD5Encryption.getFileMD5(file)+"--"+file.length());
		System.out.println(encrypt("222222"));
	}
}
