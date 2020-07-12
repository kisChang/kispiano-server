package io.kischang.kispiano.utils;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * @author KisChang
 * @date 2020-07-12
 */
public class AESUtils {
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

    public static byte[] encrypt(byte[] bytes, String aeskey) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(aeskey));
        return cipher.doFinal(bytes);
    }

    public static byte[] decrypt(byte[] bytes, String aeskey) throws Exception {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(aeskey));
        return cipher.doFinal(bytes);
    }

    private static Key getSecretKey(String aeskey) {
        return new SecretKeySpec(Base64Utils.decodeFromString(aeskey)
                , KEY_ALGORITHM);// 转换为AES专用密钥
    }
}
