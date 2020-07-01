package io.kischang.kispiano.front.web;

import com.kischang.simple_utils.formbean.ResponseData;
import com.kischang.simple_utils.page.PageInfo;
import com.kischang.simple_utils.web.bind.annotation.ReqPageInfo;
import io.kischang.kispiano.enums.AuditState;
import io.kischang.kispiano.model.MusicXmlArchive;
import io.kischang.kispiano.service.dao.MusicXmlArchiveDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author KisChang
 * @date 2020-06-30
 */
@Controller
@RequestMapping("/musicxml")
public class MusicXmlController {

    @Resource
    private MusicXmlArchiveDao archiveDao;

    @RequestMapping("/search")
    @ResponseBody
    public Page<?> searchByName(String name, int page){
        return archiveDao.findAllByNameContainsAndAndAuditStateEqualsOrderByScoreDesc(
                name, AuditState.Pass, PageRequest.of(page, 10));
    }

    @RequestMapping("/{id}/music.xml")
    public String getMusicXmlById(@PathVariable long id){
        return null;
    }

}
