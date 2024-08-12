package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.ItemDto;
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
    private final PathService pathService;

    @SneakyThrows
    public List<ItemDto> findAllFilesFromRoot(String path) {

        String prefix = pathService.parse(path);

        List<ItemDto> itemDtos = new ArrayList<>();
        Iterable<Result<Item>> items = storageService.getAllFiles(prefix);
        for (Result<Item> item : items) {

            if (item.get().objectName().equals(prefix)) {
                continue;
            }

            itemDtos.add(
                    ItemDto.builder()
                            .name(Paths.get(item.get().objectName()).getFileName().toString())
                            .path(pathService.cutUserFolder(item.get().objectName()))
                            .isDir(item.get().isDir())
                            .build()
            );
        }
        return itemDtos;
    }
}
