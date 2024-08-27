package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.file.FileUploadDto;
import by.bsuir.kostyademens.dto.folder.FolderCreateDto;
import by.bsuir.kostyademens.dto.item.ItemDeleteDto;
import by.bsuir.kostyademens.dto.item.ItemRenameDto;
import by.bsuir.kostyademens.exception.FolderAlreadyExistsException;
import by.bsuir.kostyademens.exception.MinioOperationException;
import by.bsuir.kostyademens.util.UserPathUtil;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {


    private final SimpleStorageService storageService;
    private final ItemService itemService;


    public void rename(ItemRenameDto folder) throws FolderAlreadyExistsException {

        String folderPath = getFolderPrefix(folder.getOldPath());

        String newPath = getFolderPrefix(folder.getOldPath()) + folder.getNewPath() + "/";

        folder.setNewPath(newPath);

        if (itemService.isItemAlreadyExist(folderPath, newPath)) {
            throw new FolderAlreadyExistsException("Folder with such name already exist");
        }

        Iterable<Result<Item>> items = storageService.getAllFiles(folder.getOldPath(), true);

        try {
            for (Result<Item> item : items) {

                String oldItemPath = item.get().objectName();
                String newItemPath;

                if (folder.getOldPath().isEmpty()) {
                    newItemPath = folder.getNewPath();
                } else {
                    newItemPath = oldItemPath.replace(folder.getOldPath(), folder.getNewPath());
                }

                storageService.renameFile(newItemPath, oldItemPath);
            }
        } catch (Exception e) {
            throw new MinioOperationException("Failed to find file");
        }
    }


    public void delete(ItemDeleteDto item) {
        String prefix = getFolderPrefix(item.getFullPath());

        Iterable<Result<Item>> items = storageService.getAllFiles(item.getFullPath(), true);

        try {
            for (Result<Item> itemResult : items) {
                storageService.deleteFile(itemResult.get().objectName());
            }
        } catch (Exception e) {
            throw new MinioOperationException("Failed to find file");
        }

        if (!items.iterator().hasNext()) {
            storageService.uploadEmptyFolder(prefix);
        }
    }

    public void createFolder(FolderCreateDto folder) throws FolderAlreadyExistsException {
        String userFolder = UserPathUtil.getUserRootPassword(folder.getOwnerId());

        String folderLocation = userFolder + folder.getFolderLocation();

        String folderNewName = userFolder + folder.getFolderLocation() + folder.getName() + "/";

        if (itemService.isItemAlreadyExist(folderLocation, folderNewName)) {
            throw new FolderAlreadyExistsException("Folder with such name already exits");
        }

        folder.setFolderLocation(folderLocation + "/");

        storageService.uploadEmptyFolder(folderNewName);
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
