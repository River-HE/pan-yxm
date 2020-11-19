package tech.yxm.pan.dao;

import org.springframework.data.repository.CrudRepository;
import tech.yxm.pan.pojo.ShareFile;

/**
 * @author river
 * @date 2020/11/19 15:57:07
 * @description
 */

public interface ShareFileDao extends CrudRepository<ShareFile, String> {
}
