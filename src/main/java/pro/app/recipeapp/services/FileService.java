package pro.app.recipeapp.services;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public interface FileService {

    void saveToFile(Object object, String filesDir, String fileName);
    <T> Map<Long, T> readFromFile(String filesDir, String fileName, TypeReference<TreeMap<Long, T>> map) ;
    void uploadFile(MultipartFile file, String filesDir) throws Exception;

}
