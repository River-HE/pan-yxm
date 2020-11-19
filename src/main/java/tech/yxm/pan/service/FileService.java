package tech.yxm.pan.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @author river
 * @date 2020/11/17 16:11:36
 * @description
 */

public interface FileService {

    /**
     * 上传文件
     *
     * @param directory 要上传的文件路径
     * @param file      要上传的文件
     * @return
     * @throws Exception
     */
    String uploadFile(Path directory, MultipartFile file) throws Exception;

    /**
     * 下载文件
     *
     * @param path 要下载的文件路径
     * @return
     * @throws Exception
     */
    Resource downloadFile(Path path) throws Exception;

    /**
     * 获取目录信息列表
     *
     * @param directory 所获取的信息列表所在的目录
     * @return
     * @throws IOException
     */
    List<String> getDirectories(Path directory) throws IOException;

    /**
     * 移动文件或文件夹
     *
     * @param source 源文件
     * @param target 目标文件
     * @return
     * @throws Exception
     */
    boolean moveFileOrDirectory(Path source, Path target) throws Exception;

    /**
     * 删除文件或文件夹
     *
     * @param path 文件或文件路径
     * @throws IOException
     * @throws Exception
     */
    void delete(Path path) throws IOException, Exception;


}
