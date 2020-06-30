package io.kischang.kispiano.admin.formbean;

import lombok.Data;

import java.util.List;

/**
 * @author KisChang
 * @date 2020-04-27
 */
@Data
public class LayuiInitConfig implements java.io.Serializable {

    private LayuiMenuInfo homeInfo;
    private LayuiMenuInfo logoInfo;
    private List<LayuiMenuInfo> menuInfo;

}
