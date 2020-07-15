package io.kischang.kispiano.admin.service.impl;

import com.kischang.simple_utils.utils.ZipUtilApache;
import io.kischang.kispiano.admin.service.MusicXmlArchiveMngService;
import io.kischang.kispiano.enums.AuditState;
import io.kischang.kispiano.enums.FileTypeEnum;
import io.kischang.kispiano.model.MusicXmlArchive;
import io.kischang.kispiano.model.XmlSet;
import io.kischang.kispiano.service.dao.MusicXmlArchiveDao;
import io.kischang.kispiano.service.dao.XmlSetDao;
import io.kischang.kispiano.utils.CompressUtils;
import io.kischang.kispiano.utils.CryptUtil;
import io.kischang.kispiano.utils.DateFormatUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @author KisChang
 * @date 2020-07-01
 */
@Service
public class MusicXmlArchiveMngServiceImpl implements MusicXmlArchiveMngService {

    @Value("${app.conf.savepath.pic}")
    private String picSavePath;
    @Value("${app.conf.savepath.xml}")
    private String xmlSavePath;

    @Resource
    private MusicXmlArchiveDao dao;
    @Resource
    private XmlSetDao xmlSetDao;

    @Override
    @Transactional(rollbackOn = {IOException.class})
    public void saveWithFile(MusicXmlArchive desc, boolean mainPicType, MultipartFile mainPicFile, boolean xmlType, MultipartFile xmlFile) throws IOException {
        String preffix = new SimpleDateFormat("yyMMdd").format(new Date());
        if (xmlType){
            //单文件
            desc.setShown(true);
            desc.setLastUpdate(DateFormatUtils.formatDatetime());//预存储
            desc = dao.save(desc);

            //处理图片
            if (mainPicType){
                //上传图片
                String picType = Objects.requireNonNull(mainPicFile.getOriginalFilename())
                        .substring(mainPicFile.getOriginalFilename().lastIndexOf("."));
                String picName = desc.getId() + picType;
                Path picSp = Paths.get(picSavePath, preffix, picName);
                checkPath(picSp.toFile().getParentFile());
                try (OutputStream out = new FileOutputStream(picSp.toFile())) {
                    IOUtils.write(mainPicFile.getBytes(), out);
                }

                desc.setMainPic(preffix + "/" + picName);
            }else {
                //选用自带文件
            }
            //处理文件
            String xmlName = desc.getId() + ".xml";
            Path xmlSp = Paths.get(xmlSavePath, preffix, xmlName);
            checkPath(xmlSp.toFile().getParentFile());
            //写入文件
            this.writeXmlToFile(xmlFile.getInputStream(), xmlSp.toFile());

            //更新
            desc.setLastUpdate(DateFormatUtils.formatDatetime());
            desc.setSavePath(preffix + "/" + xmlName);
            desc.setFileType(FileTypeEnum.LOCAL);

            desc.setAuditState(AuditState.Wait);
            desc = dao.save(desc);
        }else {   //zip压缩包多文件
            XmlSet tmp = new XmlSet();
            tmp.setName(desc.getName());
            tmp.setDescText(desc.getDescText());
            final XmlSet xmlSet = this.saveSetWithFile(tmp, mainPicFile);
            try {
                ZipUtilApache.unZipCoding(xmlFile.getInputStream(), "utf-8", null, (entry, inputStream) -> {
                    String fileName = entry.getName().substring(0, entry.getName().indexOf("."));

                    MusicXmlArchive onceDesc = new MusicXmlArchive();
                    onceDesc.setName("Part " + fileName);
                    onceDesc.setDescText(tmp.getName());
                    onceDesc.setXmlsetId(xmlSet.getId());
                    onceDesc.setMainPic(xmlSet.getMainPic());
                    onceDesc.setAuditState(AuditState.Pass);

                    onceDesc.setScore(100);
                    onceDesc.setLastUpdate(DateFormatUtils.formatDatetime());//预存储
                    onceDesc.setShown(false);
                    onceDesc = dao.save(onceDesc);

                    //处理文件
                    String xmlName = onceDesc.getId() + ".xml";
                    Path xmlSp = Paths.get(xmlSavePath, preffix, xmlName);
                    checkPath(xmlSp.toFile().getParentFile());
                    //写入文件
                    this.writeXmlToFile(inputStream, xmlSp.toFile());
                    //更新
                    onceDesc.setLastUpdate(DateFormatUtils.formatDatetime());
                    onceDesc.setSavePath(preffix + "/" + xmlName);
                    onceDesc = dao.save(onceDesc);
                });
            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException("转储失败：" + e.getMessage());
            }
        }
    }

    private void writeXmlToFile(InputStream inputStream, File toFile) throws IOException {
        try (OutputStream out = new FileOutputStream(toFile)) {
            byte[] xmlData = IOUtils.toByteArray(inputStream);
            //1. 加密
            byte[] cryData = CryptUtil.encrypt(xmlData);
            //2. 压缩数据
            byte[] gzData = new CompressUtils(CompressUtils.CompressType.GZIP).compression(cryData);
            //3. 写入文件
            IOUtils.write(gzData, out);
        } catch (Exception e) {
            throw new IOException("数据加密压缩异常", e);
        }
    }

    private void checkPath(File pathFile) {
        if (!pathFile.exists()){
            pathFile.mkdirs();
        }
    }

    @Override
    public MusicXmlArchive updateInfo(MusicXmlArchive desc) {
        Optional<MusicXmlArchive> opt = dao.findById(desc.getId());
        if (opt.isPresent()){
            MusicXmlArchive old = opt.get();
            old.setXmlsetId(desc.getXmlsetId());
            old.setName(desc.getName());
            old.setDescText(desc.getDescText());
            old.setScore(desc.getScore());
            old.setAuditState(desc.getAuditState());
            old.setLastUpdate(DateFormatUtils.formatDatetime());
            return dao.save(old);
        }
        return null;
    }

    @Override
    @Transactional(rollbackOn = {IOException.class})
    public XmlSet saveSetWithFile(XmlSet desc, MultipartFile mainPicFile) throws IOException {
        //预存储
        desc.setLastUpdate(DateFormatUtils.formatDatetime());
        desc.setAuditState(AuditState.Wait);
        desc = xmlSetDao.save(desc);
        String preffix = new SimpleDateFormat("yyMMdd").format(new Date());

        //处理图片
        String picType = Objects.requireNonNull(mainPicFile.getOriginalFilename())
                .substring(mainPicFile.getOriginalFilename().lastIndexOf("."));
        String picName = "set_" + desc.getId() + picType;
        Path picSp = Paths.get(picSavePath, preffix, picName);
        checkPath(picSp.toFile().getParentFile());
        try (OutputStream out = new FileOutputStream(picSp.toFile())) {
            IOUtils.write(mainPicFile.getBytes(), out);
        }

        //更新
        desc.setMainPic(preffix + "/" + picName);
        desc = xmlSetDao.save(desc);

        return desc;
    }

    @Override
    public XmlSet updateSetInfo(XmlSet desc) {
        Optional<XmlSet> opt = xmlSetDao.findById(desc.getId());
        if (opt.isPresent()){
            XmlSet old = opt.get();
            old.setName(desc.getName());
            old.setDescText(desc.getDescText());
            old.setAuditState(desc.getAuditState());
            old.setLastUpdate(DateFormatUtils.formatDatetime());
            return xmlSetDao.save(old);
        }
        return null;
    }
}
