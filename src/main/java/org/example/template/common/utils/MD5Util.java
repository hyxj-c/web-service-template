package org.example.template.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    /**
     * 用MD5算法进行加密
     * @param strSrc 原字符串
     * @return 加密后的32位字符串
     */
    public static String encrypt(String strSrc) {
        String appSecret = "白鹤淮";
        String str = strSrc + appSecret;

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！" + e);
        }
        md.update(str.getBytes());
        byte[] bytes = md.digest(); // 返回一个length为16的字节数组

        return generate32Str(bytes);
    }

    /**
     * 生成一个为传入的字节数组长度二倍的字符串
     * @param bytes 字节数组
     * @return 2倍bytes长度的字符串
     */
    public static String generate32Str(byte[] bytes) {
        char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        char[] chars = new char[bytes.length * 2]; // 长度为32的字符数组，bytes的长度为16
        int k = 0;
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            chars[k++] = hexChars[b >>> 4 & 0xf]; // b前四位的16进制值
            chars[k++] = hexChars[b & 0xf]; // b后四位的16进制值
        }
        return new String(chars);
    }

}
