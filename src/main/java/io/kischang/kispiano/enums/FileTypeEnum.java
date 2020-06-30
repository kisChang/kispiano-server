package io.kischang.kispiano.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author KisChang
 * @date 2020-06-30
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum {

    LOCAL(1, "本地"),
    //FastDFS(2, "FastDFS"),
    ;

    private int code;
    private String desc;

}
