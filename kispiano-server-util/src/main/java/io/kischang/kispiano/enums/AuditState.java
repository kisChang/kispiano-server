package io.kischang.kispiano.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.kischang.simple_utils.hibernate.model.BaseValEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author KisChang
 * @date 2020-07-01
 */
@Getter
@AllArgsConstructor
public enum AuditState implements BaseValEnum {

    Wait(0, "待审核"),
    Pass(1, "审核通过"),
    NoPass(2, "审核驳回"),
    ;

    @JsonValue
    private final int value;
    private final String name;

}
