package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.FileRenameDto;
import by.bsuir.kostyademens.dto.ItemDto;
import by.bsuir.kostyademens.model.MinioPath;
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
    public void rename(FileRenameDto fileRenameDto) {

        String oldName = fileRenameDto.getOldName();
        String newName = getPathToTheFileWithoutName(fileRenameDto.getPath(), oldName) + fileRenameDto.getNewName();

        storageService.renameFile(newName, fileRenameDto.getPath());


        System.out.println("hi");
    }

    private String getPathToTheFileWithoutName(String path, String name) {
        if (path.endsWith("/")) {
            return null;
        } else {
            return path.substring(0, path.length() - name.length());
        }
    }

}
