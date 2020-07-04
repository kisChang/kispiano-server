package io.kischang.kispiano.admin.service;

import io.kischang.kispiano.model.MusicXmlArchive;
import io.kischang.kispiano.model.XmlSet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author KisChang
 * @date 2020-07-01
 */
public interface MusicXmlArchiveMngService {

    void saveWithFile(MusicXmlArchive desc, boolean xmlType, MultipartFile mainPicFile, MultipartFile xmlFile) throws IOException;

    MusicXmlArchive updateInfo(MusicXmlArchive desc);

    XmlSet saveSetWithFile(XmlSet desc, MultipartFile mainPicFile) throws IOException;

    XmlSet updateSetInfo(XmlSet desc);

}
