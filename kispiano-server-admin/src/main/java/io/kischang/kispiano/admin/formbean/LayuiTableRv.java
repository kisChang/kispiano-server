package io.kischang.kispiano.admin.formbean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author KisChang
 * @date 2020-06-30
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LayuiTableRv<T> implements java.io.Serializable {

    private int code;
    private int count;
    private String msg;
    private List<T> data;

    public static <T> LayuiTableRv<T> ok(Page<T> page) {
        return ok(page.getContent(), (int) page.getTotalElements());
    }

    public static <T> LayuiTableRv<T> ok(List<T> data, int count) {
        return new LayuiTableRv<>(0, count, "", data);
    }

    public static LayuiTableRv<?> err(int code, String msg) {
        return new LayuiTableRv<>(code, 0, msg, null);
    }

}
