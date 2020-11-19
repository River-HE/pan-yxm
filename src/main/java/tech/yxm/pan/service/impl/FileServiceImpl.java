package tech.yxm.pan.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tech.yxm.pan.service.FileService;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author river
 * @date 2020/11/17 16:11:36
 * @description
 */

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(Path directory, MultipartFile file) throws Exception {
        log.info("OriginalFileName: " + file.getOriginalFilename());
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        log.info("fileName: " + fileName);

        final String TWO_DOTS = "..";
        if (fileName.contains(TWO_DOTS)) {
            throw new Exception("文件名不合法: " + fileName);
        }
        try {
            Path targetLocation = directory.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new Exception("文件上传失败", e);
        }
        return fileName;
    }

    @Override
    public Resource downloadFile(Path path) throws Exception {
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists()) {
            return resource;
        } else {
            throw new Exception("文件不存在");
        }
    }

    @Override
    public List<String> getDirectories(Path directory) throws IOException {
        DirectoryStream<Path> stream = Files.newDirectoryStream(directory);
        List<String> list = new ArrayList<>();
        for (Path path : stream) {
            String name = path.toFile().getName();
            if (".DS_Store".equals(name)) {
                continue;
            }
            list.add(name);
        }
        if (null != stream) {
            stream.close();
        }
        return list;
    }

    @Override
    public boolean moveFileOrDirectory(Path source, Path target) throws Exception {
        try {
            if (Files.notExists(target)) {
                Files.createDirectories(target);
            }

            Files.move(source, target.resolve(source.toFile().getName()),
                    StandardCopyOption.REPLACE_EXISTING);
            //设置最后一次修改时间
            Files.setLastModifiedTime(target.resolve(source.toFile().getName()),
                    FileTime.fromMillis(System.currentTimeMillis()));
            return true;
        } catch (IOException e) {
            throw new Exception("删除失败3", e);
        }
    }

    @Override
    public void delete(Path path) throws Exception {
        if (Files.notExists(path)) {
            return;
        }
        if (!Files.isDirectory(path)) {
            Files.deleteIfExists(path);
        } else {
            DirectoryStream<Path> stream = null;
            try {
                stream = Files.newDirectoryStream(path);
                for (Path s : stream) {
                    delete(s);
                }
                Files.deleteIfExists(path);
            } catch (Exception e) {
                throw new Exception("删除文件或文件夹出错", e);
            } finally {
                if (null != stream) {
                    stream.close();
                }
            }
        }
    }
}
