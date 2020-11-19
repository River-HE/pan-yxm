package tech.yxm.pan.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import tech.yxm.pan.config.PanProperties;
import tech.yxm.pan.pojo.enumclass.Module;
import tech.yxm.pan.pojo.enumclass.OpType;
import tech.yxm.pan.service.FileService;
import tech.yxm.pan.service.RecordService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author river
 * @date 2020/11/17 16:11:36
 * @description
 */

@Slf4j
@RestController
@RequestMapping("pan")
public class PanController {

    private FileService fileService;

    private PanProperties panProperties;

    private RecordService recordService;

    @Autowired
    public PanController(FileService fileService,
                         PanProperties panProperties,
                         RecordService recordService) {
        this.fileService = fileService;
        this.panProperties = panProperties;
        this.recordService = recordService;
    }

    /**
     * 上传单个文件
     *
     * @param file      要上传的文件
     * @param parentDir 该文件的父目录
     * @param username  用户名
     * @param response
     * @return
     */
    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam(value = "parentDir", defaultValue = "/",
                                     required = false) String parentDir,
                             @RequestHeader("Username") String username,
                             HttpServletResponse response) {
        if (null == username || username.isEmpty()) {
            response.setStatus(412);
            return "用户名不存在";
        }
        Path directory = Paths.get(panProperties.getBaseDir(), username, "pan", parentDir);
        if (Files.notExists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                log.error("创建文件夹失败");
                response.setStatus(412);
                return e.toString();
            }
        }

        try {
            String fileName = fileService.uploadFile(directory, file);
            recordService.record(username, Module.PAN, OpType.UPLOAD, parentDir + "/" + fileName);
            return fileName;
        } catch (Exception e) {
            log.error("文件上传失败", e);
            response.setStatus(412);
            return e.toString();
        }
    }

    /**
     * 下载单个文件
     *
     * @param username 用户名
     * @param request
     * @return
     */
    @GetMapping("{username}/**")
    public ResponseEntity getFileOrDirectory(@PathVariable("username") String username,
                                             HttpServletRequest request) {
        String fullPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String location = new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, fullPath);

        log.info("fullPath = " + fullPath);
        log.info("bestMatchPattern = " + bestMatchPattern);
        log.info("location = " + location);

        recordService.record(username, Module.PAN, OpType.GET, location);
        //获取需要下载的路径
        Path path = Paths.get(panProperties.getBaseDir(), username, "pan", location);
        if (Files.notExists(path)) {
            return ResponseEntity.status(404).body("路径不存在");
        } else if (Files.isDirectory(path)) {
            try {
                List<String> names = fileService.getDirectories(path);
                List<String> retNames = new ArrayList<>();
                for (String name : names) {
                    retNames.add(location + "/" + name);
                }
                return ResponseEntity.ok(retNames);
            } catch (Exception e) {
                log.error("获取文件或目录信息失败", e);
                return ResponseEntity.status(404).body("获取文件或目录信息失败");
            }
        } else {
            try {
                Resource resource = fileService.downloadFile(path);
                String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
                if (null == contentType) {
                    contentType = "application/octet-stream";
                }
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
            } catch (Exception e) {
                log.error("获取文件或目录信息失败", e);
                return ResponseEntity.status(404).body("获取文件或目录信息失败");
            }
        }
    }

    /**
     * 删除文件或文件夹
     *
     * @param username 用户名
     * @param request
     * @return
     */
    @DeleteMapping("{username}/**")
    public ResponseEntity deleteFile(@PathVariable("username") String username,
                                     HttpServletRequest request) {
        String fullPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String location = new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, fullPath);

        log.info("fullPath = " + fullPath);
        log.info("bestMatchPattern = " + bestMatchPattern);
        log.info("location = " + location);

        recordService.record(username, Module.PAN, OpType.DELETE, location);
        //获取需要删除的文件的路径
        Path path = Paths.get(panProperties.getBaseDir(), username, "pan", location);
        log.info(path.toString());
        if (Files.notExists(path)) {
            log.error("路径不存在");
            return ResponseEntity.status(404).body("路径不存在");
        } else {
            try {
                Path recyclePath = Paths.get(panProperties.getBaseDir(), username, "recycle");
                log.info(recyclePath.toString());
                fileService.moveFileOrDirectory(path, recyclePath);
                return ResponseEntity.status(200).body("删除成功");
            } catch (Exception e) {
                log.error("删除失败1");
                return ResponseEntity.status(404).body("删除失败2");
            }
        }
    }
}
