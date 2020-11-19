package tech.yxm.pan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.yxm.pan.dao.ShareFileDao;
import tech.yxm.pan.service.ShareFileService;

/**
 * @author river
 * @date 2020/11/19 15:59:04
 * @description
 */

@Service
public class ShareFileServiceImpl implements ShareFileService {

    private ShareFileDao shareFileDao;

    @Autowired
    public ShareFileServiceImpl(ShareFileDao shareFileDao) {
        this.shareFileDao = shareFileDao;
    }

}
