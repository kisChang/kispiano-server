package io.kischang.kispiano.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.kischang.simple_utils.hibernate.model.BaseValEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author KisChang
 * @date 2020-06-30
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum implements BaseValEnum {

    LOCAL(1, "本地"),
    //FastDFS(2, "FastDFS"),
    ;

    @JsonValue
    private int value;
    private String desc;

}
