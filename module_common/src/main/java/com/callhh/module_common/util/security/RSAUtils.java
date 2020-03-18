package com.callhh.module_common.util.security;


import javax.crypto.Cipher;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;


/**
 * Rsa加解密算法
 */
public class RSAUtils {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    public static final String SIGNATURE_ALGORITHMSHA = "SHA1withRSA";
    public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";//加密填充方式
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    public static final byte[] DEFAULT_SPLIT = "#PART#".getBytes();
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final String PUBLIC_KEY_TEMP = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDeLvhCsCo9+W64Z+lsIOGYEzeP\n" +
            "H/TDJ/ZZYwhGhy2qFFGsNWdfiYmuccI1c3ohd8oQspBycDZEUr5/++DA8EzmmNxR\n" +
            "MXQ6DQpnHLQuyqqYSQyKPgLJhm+fpXcXiPzGR851DbUtwCNib42Re94KUE2vpABR\n" +
            "24gXB3boFGPgC5/R2wIDAQAB";
    public static final int DEFAULT_BUFFERSIZE = (PUBLIC_KEY_TEMP.length() / 8) - 11;

    /**
     * 使用公钥加密
     *
     * @param content
     * @return
     */
    public static String encryptByPublic(String content) {
        try {
            PublicKey pubkey = getPublicKeyFromX509(KEY_ALGORITHM, PUBLIC_KEY_TEMP);
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, pubkey);
            byte plaintext[] = content.getBytes("UTF-8");
            byte[] output = cipher.doFinal(plaintext);
            String s = new String(Base64.encode(output, Base64.DEFAULT));
            return s;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到公钥
     */
    private static PublicKey getPublicKeyFromX509(String algorithm,
                                                  String bysKey) throws NoSuchAlgorithmException, Exception {
        byte[] decodedKey = Base64.decode(bysKey, Base64.DEFAULT);
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(x509);
    }

    /**
     * 用公钥对字符串进行分段加密
     */
    public static String encryptByPublicKeyForSpilt(String content) {
        byte[] data = new byte[0];
        try {
            data = content.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int dataLen = data.length;
        if (dataLen <= MAX_ENCRYPT_BLOCK) {
            byte[] bytes = new byte[0];
            try {
                bytes = encryptByPublicKey(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new String(Base64.encode(bytes, Base64.DEFAULT));
        }
        List<Byte> allBytes = new ArrayList<Byte>(1024);
        int bufIndex = 0;
        int subDataLoop = 0;
        byte[] buf = new byte[MAX_ENCRYPT_BLOCK];
        for (int i = 0; i < dataLen; i++) {
            buf[bufIndex] = data[i];
            if (++bufIndex == MAX_ENCRYPT_BLOCK || i == dataLen - 1) {
                subDataLoop++;
                if (subDataLoop != 1) {
                    for (byte b : DEFAULT_SPLIT) {
                        allBytes.add(b);
                    }
                }
                byte[] encryptBytes = new byte[0];
                try {
                    encryptBytes = encryptByPublicKey(buf);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (byte b : encryptBytes) {
                    allBytes.add(b);
                }
                bufIndex = 0;
                if (i == dataLen - 1) {
                    buf = null;
                } else {
                    buf = new byte[Math.min(MAX_ENCRYPT_BLOCK, dataLen - i - 1)];
                }
            }
        }
        byte[] bytes = new byte[allBytes.size()];
        {
            int i = 0;
            for (Byte b : allBytes) {
                bytes[i++] = b.byteValue();
            }
        }
        return new String(Base64.encode(bytes, Base64.DEFAULT));
    }

    /**
     * 用公钥对字符串进行加密
     *
     * @param data 原文
     */
    public static byte[] encryptByPublicKey(byte[] data) throws Exception {
        // 得到公钥
        PublicKey pubkey = getPublicKeyFromX509(KEY_ALGORITHM, PUBLIC_KEY_TEMP);
        // 加密数据
        Cipher cp = Cipher.getInstance(ECB_PKCS1_PADDING);
        cp.init(Cipher.ENCRYPT_MODE, pubkey);
        return cp.doFinal(data);
    }

    /**
     * 公钥加密
     *
     * @param content 加密前字符串
     * @return 加密后密文
     */
    public static String encryptByPublicKey(String content)
            throws Exception {
        if (content == null) {
            return null;
        }
        byte[] data = content.getBytes("UTF-8");
        PublicKey pubkey = getPublicKeyFromX509(KEY_ALGORITHM, PUBLIC_KEY_TEMP);
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        // 对数据加密
        cipher.init(Cipher.ENCRYPT_MODE, pubkey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        String s = new String(Base64.encode(encryptedData, Base64.DEFAULT));
        return s;
    }

}
