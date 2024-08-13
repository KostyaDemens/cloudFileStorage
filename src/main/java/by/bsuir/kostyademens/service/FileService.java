package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.ItemDto;
import by.bsuir.kostyademens.model.MinioPath;
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
    public List<ItemDto> findAllFiles(MinioPath path) {

        List<ItemDto> itemDtos = new ArrayList<>();
        Iterable<Result<Item>> items = storageService.getAllFiles(path.getFullPath());
        for (Result<Item> item : items) {

        String itemName = item.get().objectName();

            if (itemName.equals(path.getFullPath())) {
                continue;
            }

            itemDtos.add(
                    ItemDto.builder()
                            .name(path.getFileName(itemName))
                            .path(path.getPathWithoutUserFolder(itemName))
                            .fullPath(itemName)
                            .isDir(item.get().isDir())
                            .build()
            );
        }
        return itemDtos;
    }


    @SneakyThrows
    public void downloadFile(ItemDto itemDto) {
        String homeDir = System.getProperty("user.home") + "/Downloads/";

        storageService.downloadFile(itemDto.getFullPath(), homeDir + itemDto.getName());
    }
}
