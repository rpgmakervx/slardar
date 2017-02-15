package org.easyarch.slardar.utils;/**
 * Description : MD5Util
 * Created by YangZH on 16-5-25
 *  下午2:25
 */

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.easyarch.slardar.utils.CodecUtils.HashType.*;

/**
 * Description : MD5Util
 * Created by YangZH on 16-5-25
 * 下午2:25
 */

public class CodecUtils {

    public static String hash(byte[] bytes,String encName){
        MessageDigest md = null;
        String strDes = null;
        try {
            if (encName == null || encName.isEmpty()) {
                encName = "MD5";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bytes);
            strDes = bytes2Hex(md.digest()); //to HexString
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid algorithm.");
            return null;
        }
        return strDes;
    }

    public static String sha1(byte[] bytes){
        return hash(bytes,SHA1.name);
    }

    public static String md5(byte[] bytes){
        return hash(bytes, MD5.name);
    }
    public static String md5(String key){
        return hash(key,MD5.name);
    }

    public static String sha1(String key){
        return hash(key,SHA1.name);
    }

    public static String sha1Obj(Object obj){
        return sha1(String.valueOf(obj.hashCode()));
    }

    public static String hash(String strSrc, String encName) {
        return hash(strSrc.getBytes(),encName);
    }

    public static long hash(String key) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance(SHA1.name);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(key.getBytes());
        byte[] bKey = md5.digest();
        long res = ((long) (bKey[3] & 0xFF) << 24) | ((long) (bKey[2] & 0xFF) << 16) | ((long) (bKey[1] & 0xFF) << 8)
                | (long) (bKey[0] & 0xFF);
        return res & 0xffffffffL;
    }

    public static byte[] hmacSHA1(byte[] data, String key) {
        SecretKey secretKey;
        byte[] bytes = null;
        try {
            secretKey = new SecretKeySpec(decodeBase64(key), HMACSHA1.name);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static String encodeBase64(byte[] key) {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * BASE64 解密
     * @param key 需要解密的字符串
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] decodeBase64(String key) {
        try {
            return (new BASE64Decoder()).decodeBuffer(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytes2Hex(byte[] bts) {
        StringBuffer des = new StringBuffer();
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des.append("0");
            }
            des.append(tmp);
        }
        return des.toString();

    }

    public enum HashType{
        SHA1("SHA-1"),MD5("MD5"),SHA256("SHA-256"),HMACSHA1("HmacSHA1");
        public String name;

        HashType(String name) {
            this.name = name;
        }
    }
}
