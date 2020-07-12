package io.kischang.kispiano.utils;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * 压缩工具类
 */
public class CompressUtils {

    private CompressType compressType = CompressType.GZIP;

    public CompressUtils(CompressType type) {
        this.compressType = type;
    }

    public enum CompressType {
        GZIP("gz"), BZIP2("bzip2");

        private String value;

        CompressType(String val) {
            this.value = val;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 压缩
     */
    public byte[] compression(byte[] message) {
        byte[] output = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(message);
             ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            compress(bais, baos);
            output = baos.toByteArray();
            baos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    /**
     * 解压缩
     */
    public byte[] decompression(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            baos = new ByteArrayOutputStream();
            decompress(bais, baos);
            data = baos.toByteArray();
            baos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(baos);
            IOUtils.closeQuietly(bais);
        }
        return data;
    }


    /**
     * 数据流压缩
     */
    public void compress(InputStream is, OutputStream os) throws Exception {
        try (CompressorOutputStream gos = new CompressorStreamFactory()
                .createCompressorOutputStream(compressType.getValue(), os)) {
            IOUtils.copy(is, gos);
        }
    }

    /**
     * 获取压缩数据流
     */
    public OutputStream compress(OutputStream os) throws CompressorException {
        return new CompressorStreamFactory().createCompressorOutputStream(compressType.getValue(), os);
    }

    /**
     * 数据流解压缩
     */
    public void decompress(InputStream is, OutputStream os)
            throws Exception {
        try (CompressorInputStream gis = new CompressorStreamFactory()
                .createCompressorInputStream(compressType.getValue(), is)) {
            IOUtils.copy(gis, os);
        }
    }


    /**
     * 数据流解压缩
     */
    public InputStream decompress(InputStream in) throws CompressorException {
        return new CompressorStreamFactory().createCompressorInputStream(compressType.getValue(), in);
    }
}
