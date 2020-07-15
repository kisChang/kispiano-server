package io.kischang.kispiano;

import io.kischang.kispiano.utils.CompressUtils;
import io.kischang.kispiano.utils.CryptUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author KisChang
 * @date 2020-07-12
 */
public class CrypSpecTest {
    public static File pathSource = new File("C:\\Users\\KisChang\\Desktop\\xml\\");
    public static String pathTarget = "C:\\Users\\KisChang\\Desktop\\xmltar\\";

    /**
     * 反向解密xml */
    @Test
    public void testDecodeXml() throws Exception {
        File file = new File("C:\\Users\\KisChang\\Desktop\\xmltar\\200709\\05e017f06d4c48c286e608b516dbe006.xml");
        String sourceData = IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
        System.out.println("sourceData ");
        System.out.println(sourceData);
        //1. gunzip
        byte[] sd = new CompressUtils(CompressUtils.CompressType.GZIP)
                .decompression(Base64Utils.decodeFromString(sourceData));
        System.out.println("unz Data >>" + sd.length);
        System.out.println(Base64Utils.encodeToString(sd));
        //2. 解密
        sd = CryptUtil.decrypt(sd);
        System.out.println("dec Data >>" + sd.length);
        System.out.println(new String(sd, StandardCharsets.UTF_8).substring(0, 100));
    }

    /**
     * 输入XML文件原文档，进行加密和压缩并存储同名文件到目标目录
     */
    @Test
    public void testParseXml() throws Exception {
        listFile(pathSource);
//        parseXml(new File("C:\\Users\\KisChang\\Desktop\\xml\\200701\\5e311081fe9d4eebb64bf87312ffd340.xml"));
    }
    private void listFile(File pathSource) throws Exception {
        for (File once : Objects.requireNonNull(pathSource.listFiles())){
            if (once.isDirectory()){
                listFile(once);
            }else {
                parseXml(once);
            }
        }
    }

    private void parseXml(File once) throws Exception {
        // 加载私钥、公钥
        CompressUtils compressUtils = new CompressUtils(CompressUtils.CompressType.GZIP);

        System.out.println(once.getAbsolutePath());
        //1. 读取二进制
        String fileContent = IOUtils.toString(new FileInputStream(once), StandardCharsets.UTF_8);
        //2. 加密数据
        byte[] cryData = CryptUtil.encrypt(fileContent.getBytes());
        //3. 压缩数据
        byte[] gzData = compressUtils.compression(cryData);
        //4. 存储
        String absolutePath = once.getAbsolutePath();
        File outPath = new File(pathTarget + absolutePath.substring(absolutePath.indexOf("xml") + 4));
        if (!outPath.getParentFile().exists()){
            outPath.getParentFile().mkdirs();
        }
        try (OutputStream out = new FileOutputStream(outPath)){
            IOUtils.write(Base64Utils.encode(gzData), out);
        }
    }

    @Test
    public void testGenKey() throws Exception {
        KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
        keygenerator.init(256);
        SecretKey desKey = keygenerator.generateKey();
        System.out.println(Base64Utils.encodeToString(desKey.getEncoded()));
    }

    @Test
    public void testDeEncrypt() throws Exception {
        byte[] key256 = Constant.AES_KEY;
        //iv.length须满足16的整数倍
        byte[] iv = Constant.AES_IV;

        String content_str = "helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好helloworld 你好";
        byte[] contentbytes = content_str.getBytes(StandardCharsets.UTF_8);

        //ecb256 bytes
        byte[] encryptbytes = CryptUtil.aesEncryptToECB(contentbytes, key256);
        byte[] decryptbytes = CryptUtil.aesDecryptToECB(encryptbytes, key256);
        System.out.println(new String(decryptbytes, StandardCharsets.UTF_8));

        //cbc256 bytes
        encryptbytes = CryptUtil.aesEncryptToCBC(contentbytes, key256, iv);
        decryptbytes = CryptUtil.aesDecryptToCBC(encryptbytes, key256, iv);
        System.out.println(new String(decryptbytes, StandardCharsets.UTF_8));
    }


}
