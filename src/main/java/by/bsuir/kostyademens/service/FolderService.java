package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.file.FileUploadDto;
import by.bsuir.kostyademens.dto.folder.FolderCreateDto;
import by.bsuir.kostyademens.dto.item.ItemDeleteDto;
import by.bsuir.kostyademens.dto.item.ItemRenameDto;
import by.bsuir.kostyademens.util.UserPathUtil;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    //TODO Обработать ошибки

    private final SimpleStorageService storageService;

    @SneakyThrows
    public void rename(ItemRenameDto folder) {
        folder.setNewPath(getFolderPrefix(folder.getOldPath()) + folder.getNewPath() + "/");

        Iterable<Result<Item>> items = storageService.getAllFiles(folder.getOldPath(), true);

        for (Result<Item> item : items) {

            String oldItemPath = item.get().objectName();

            String newItemPath = oldItemPath.replace(folder.getOldPath(), folder.getNewPath());

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

    public void createFolder(FolderCreateDto folder) {
        String userFolder = UserPathUtil.getUserRootPassword(folder.getOwnerId());

        String folderLocation = userFolder + folder.getFolderLocation() + folder.getName();

        folder.setFolderLocation(folderLocation);

        storageService.uploadEmptyFolder(folderLocation + "/");
    }

    public void upload(List<MultipartFile> files, FileUploadDto fileUpload) {
        String key = UserPathUtil.getUserRootPassword(fileUpload.getOwnerId()) + fileUpload.getPath();
        for (MultipartFile file : files) {
            storageService.uploadFile(file, key);
        }
    }

    private String getFolderPrefix(String path) {
        path = StringUtils.chop(path);
        return path.substring(0, path.lastIndexOf("/") + 1);
    }

}
