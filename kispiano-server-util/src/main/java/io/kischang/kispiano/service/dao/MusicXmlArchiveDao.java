package io.kischang.kispiano.service.dao;

import io.kischang.kispiano.enums.AuditState;
import io.kischang.kispiano.model.MusicXmlArchive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author KisChang
 * @date 2020-06-30
 */
public interface MusicXmlArchiveDao extends PagingAndSortingRepository<MusicXmlArchive, String> {

    Page<MusicXmlArchive> findAllByNameContains(String nameKey, Pageable page);

    Page<MusicXmlArchive> findAllByNameContainsAndAndAuditStateEqualsOrderByScoreDesc(String nameKey, AuditState state, Pageable page);

}
