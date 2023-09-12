package com.example.socialmedia.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtil {
    public static void saveFile(String fileName, MultipartFile multipartFile)
            throws IOException {
        checkDirectoryExist("files");

        Path uploadPath = Paths.get("files");

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IOException("Could not save file: " + fileName, ex);
        }
    }

    public static byte[] readFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] arr = new byte[(int) file.length()];
        fileInputStream.read(arr);
        fileInputStream.close();

        return arr;
    }

    private static void checkDirectoryExist(String directory) throws IOException {
        Path uploadPath = Paths.get(directory);

        if (!Files.exists(uploadPath))
            Files.createDirectory(uploadPath);
    }
}
