package io.kischang.kispiano.service.dao;

import io.kischang.kispiano.enums.AuditState;
import io.kischang.kispiano.model.XmlSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author KisChang
 * @date 2020-07-02
 */
public interface XmlSetDao extends PagingAndSortingRepository<XmlSet, String> {

    Page<XmlSet> findAllByNameContains(String nameKey, Pageable page);

    Page<XmlSet> findAllByAuditStateEquals(AuditState auditState, Pageable page);

    Page<XmlSet> findAllByNameContainsAndAuditStateEquals(String nameKey, AuditState auditState, Pageable page);

}
