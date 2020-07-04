package io.kischang.kispiano.front.web;

import io.kischang.kispiano.enums.AuditState;
import io.kischang.kispiano.front.qo.SearchPage;
import io.kischang.kispiano.service.dao.MusicXmlArchiveDao;
import io.kischang.kispiano.service.dao.XmlSetDao;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author KisChang
 * @date 2020-06-30
 */
@Controller
@RequestMapping("/api/front/xmlset")
public class XmlSetController {

    @Resource
    private XmlSetDao xmlSetDao;

    @RequestMapping("/search")
    @ResponseBody
    public Page<?> searchByName(@RequestBody SearchPage searchPage) {
        if (searchPage.getName() != null) {
            return xmlSetDao.findAllByNameContainsAndAuditStateEquals(
                    searchPage.getName(), AuditState.Pass, searchPage.toPage());
        } else {
            return xmlSetDao.findAllByAuditStateEquals(
                    AuditState.Pass, searchPage.toPage());
        }
    }

}
