package io.kischang.kispiano;

import io.kischang.kispiano.utils.AESUtils;
import io.kischang.kispiano.utils.CompressUtils;
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

/**
 * @author KisChang
 * @date 2020-07-12
 */
public class CrypSpecTest {
    public static File pathSource = new File("C:\\Users\\KisChang\\Desktop\\xml\\");
    public static String pathTarget = "C:\\Users\\KisChang\\Desktop\\xmltar\\";
    /**
     * 输入XML文件原文档，进行加密和压缩并存储同名文件到目标目录
     */
    @Test
    public void testParseXml() throws Exception {
        listFile(pathSource);
//        parseXml(new File("C:\\Users\\KisChang\\Desktop\\xml\\200701\\5e311081fe9d4eebb64bf87312ffd340.xml"));
    }
    private void listFile(File pathSource) throws Exception {
        for (File once : pathSource.listFiles()){
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
        byte[] cryData = AESUtils.encrypt(fileContent.getBytes(), Constant.AESKEY);
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
        String sourceStr = "HelloWorld";
        //加密\解密
        byte[] enData = AESUtils.encrypt(sourceStr.getBytes(), Constant.AESKEY);
        byte[] rvData = AESUtils.decrypt(enData, Constant.AESKEY);
        System.out.println(">>>>" + new String(rvData));
    }


}
