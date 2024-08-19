package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.FolderRenameDto;
import by.bsuir.kostyademens.dto.ItemDeleteDto;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderService {

    //TODO Обработать ошибки

    private final SimpleStorageService storageService;
    private final FileService fileService;


    @SneakyThrows
    public void rename(FolderRenameDto folder) {
        folder.setNewName(getFolderPrefix(folder.getOldPath()) + folder.getNewName() + "/");

        Iterable<Result<Item>> items = storageService.getAllFiles(folder.getOldPath(), true);

        for (Result<Item> item : items) {

            String oldItemPath = item.get().objectName();

            String newItemPath = oldItemPath.replace(folder.getOldPath(), folder.getNewName());

            storageService.renameFile(newItemPath, oldItemPath);
        }
    }

    @SneakyThrows
    public void delete(ItemDeleteDto item) {
        String prefix = getFolderPrefix(item.getFullPath());

        Iterable<Result<Item>> items = storageService.getAllFiles(item.getFullPath(), true);

        for (Result<Item> itemResult : items) {
            storageService.deleteFile(itemResult.get().objectName());
        }

        if (!items.iterator().hasNext()) {
            storageService.uploadEmptyFolder(prefix);
        }
    }

    private String getFolderPrefix(String path) {
        path = StringUtils.chop(path);
        return path.substring(0, path.lastIndexOf("/") + 1);
    }

}
