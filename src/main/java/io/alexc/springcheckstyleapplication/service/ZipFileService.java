package io.alexc.springcheckstyleapplication.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Service to handle the zip files
 */
@Service
@Scope("singleton")
public class ZipFileService {

    public Path unzipToTempDirectory(MultipartFile zipFile) {

        Path tmpPath = null;

        try {
            ZipInputStream zipInputStream = new ZipInputStream(zipFile.getInputStream());
            tmpPath = Files.createTempDirectory(null);
            this.unzipToDirectory(zipInputStream, tmpPath);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return tmpPath;

    }

    public void unzipToDirectory(ZipInputStream zipInputStream, Path path) throws IOException {

        ZipEntry entry = null;
        try {
            entry = zipInputStream.getNextEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File previousFile;

        while (entry != null) {
            if (!entry.isDirectory()) {
                this.newFile(path, entry.getName(), zipInputStream);
            } else {
                this.newDir(path, entry.getName());
            }
            entry = zipInputStream.getNextEntry();
        }

        zipInputStream.closeEntry();
        zipInputStream.close();
    }

    private void newDir(Path destinationDir, String name) {

       File file = new File(destinationDir.toFile(), name);
       file.mkdirs();

    }

    private void newFile(Path destinationDir, String name, ZipInputStream zipInputStream) {
        byte[] buffer = new byte[1024];
        try {
            File file = new File(destinationDir.toFile(), name);
            FileOutputStream fos = new FileOutputStream(file);
            int len;
            while ((len = zipInputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
