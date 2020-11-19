package tech.yxm.pan.FileServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.yxm.pan.service.impl.FileServiceImpl;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class FileServiceImplTest {

    FileServiceImpl test = new FileServiceImpl();

    @Test
    public void moveFile() throws Exception {
        Path path1 = Paths.get("/Volumes/RiverFiles/Files/Idea/ideaWork/pan-base-dir/yxm/dir1");
        Path path2 = Paths.get("/Volumes/RiverFiles/Files/Idea/ideaWork/pan-base-dir/yxm/recycle");

        test.moveFileOrDirectory(path1, path2);
    }

    @Test
    public void delete() throws Exception {
        Path path = Paths.get("/Volumes/RiverFiles/Files/Idea/ideaWork/pan-base-dir/yxm/recycle/java");
        Assertions.assertTrue(Files.exists(path));
        test.delete(path);
        Assertions.assertTrue(!Files.exists(path));
    }

    @Test
    public void regularClean() throws IOException {

        final int EXECUTION_CYCLE = 10;

        Path path = Paths.get("/Volumes/RiverFiles/Files/Idea/ideaWork/pan-base-dir/yxm/recycle");
        DirectoryStream<Path> stream = Files.newDirectoryStream(path);
        for (Path s : stream) {
            String name = s.toFile().getName();
            if (".DS_Store".equals(name)) {
                continue;
            }
            Date modifyTime = new Date(Files.getLastModifiedTime(s).toMillis());
            long expiration = Files.getLastModifiedTime(s).toMillis() + EXECUTION_CYCLE * 60 * 1000;
            Date expirationTime = new Date(expiration);

            System.out.println("文件的修改时间，Date类型： " + modifyTime);
            System.out.println("文件的的修改时间后的10分钟： " + expirationTime);
            if (expirationTime.before(new Date())) {
                System.out.println("<" + name + ">" + " 文件在 " + expirationTime + " 过期\n");
            } else {
                System.out.println("<" + name + ">" + " 文件未过期" + "\n");
            }
        }
        if (null != stream) {
            stream.close();
        }
    }
}
