package io.kischang.kispiano.front.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SearchPage implements java.io.Serializable {
    private String name;
    private int page;
    private Integer pageSize;

    public int getSize() {
        return pageSize == null ? 10 : pageSize;
    }

    public Pageable toPage() {
        return PageRequest.of(this.page, this.getSize());
    }
}