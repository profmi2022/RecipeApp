package pro.app.recipeapp.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Data
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final ObjectMapper objectMapper;

    @Override
    public void saveToFile(Object object, String filesDir, String fileName) {
        Path path = Path.of(filesDir, fileName);
        try {
            String json = objectMapper.writeValueAsString(object);
            Files.createDirectories(path.getParent());
            Files.deleteIfExists(path);
            Files.createFile(path);
            Files.writeString(path, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public <T> Map<Long, T> readFromFile(Path path, TypeReference<HashMap<Long, T>> typeReference) {
//        try {
//            String json = Files.readString(path);
//
//            if (json.isEmpty()) {
//                return new HashMap<>();
//            }
//            return objectMapper.readValue(json, typeReference);
//
//        } catch (NoSuchFileException e) {
//            return new HashMap<>();
//
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public <T> Map<Long, T> readFromFile(String filesDir, String fileName, TypeReference<TreeMap<Long, T>> map) {
        try {
            String json = Files.readString(Path.of(filesDir, fileName));
            return objectMapper.readValue(json, map);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public void uploadFile(MultipartFile file, String filesDir) throws Exception {
        Path filePath = Path.of(filesDir,file.getOriginalFilename());
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
    }


}
