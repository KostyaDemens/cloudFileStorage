package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.FileRenameDto;
import by.bsuir.kostyademens.dto.ItemDeleteDto;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private String getFilePrefix(String path) {
        return path.substring(0, path.lastIndexOf("/") + 1);
    }
}
