package io.kischang.kispiano;

import org.springframework.util.Base64Utils;
import java.nio.charset.StandardCharsets;

/**
 * @author KisChang
 * @date 2020-07-12
 */
public class Constant {

    public static byte[] AES_KEY = Base64Utils.decodeFromString("5SB7FwP2l+TKP3AR6dK8CKAYBX38zVdL4RjyS4aoyPs=");
    public static byte[] AES_IV = "abcdefgh12345678".getBytes(StandardCharsets.UTF_8);

}
