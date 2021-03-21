package cn.soneer.assetdata.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author twenc
 */
public class EncryptUtil {
    /** 加密算法 */
    public final static String HASH_ALGORITHM_NAME = "SHA-256";
    /** 加密循环次数 */
    public final static int HASH_ITERATIONS = 3;
    /** 字符编码 */
    private final static String CHARSET = "UTF-8";

    /**
     * 加密处理
     * @param password 密码
     * @param salt 密码盐
     */
    public static String encrypt(String password, String salt) {
        return encrypt(password, salt, HASH_ALGORITHM_NAME, HASH_ITERATIONS);
    }

    /**
     * 加密处理
     * @param password 密码
     * @param salt 密码盐
     * @param hashAlgorithmName 加密算法
     * @param hashIterations 加密循环次数
     */
    public static String encrypt(String password, String salt, String hashAlgorithmName, int hashIterations) {
        // 将字符串转换为字节数组
        byte[] byteSalt = new byte[0];
        byte[] bytePassword = new byte[0];
        try {
            byteSalt = salt.getBytes(CHARSET);
            bytePassword = password.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 创建加密方式
        MessageDigest digest = null;
        try {
            // 根据加密算法获取加密对象
            digest = MessageDigest.getInstance(hashAlgorithmName);

            // 加盐混淆
            digest.reset();
            digest.update(byteSalt);

            // 密码混淆
            byte[] hashed = digest.digest(bytePassword);

            // 循环混淆
            for(int i = 0; i < hashIterations - 1; ++i) {
                digest.reset();
                hashed = digest.digest(hashed);
            }

            // 返回16进制字符串
            return bytesToHexString(hashed);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取随机盐值
     */
    public static String getRandomSalt(int length){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // 获取ascii码中的字符 数字48-57 小写65-90 大写97-122
            int range = random.nextInt(75)+48;
            range = range<97?(range<65?(range>57?114-range:range):(range>90?180-range:range)):range;
            sb.append((char)range);
        }
        return sb.toString();
    }

    /**
     * 字符数组转16进制字符串
     * @param data 字符数组
     */
    private static String bytesToHexString(byte[] data){
        char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        final int l = data.length;
        final char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = digits[(0xF0 & data[i]) >>> 4];
            out[j++] = digits[0x0F & data[i]];
        }
        return new String(out);
    }

    public static String getMD5Str(String str,String salt) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest  = md5.digest((str+salt).getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //16是表示转换为16进制数
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }
}
