package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.ItemDto;
import by.bsuir.kostyademens.dto.ItemRenameDto;
import by.bsuir.kostyademens.model.MinioPath;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {


    private final FileService fileService;
    private final SimpleStorageService storageService;


    public void rename(ItemRenameDto itemRenameDto) {
        String path = fileService.getPathToTheFileWithoutName(itemRenameDto.getOldPath());

        List<ItemDto> items = fileService.findAllFiles(new MinioPath(itemRenameDto.getOldPath().substring(path.length())));

        for (ItemDto item : items) {
            storageService.renameFile(
                    path + itemRenameDto.getNewName() + "/" + item.getName(),
                    item.getFullPath()
            );
        }
    }
}
