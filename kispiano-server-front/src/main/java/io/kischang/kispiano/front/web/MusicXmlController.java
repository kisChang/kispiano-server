package io.kischang.kispiano.front.web;

import com.kischang.simple_utils.formbean.ResponseData;
import com.kischang.simple_utils.page.PageInfo;
import com.kischang.simple_utils.web.bind.annotation.ReqPageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author KisChang
 * @date 2020-06-30
 */
@Controller
@RequestMapping("/musicxml")
public class MusicXmlController {

    @RequestMapping("/search")
    @ResponseBody
    public ResponseData<?> searchByName(String name, @ReqPageInfo PageInfo pageInfo){
        return null;
    }

    @RequestMapping("/{id}/music.xml")
    public String getMusicXmlById(@PathVariable long id){
        return null;
    }

}
