package tech.yxm.pan.service;

import tech.yxm.pan.pojo.enumclass.Module;
import tech.yxm.pan.pojo.enumclass.OpType;

/**
 * @author river
 * @date 2020/11/17 00:45:13
 * @description
 */

public interface RecordService {

    /**
     * 记录操作日志
     *
     * @param username 用户名
     * @param module   操作模块
     * @param opType   操作类型
     * @param path     路径
     */
    void record(String username, Module module, OpType opType, String path);
}
