package tech.yxm.pan.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import tech.yxm.pan.config.PanProperties;
import tech.yxm.pan.pojo.enumclass.Module;
import tech.yxm.pan.pojo.enumclass.OpType;
import tech.yxm.pan.service.FileService;
import tech.yxm.pan.service.RecordService;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * @author river
 * @date 2020/11/18 00:01:16
 * @description 清理recycle文件夹的任务
 */

@Slf4j
public class ClearRecycleJob extends QuartzJobBean {

//    private static final int EXECUTION_CYCLE = 10;

    private FileService fileService;

    private PanProperties panProperties;

    private RecordService recordService;

    @Autowired
    public ClearRecycleJob(FileService fileService,
                           PanProperties panProperties,
                           RecordService recordService) {
        this.fileService = fileService;
        this.panProperties = panProperties;
        this.recordService = recordService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("清理recycle文件夹的任务开始");
        DirectoryStream<Path> baseDirStream = null;
        DirectoryStream<Path> userRecycleStream = null;
        try {
            baseDirStream = Files.newDirectoryStream(Paths.get(panProperties.getBaseDir()));
            for (Path userPath : baseDirStream) {
                String username = userPath.toFile().getName();
                if (".DS_Store".equals(username)) {
                    continue;
                }
                //路径拼接
                Path recyclePath = userPath.resolve("recycle");
                userRecycleStream = Files.newDirectoryStream(recyclePath);
                for (Path recycleSubPath : userRecycleStream) {
                    if (".DS_Store".equals(recycleSubPath.toFile().getName())) {
                        continue;
                    }
                    long expiration = Files.getLastModifiedTime(recycleSubPath).toMillis() + 30 * 1000;
                    Date expirationTime = new Date(expiration);
                    if (expirationTime.before(new Date())) {
                        fileService.delete(recycleSubPath);
                        recordService.record(username,
                                Module.RECYCLE,
                                OpType.AUTO_DELETE,
                                recycleSubPath.toFile().getName());
                    } else {
                        continue;
                    }
                }
                if (null != userRecycleStream) {
                    userRecycleStream.close();
                }
            }
            if (null != baseDirStream) {
                baseDirStream.close();
            }
        } catch (Exception e) {
            throw new JobExecutionException("清理recycle出错", e);
        }
        log.info("清理recycle文件夹的任务结束");
    }
}
