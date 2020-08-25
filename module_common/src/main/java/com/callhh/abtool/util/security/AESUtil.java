package com.callhh.abtool.util.security;


import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES对称加密和解密 工具类
 * 规则：【AES加密模式:CBC  填充:PKCS7	偏移量:Iv  数据块:128位  输出:base64  字符集:UTF-8】
 * <p>
 * 背景：MD5加密不可逆的特性决定了在很多场景下并不适用。如在某些需要对加密后的密文进行解密使之可读的场景下，就需要使用可逆加密算法实现！
 * 常用的可逆加密算法有：AES对称加密算法、RSA非对称加密算法
 * <p>
 * 概述：
 * 高级加密标准（英语：Advanced Encryption Standard，缩写：AES），在密码学中又称Rijndael加密法，是美国联邦政府采用的一种区块加密标准。
 * 这个标准用来替代原先的DES，已经被多方分析且广为全世界所使用，是对称密钥加密中最流行的算法之一；
 * 工作模式包括：ECB、CBC、CTR、OFB、CFB；
 * 使用范围：该工具类仅支持CBC模式下的：
 * 填充：PKCS7PADDING
 * 数据块：128位
 * 密码（key）：32字节长度（例如：12345678901234567890123456789012）
 * 偏移量（iv）：16字节长度（例如：1234567890123456）
 * 输出：hex
 * 字符集：UTF-8
 * 使用方式：String encrypt = AESCBCUtil.encrypt("wy");
 * String decrypt = AESCBCUtil.decrypt(encrypt);
 * 验证方式：http://tool.chacuo.net/cryptaes（在线AES加密解密）
 *
 */
public class AESUtil {
    /**
     * 秘钥secret key
     */
    private static final String SECRET_KEY = "h51az2b1b7gf13#p";
    /**
     * iv偏移量
     */
    private static final String IV_OFFSET = "keq3r81&161j13tp";
    /**
     * 算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 默认的加密算法：算法/模式/填充
     */
    private final static String CIPHER_MODE_PADDING = "AES/CBC/PKCS7PADDING";
    /**
     * 编码
     */
    private static final String ENCODING = "UTF-8";
    /**
     * 服务端约定的盐值
     */
    private static final String AES_SALT = "One@-!Sports123G";

    /**
     * 获取密钥信息
     *
     * @param timestamp 时间戳
     *                  AES密钥：md5(timestamp+salt)，取前16位做为AES密钥
     *                  Iv:md5后16位做为AES偏移量
     * @return MD5加密后的密钥信息，32位小写
     */
    public static String getSecretKeyInfo(String timestamp) {
        return MD5Utils.MD5(timestamp + AES_SALT);
    }

    /**
     * 加密：对字符串进行加密
     * SECRET_KEY   密钥
     * IV_OFFSET    iv偏移量
     *
     * @param encryptStr 需要加密的字符串
     * @return 加密后的十六进制字符串(hex)
     */
    public static String encrypt(String encryptStr) {
        String resultStr = "";
        try {
            //创建AES秘钥
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes(ENCODING), ALGORITHM);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(CIPHER_MODE_PADDING);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV_OFFSET.getBytes(ENCODING));
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
            // 加密
            byte[] encrypted = cipher.doFinal(encryptStr.getBytes());
            resultStr = Base64Encoder.encode(encrypted);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultStr;
    }

    /**
     * 解密：对加密后的十六进制字符串(hex)进行解密，并返回字符串
     *
     * @param encryptedStr 需要解密的字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String encryptedStr) {
        String resultStr = "";
        try {
            // 创建AES秘钥
            SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes(ENCODING), ALGORITHM);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(CIPHER_MODE_PADDING);
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV_OFFSET.getBytes(ENCODING));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
            // 解密
//            byte[] bytes = hexStr2Bytes(encryptedStr);
            byte[] bytes = Base64Decoder.decodeToBytes(encryptedStr);
            byte[] encrypted = cipher.doFinal(bytes);
            resultStr = new String(encrypted, ENCODING);//此处使用BASE64做转码
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultStr;
    }

    /**
     * 十六进制字符串转换为byte[]
     *
     * @param hexStr 需要转换为byte[]的字符串
     * @return 转换后的byte[]
     */
    public static byte[] hexStr2Bytes(String hexStr) {
        /*对输入值进行规范化整理*/
        hexStr = hexStr.trim().replace(" ", "").toUpperCase(Locale.US);
        //处理值初始化
        int m = 0, n = 0;
        int iLen = hexStr.length() / 2; //计算长度
        byte[] ret = new byte[iLen]; //分配存储空间

        for (int i = 0; i < iLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = (byte) (Integer.decode("0x" + hexStr.substring(i * 2, m) + hexStr.substring(m, n)) & 0xFF);
        }
        return ret;
    }

    /**
     * byte[]转换为十六进制字符串
     *
     * @param bytes 需要转换为字符串的byte[]
     * @return 转换后的十六进制字符串
     */
    public static String byte2HexStr(byte[] bytes) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < bytes.length; n++) {
            stmp = (Integer.toHexString(bytes[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs;
    }

}
