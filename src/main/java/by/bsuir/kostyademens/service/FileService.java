package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.model.User;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileService {

    private final SimpleStorageService storageService;

    @SneakyThrows
    public List<String> findAllFilesFromRoot(User user) {
        List<String> names = new ArrayList<>();
        String prefix = getPathToTheUserFolder(user);
        Iterable<Result<Item>> items = storageService.getAllFileFromRoot(prefix);

        for (Result<Item> item : items) {
            names.add(item.get().objectName());
        }

        return names;
    }

    @SneakyThrows
    public List<String> findAllFilesFromSubfolder(User user, String prefix) {
        List<String> names = new ArrayList<>();
        Iterable<Result<Item>> items = storageService.getAllFileFromRoot(getPathToTheUserFolder(user) + prefix + "/");

        for (Result<Item> item : items) {
            names.add(item.get().objectName());
        }

        return names;
    }

    private String getPathToTheUserFolder(User user) {
        return "user-" + user.getId() + "-files/";
    }
}
