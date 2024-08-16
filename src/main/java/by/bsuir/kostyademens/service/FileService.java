package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.FileRenameDto;
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

    private String getFilePrefix(String path) {
        return path.substring(0, path.lastIndexOf("/") + 1);
    }
}
