package io.kischang.kispiano.model;

import com.kischang.simple_utils.hibernate.ig.HibUUIDGenerator;
import io.kischang.kispiano.enums.FileTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * MusicXml档案库
 *
 * @author KisChang
 * @date 2020-06-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "musicxml_archive")
public class MusicXmlArchive implements java.io.Serializable {

    //UUID，存入客户端
    @Id
    @GenericGenerator(name = "hibUuid", strategy = HibUUIDGenerator.strategy)
    @GeneratedValue(generator = "hibUuid")
    private String id;

    //基础描述信息
    private String name;
    private String mainPic;
    private String descText;
    private String lastUpdate;
    //评分信息（0-100，按 /10 显示）
    private int score;

    //存储信息
    private FileTypeEnum fileType;
    private String savePath;

}
