package pro.app.recipeapp.services;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;
import java.util.TreeMap;

public interface FileService {

    void saveToFile(Object object, String filesDir, String fileName);
    <T> Map<Long, T> readFromFile(String filesDir, String fileName, TypeReference<TreeMap<Long, T>> map) ;

}
