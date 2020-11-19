package tech.yxm.pan.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @author river
 * @date 2020/11/17 16:04:39
 * @description
 */

@Data
@Entity
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "username", length = 20)
    private String username;

    @Column(name = "password", nullable = false, length = 20)
    private String password;
}
