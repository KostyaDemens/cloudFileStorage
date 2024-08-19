package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.ItemDto;
import by.bsuir.kostyademens.model.path.ItemPath;
import by.bsuir.kostyademens.model.path.MinioPath;
import by.bsuir.kostyademens.model.User;
import by.bsuir.kostyademens.util.UserPathUtil;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final SimpleStorageService storageService;


    public List<ItemDto> findAllFiles(User user, String path) {

        List<ItemDto> itemDtos = new ArrayList<>();

        String userRootFolder = UserPathUtil.getUserRootPassword(user.getId());
        MinioPath minioPath = new MinioPath(userRootFolder, path);

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
                                .path(itemPath. getItemPath(userRootFolder))
                                .fullPath(itemPath.getPath())
                                .isDir(item.get().isDir())
                                .build()
                );
            } catch (Exception e) {
                //TODO Обработать ислкючение / Выкинуть кастомное
                throw new RuntimeException();
            }
        }
        return itemDtos;


//    @SneakyThrows
//    public void rename(ItemRenameDto itemDto) {
//
//        storageService.renameFile(
//                getPathToTheFileWithoutName(itemDto.getOldPath()) + itemDto.getNewName(),
//                itemDto.getOldPath()
//        );
//    }
//
//    public String getPathToTheFileWithoutName(String path) {
//        if (path.endsWith("/")) {
//            path = StringUtils.chop(path);
//        }
//        return path.substring(0, path.lastIndexOf("/") + 1);
//    }
    }
}

