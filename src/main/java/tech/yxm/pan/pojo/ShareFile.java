package tech.yxm.pan.pojo;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.nio.file.Path;
import java.util.List;

/**
 * @author river
 * @date 2020/11/19 15:47:28
 * @description
 */

@Data
@Entity
@Table(name = "SHARE_FILE")
public class ShareFile {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "sharePathList")
    private List<Path> sharePathList;
}
