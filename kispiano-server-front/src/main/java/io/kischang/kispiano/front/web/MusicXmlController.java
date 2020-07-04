package io.kischang.kispiano.front.web;

import com.kischang.simple_utils.formbean.ResponseData;
import com.kischang.simple_utils.page.PageInfo;
import com.kischang.simple_utils.web.bind.annotation.ReqPageInfo;
import io.kischang.kispiano.enums.AuditState;
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

    @Data
    public static class SearchPage implements java.io.Serializable {
        private String name;
        private int page;
        private Integer pageSize;

        public int getSize(){
            return pageSize == null ? 10 : pageSize;
        }
    }
    @RequestMapping("/search")
    @ResponseBody
    public Page<?> searchByName(@RequestBody SearchPage searchPage) {
        if (searchPage.name != null){
            return archiveDao.findAllByNameContainsAndAuditStateEqualsOrderByScoreDesc(
                    searchPage.name, AuditState.Pass, PageRequest.of(searchPage.page, searchPage.getSize()));
        }else {
            return archiveDao.findAllByAuditStateEqualsOrderByScoreDesc(
                     AuditState.Pass, PageRequest.of(searchPage.page, searchPage.getSize()));
        }
    }

}
