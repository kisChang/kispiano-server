package io.kischang.kispiano.front.web;

import com.kischang.simple_utils.formbean.ResponseData;
import com.kischang.simple_utils.page.PageInfo;
import com.kischang.simple_utils.web.bind.annotation.ReqPageInfo;
import io.kischang.kispiano.enums.AuditState;
import io.kischang.kispiano.front.qo.SearchPage;
import io.kischang.kispiano.model.MusicXmlArchive;
import io.kischang.kispiano.service.dao.MusicXmlArchiveDao;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author KisChang
 * @date 2020-06-30
 */
@Controller
@RequestMapping("/api/front/musicxml")
public class MusicXmlController {

    @Resource
    private MusicXmlArchiveDao archiveDao;

    @RequestMapping("/search")
    @ResponseBody
    public Page<?> searchByName(@RequestBody SearchPage searchPage) {
        if (searchPage.getName() != null){
            return archiveDao.findAllByNameContainsAndAuditStateEqualsAndShownEqualsOrderByLastUpdateDesc(
                    searchPage.getName(), AuditState.Pass, true, searchPage.toPage());
        }else {
            return archiveDao.findAllByAuditStateEqualsAndShownEqualsOrderByLastUpdateDesc(
                     AuditState.Pass, true, searchPage.toPage());
        }
    }
    @RequestMapping("/list")
    @ResponseBody
    public Page<?> listById(@RequestBody SearchPage searchPage) {
        return archiveDao.findAllByXmlsetIdEqualsOrderByName(searchPage.getName(), searchPage.toPage());
    }

}
