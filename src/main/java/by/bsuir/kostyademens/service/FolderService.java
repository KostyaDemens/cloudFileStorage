package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.FolderRenameDto;
import by.bsuir.kostyademens.dto.ItemDto;
import by.bsuir.kostyademens.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final SimpleStorageService storageService;
    private final ItemService itemService;
    private final FileService fileService;


    public void rename(FolderRenameDto folder, User user) {

        List<ItemDto> items = itemService.findAllFiles(user, folder.getFolderPath());

        for (ItemDto item : items) {
            if (item.isDir()) {
                String newPath = getFolderPrefix(folder.getOldPath()) + folder.getNewName() + "/";
                rename(new FolderRenameDto(
                        item.getFullPath(),
                        newPath + item.getName() + "/",
                        item.getPath()), user
                );
            }
            storageService.renameFile(
                    getFolderPrefix(folder.getOldPath()) + folder.getNewName() + "/" + item.getName(),
                    item.getFullPath()
            );
        }
    }

    private String getFolderPrefix(String path) {
        path = StringUtils.chop(path);
        return path.substring(0, path.lastIndexOf("/") + 1);
    }

}
