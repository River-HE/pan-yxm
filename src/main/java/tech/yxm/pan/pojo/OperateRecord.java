package tech.yxm.pan.pojo;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import tech.yxm.pan.pojo.enumclass.Module;
import tech.yxm.pan.pojo.enumclass.OpType;

import javax.persistence.*;
import java.util.Date;

/**
 * @author river
 * @date 2020/11/16 22:21:07
 * @description
 */

@Data
@Entity
@Table(name = "OPERATION_RECORD")
public class OperateRecord {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "MODULE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Module module;

    @Column(name = "OP_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private OpType opType;

    @Column(name = "PATH", nullable = false)
    private String path;

    @Column(name = "OP_TIME", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private Date opTime;
}
