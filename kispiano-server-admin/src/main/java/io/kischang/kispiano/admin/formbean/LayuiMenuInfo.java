package io.kischang.kispiano.admin.formbean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author KisChang
 * @date 2020-04-27
 */
@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LayuiMenuInfo implements java.io.Serializable {

    private String title;
    private String icon;
    private String image;
    private String href;
    private String target = "_self";
    private List<LayuiMenuInfo> child;

    public LayuiMenuInfo(String title, String href) {
        this.title = title;
        this.href = href;
    }

    public LayuiMenuInfo(String title, String image, String href) {
        this.title = title;
        this.image = image;
        this.href = href;
    }

    public LayuiMenuInfo(String title, String icon, String image, String href) {
        this.title = title;
        this.icon = icon;
        this.image = image;
        this.href = href;
    }

    public static LayuiMenuInfo mkHome(String title, String href){
        return new LayuiMenuInfo(title, href);
    }

    public static LayuiMenuInfo mkLogo(String title, String image, String href){
        return new LayuiMenuInfo(title, image, href);
    }

    public static LayuiMenuInfo mkMenu(String title, String icon, String href){
        return new LayuiMenuInfo(title, icon, null, href);
    }
}
