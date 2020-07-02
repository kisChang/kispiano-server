package io.kischang.kispiano.admin.web;

import com.kischang.simple_utils.formbean.ResponseData;
import io.kischang.kispiano.admin.formbean.LayuiInitConfig;
import io.kischang.kispiano.admin.formbean.LayuiMenuInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author KisChang
 * @date 2020-06-30
 */
@Controller
@RequestMapping
public class IndexController {

    @RequestMapping("/_clear.json")
    @ResponseBody
    public Map<String, Object> clearMenuCache(){
        menuConf = null;
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "清除缓存成功！");
        return map;
    }

    private static LayuiInitConfig menuConf = null;

    @RequestMapping("/_init.json")
    @ResponseBody
    public LayuiInitConfig getUserMenus(){
        LayuiInitConfig tmp = menuConf;
        if (tmp == null){
            LayuiInitConfig initConfig = new LayuiInitConfig();
            initConfig.setHomeInfo(LayuiMenuInfo.mkHome("首页", "/welcome"));
            initConfig.setLogoInfo(LayuiMenuInfo.mkLogo("KIS", "/static/img/logo_mini.png", ""));
            List<LayuiMenuInfo> menuInfo = new LinkedList<>();
            initConfig.setMenuInfo(menuInfo);
            menuInfo.add(LayuiMenuInfo.mkMenu("资源管理", "fa fa-address-book", "/musicxml"));
            menuInfo.add(LayuiMenuInfo.mkMenu("集合管理", "fa fa-address-book", "/xmlset"));
            //本地缓存
            //menuConf = initConfig;
            return initConfig;
        }else {
            return tmp;
        }
    }

    @RequestMapping("/")
    public String index1(){
        return "index";
    }
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/welcome")
    @ResponseBody
    public String welcome(){
        return "Hello World!";
    }

}
