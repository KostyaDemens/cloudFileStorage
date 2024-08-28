package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.file.FileSearchDto;
import by.bsuir.kostyademens.dto.item.ItemDto;
import by.bsuir.kostyademens.exception.MinioOperationException;
import by.bsuir.kostyademens.model.BreadCrumb;
import by.bsuir.kostyademens.model.User;
import by.bsuir.kostyademens.model.path.ItemPath;
import by.bsuir.kostyademens.model.path.MinioPath;
import by.bsuir.kostyademens.util.UserPathUtil;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Getter
@Service
@RequiredArgsConstructor
public class ItemService {

    private final SimpleStorageService storageService;

    private LinkedHashMap<String, String> breadCrumbs;

    public List<ItemDto> findAllFiles(User user, String path) {

        List<ItemDto> itemDtos = new ArrayList<>();
        BreadCrumb breadCrumb = new BreadCrumb();

        String userRootFolder = UserPathUtil.getUserRootPassword(user.getId());
        MinioPath minioPath = new MinioPath(userRootFolder, path);

        breadCrumbs = breadCrumb.createBreadCrumbs(minioPath.getPath(), userRootFolder);

        Iterable<Result<Item>> items = storageService.getAllFiles(minioPath.getPath(), false);

        for (Result<Item> item : items) {
            try {

                ItemPath itemPath = new ItemPath(item.get().objectName());

                if (itemPath.getPath().equals(minioPath.getPath())) {
                    continue;
                }

                itemDtos.add(
                        ItemDto.builder()
                                .name(itemPath.getItemName())
                                .path(itemPath.getItemPath(userRootFolder))
                                .fullPath(itemPath.getPath())
                                .isDir(item.get().isDir())
                                .build()
                );

            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
        return itemDtos;
    }

    public List<FileSearchDto> search(String query, User user) {
        List<FileSearchDto> files = new ArrayList<>();

        String userRootFolder = UserPathUtil.getUserRootPassword(user.getId());

        Iterable<Result<Item>> items = storageService.getAllFiles(userRootFolder, true);

        for (Result<Item> item : items) {
            try {

                ItemPath itemPath = new ItemPath(item.get().objectName());

                if (!item.get().objectName().endsWith("/")
                        && itemPath.getItemName().toLowerCase().contains(query)) {
                    files.add(
                            FileSearchDto.builder()
                                    .name(itemPath.getItemName())
                                    .path(itemPath.getPathWithoutUserAndCurrentFolder())
                                    .build()
                    );
                }

            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
        return files;
    }

    public boolean isItemAlreadyExist(String folderName, String fileName) {
        Iterable<Result<Item>> items = storageService.getAllFiles(folderName, false);

        try {
            for (Result<Item> item : items) {
                if (item.get().objectName().equalsIgnoreCase(fileName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new MinioOperationException("Failed to find file");
        }
        return false;
    }

}

