package io.kischang.kispiano.model;

import com.kischang.simple_utils.hibernate.ig.HibUUIDGenerator;
import io.kischang.kispiano.enums.AuditState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author KisChang
 * @date 2020-07-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "musicxml_set")
public class XmlSet implements java.io.Serializable {

    //UUID，存入客户端
    @Id
    @GenericGenerator(name = "hibUuid", strategy = HibUUIDGenerator.strategy)
    @GeneratedValue(generator = "hibUuid")
    private String id;

    //基础描述信息
    private String name;
    private String descText;
    private String mainPic;
    private String lastUpdate;

    //审核状态
    private AuditState auditState;

}
