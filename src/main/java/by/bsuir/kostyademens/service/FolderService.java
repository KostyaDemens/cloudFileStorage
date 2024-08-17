package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.FolderRenameDto;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderService {

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

    private String getFolderPrefix(String path) {
        path = StringUtils.chop(path);
        return path.substring(0, path.lastIndexOf("/") + 1);
    }

}
