package io.kischang.kispiano.admin.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kischang.simple_utils.formbean.ResponseData;
import com.kischang.simple_utils.utils.JacksonUtils;
import io.kischang.kispiano.admin.formbean.LayuiTableRv;
import io.kischang.kispiano.admin.service.MusicXmlArchiveMngService;
import io.kischang.kispiano.enums.AuditState;
import io.kischang.kispiano.model.MusicXmlArchive;
import io.kischang.kispiano.service.dao.MusicXmlArchiveDao;
import io.kischang.kispiano.service.dao.XmlSetDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author KisChang
 * @date 2020-06-30
 */
@Controller
@RequestMapping("/musicxml")
public class MusicXmlMngController {

    @Resource
    private MusicXmlArchiveDao archiveDao;
    @Resource
    private XmlSetDao xmlSetDao;
    @Resource
    private MusicXmlArchiveMngService musicXmlArchiveMngService;

    @RequestMapping
    public String index(Model model) {
        return "mng/music/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public LayuiTableRv<?> dataList(int page, int limit, String searchParams) {
        MusicXmlArchive param = JacksonUtils.jsonToType(searchParams, new TypeReference<MusicXmlArchive>() {});
        Page<MusicXmlArchive> pageData;
        if (param == null || StringUtils.isEmpty(param.getName())){
            pageData = archiveDao.findAll(PageRequest.of(page - 1, limit));
        }else {
            pageData = archiveDao.findAllByNameContains(param.getName(), PageRequest.of(page - 1, limit));
        }
        return LayuiTableRv.ok(pageData);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addView(Model model) {
        model.addAttribute("setList", xmlSetDao.findAll());
        return "mng/music/add";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<?> addPost(MusicXmlArchive desc, Boolean mainPicType, Boolean xmlType, MultipartFile mainPicFile, MultipartFile xmlFile) {
        try {
            musicXmlArchiveMngService.saveWithFile(desc
                    , mainPicType == null ? true : mainPicType, mainPicFile
                    , xmlType == null ? false : xmlType, xmlFile);
            return new ResponseData<>(true, null, 0);
        } catch (IOException e) {
            return new ResponseData<>(false, "文件转储失败！" + e.getMessage(), -1);
        }
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editView(String id, Model model) {
        model.addAttribute("model", archiveDao.findById(id));
        model.addAttribute("auditStateList", AuditState.values());
        model.addAttribute("setList", xmlSetDao.findAll());
        return "mng/music/edit";
    }
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<?> edit(MusicXmlArchive desc) {
        if (musicXmlArchiveMngService.updateInfo(desc) == null){
            return new ResponseData<>(false,"更新失败，原纪录未找到！", -1);
        }else {
            return new ResponseData<>(true, null, 0);
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData<?> delete(String id) {
        archiveDao.deleteById(id);
        return new ResponseData<>(true, null, 0);
    }

}
