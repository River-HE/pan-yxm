package tech.yxm.pan.dao;

import org.springframework.data.repository.CrudRepository;
import tech.yxm.pan.pojo.User;

/**
 * @author river
 * @date 2020/11/17 16:07:19
 * @description
 */

public interface UserDao extends CrudRepository<User, String> {
}
