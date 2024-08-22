package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.FileRenameDto;
import by.bsuir.kostyademens.dto.ItemDeleteDto;
import by.bsuir.kostyademens.model.User;
import by.bsuir.kostyademens.util.UserPathUtil;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private final SimpleStorageService storageService;

    public void rename(FileRenameDto item) {

        item.setNewPath(getFilePrefix(item.getOldPath()) + item.getNewPath());

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

    public void upload(MultipartFile file, String key, User user) {
        key = UserPathUtil.getUserRootPassword(user.getId()) + key;

        storageService.uploadFile(file, key);
    }

    private String getFilePrefix(String path) {
        return path.substring(0, path.lastIndexOf("/") + 1);
    }
}
