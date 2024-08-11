package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.ItemDto;
import by.bsuir.kostyademens.model.User;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileService {

    private final SimpleStorageService storageService;

    @SneakyThrows
    public List<ItemDto> findAllFilesFromRoot(User user, String path) {
        List<ItemDto> itemDtos = new ArrayList<>();

        if (path == null) {
            path = "";
        }

        String prefix = getPrefix(user, path);

        Iterable<Result<Item>> items = storageService.getAllFiles(prefix);
        for (Result<Item> item : items) {
            if (item.get().objectName().equals(prefix)) {
                continue;
            }
            String nameWithoutPath = Paths.get(item.get().objectName()).getFileName().toString();

            itemDtos.add(
                    ItemDto.builder()
                            .name(nameWithoutPath)
                            .isDir(item.get().isDir())
                            .build()
            );

            System.out.println(Paths.get(item.get().objectName()).getFileName());

        }

        return itemDtos;
    }

    private String getPrefix(User user, String path) {
        return getPathToTheUserFolder(user) + path;
    }

    private String getPathToTheUserFolder(User user) {
        return "user-" + user.getId() + "-files/";
    }
}
