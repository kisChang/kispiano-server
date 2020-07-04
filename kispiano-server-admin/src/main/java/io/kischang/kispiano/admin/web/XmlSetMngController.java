package io.kischang.kispiano.admin.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kischang.simple_utils.formbean.ResponseData;
import com.kischang.simple_utils.utils.JacksonUtils;
import io.kischang.kispiano.admin.formbean.LayuiTableRv;
import io.kischang.kispiano.admin.service.MusicXmlArchiveMngService;
import io.kischang.kispiano.enums.AuditState;
import io.kischang.kispiano.model.XmlSet;
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
@RequestMapping("/xmlset")
public class XmlSetMngController {

    @Resource
    private XmlSetDao xmlSetDao;
    @Resource
    private MusicXmlArchiveMngService musicXmlArchiveMngService;

    @RequestMapping
    public String index() {
        return "mng/xmlset/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public LayuiTableRv<?> dataList(int page, int limit, String searchParams) {
        XmlSet param = JacksonUtils.jsonToType(searchParams, new TypeReference<XmlSet>() {});
        Page<XmlSet> pageData;
        if (param == null || StringUtils.isEmpty(param.getName())){
            pageData = xmlSetDao.findAll(PageRequest.of(page - 1, limit));
        }else {
            pageData = xmlSetDao.findAllByNameContains(param.getName(), PageRequest.of(page - 1, limit));
        }
        return LayuiTableRv.ok(pageData);
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addView() {
        return "mng/xmlset/add";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<?> addPost(XmlSet desc, MultipartFile mainPicFile) {
        try {
            musicXmlArchiveMngService.saveSetWithFile(desc, mainPicFile);
            return new ResponseData<>(true, null, 0);
        } catch (IOException e) {
            return new ResponseData<>(false, "文件转储失败！" + e.getMessage(), -1);
        }
    }


    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editView(String id, Model model) {
        model.addAttribute("model", xmlSetDao.findById(id));
        model.addAttribute("auditStateList", AuditState.values());
        return "mng/xmlset/edit";
    }
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData<?> edit(XmlSet desc) {
        if (musicXmlArchiveMngService.updateSetInfo(desc) == null){
            return new ResponseData<>(false,"更新失败，原纪录未找到！", -1);
        }else {
            return new ResponseData<>(true, null, 0);
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData<?> delete(String id) {
        xmlSetDao.deleteById(id);
        return new ResponseData<>(true, null, 0);
    }

}
