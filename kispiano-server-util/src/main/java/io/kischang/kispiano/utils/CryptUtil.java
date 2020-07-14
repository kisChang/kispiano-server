package io.kischang.kispiano.utils;

import io.kischang.kispiano.Constant;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

public class CryptUtil {

    /*---------------------本项目默认的加解密功能---------------------*/
    public static byte[] encrypt(byte[] bytes) throws Exception {
        return aesEncryptToCBC(bytes, Constant.AES_KEY, Constant.AES_IV);
    }
    public static byte[] decrypt(byte[] bytes) throws Exception {
        return aesDecryptToCBC(bytes, Constant.AES_KEY, Constant.AES_IV);
    }


    /*---------------------后续为公用加解密功能---------------------*/
    /**
     * 验证密钥长度是否有效
     *
     * @param key 密钥bytes
     * @throws Exception
     */
    public static void checkkey(byte[] key) throws Exception {
        if (key.length != 16 && key.length != 32) {
            throw new Exception("密钥长度错误，key byte[]必须是16或者32位");
        }
    }

    /**
     * AES加密 aes-128/256-ecb
     *
     * @param content 待加密的内容
     * @param key     加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToECB(byte[] content, byte[] key) throws Exception {
        checkkey(key);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
        return cipher.doFinal(content);
    }

    /**
     * AES解密 aes-128/256-ecb
     *
     * @param content 待解密的byte[]
     * @param key     解密密钥
     * @return 解密后的byte[]
     * @throws Exception
     */
    public static byte[] aesDecryptToECB(byte[] content, byte[] key) throws Exception {
        checkkey(key);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
        byte[] decryptBytes = cipher.doFinal(content);
        return decryptBytes;
    }



    /**
     * AES加密 aes-128/256-cbc
     *
     * @param content 待解密的byte[]
     * @param key     加密密钥
     * @param iv      偏移
     * @return 解密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToCBC(byte[] content, byte[] key, byte[] iv) throws Exception {
        checkkey(key);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //算法参数
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), paramSpec);
        return cipher.doFinal(content);
    }

    /**
     * AES解密 aes-128/256-cbc
     *
     * @param content 待解密的byte[]
     * @param key     解密密钥
     * @param iv      偏移
     * @return 解密后的byte[]
     * @throws Exception
     */
    public static byte[] aesDecryptToCBC(byte[] content, byte[] key, byte[] iv) throws Exception {
        checkkey(key);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //算法参数
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), paramSpec);
        return cipher.doFinal(content);
    }
}
