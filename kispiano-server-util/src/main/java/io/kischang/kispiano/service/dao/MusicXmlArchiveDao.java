package io.kischang.kispiano.service.dao;

import io.kischang.kispiano.model.MusicXmlArchive;
import org.springframework.data.repository.CrudRepository;

/**
 * @author KisChang
 * @date 2020-06-30
 */
public interface MusicXmlArchiveDao extends CrudRepository<MusicXmlArchive, String> {

}
