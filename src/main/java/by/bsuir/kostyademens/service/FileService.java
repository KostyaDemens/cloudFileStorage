package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.file.FileUploadDto;
import by.bsuir.kostyademens.dto.item.ItemDeleteDto;
import by.bsuir.kostyademens.dto.item.ItemDto;
import by.bsuir.kostyademens.dto.item.ItemRenameDto;
import by.bsuir.kostyademens.exception.MinioOperationException;
import by.bsuir.kostyademens.util.UserPathUtil;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final SimpleStorageService storageService;

    public void rename(ItemRenameDto item) throws FileAlreadyExistsException {

        String newPath = getFilePrefix(item.getOldPath()) + item.getNewPath();

        item.setNewPath(newPath);

        if (isFileAlreadyExists(getFilePrefix(item.getOldPath()), newPath)) {
            throw new FileAlreadyExistsException("File with such name already exist");
        }


        storageService.renameFile(item.getNewPath(), item.getOldPath());
    }

    public void delete(ItemDeleteDto item) {
        storageService.deleteFile(item.getFullPath());

        String prefix = getFilePrefix(item.getFullPath());

        Iterable<Result<Item>> items = storageService.getAllFiles(prefix, false);

        if (!items.iterator().hasNext()) {
            storageService.uploadEmptyFolder(prefix);
        }
    }

    public void upload(MultipartFile file, FileUploadDto fileUpload) {
        String key = UserPathUtil.getUserRootPassword(fileUpload.getOwnerId()) + fileUpload.getPath();

        storageService.uploadFile(file, key);
    }

    private boolean isFileAlreadyExists(String folderName, String fileName) {
        Iterable<Result<Item>> items = storageService.getAllFiles(folderName, false);

        try {
            for (Result<Item> item : items) {
                if (item.get().objectName().equals(fileName)) {
                    return true;
                }
            }
        }  catch (Exception e) {
            throw new MinioOperationException("Failed to find file");
        }
        return false;
    }

    private String getFilePrefix(String path) {
        return path.substring(0, path.lastIndexOf("/") + 1);
    }
}
