package tech.yxm.pan.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.yxm.pan.service.ShareFileService;

/**
 * @author river
 * @date 2020/11/19 16:01:06
 * @description
 */

@Slf4j
@RestController
@RequestMapping("share")
public class ShareFileController {

    private ShareFileService shareFileService;

    @Autowired
    public ShareFileController(ShareFileService shareFileService) {
        this.shareFileService = shareFileService;
    }
}
