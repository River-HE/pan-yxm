package tech.yxm.pan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tech.yxm.pan.dao.OperatRecordDao;
import tech.yxm.pan.pojo.OperateRecord;
import tech.yxm.pan.pojo.enumclass.Module;
import tech.yxm.pan.pojo.enumclass.OpType;
import tech.yxm.pan.service.RecordService;

import java.util.Date;

/**
 * @author river
 * @date 2020/11/17 00:45:43
 * @description
 */

@Service
public class RecordServiceImpl implements RecordService {

    private OperatRecordDao operatRecordDao;

    @Autowired
    public RecordServiceImpl(OperatRecordDao operatRecordDao) {
        this.operatRecordDao = operatRecordDao;
    }

    @Override
    public void record(String username, Module module, OpType opType, String path) {
        OperateRecord record = new OperateRecord();
        record.setUsername(username);
        record.setModule(module);
        record.setOpType(opType);
        record.setPath(path);
        record.setOpTime(new Date());
        operatRecordDao.save(record);
    }
}
