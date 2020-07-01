package io.kischang.kispiano.admin.service;

import io.kischang.kispiano.model.MusicXmlArchive;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author KisChang
 * @date 2020-07-01
 */
public interface MusicXmlArchiveMngService {

    MusicXmlArchive saveWithFile(MusicXmlArchive desc, MultipartFile mainPicFile, MultipartFile xmlFile) throws IOException;

    MusicXmlArchive updateInfo(MusicXmlArchive desc);
}
