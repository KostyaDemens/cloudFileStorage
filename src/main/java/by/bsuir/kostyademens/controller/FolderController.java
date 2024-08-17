package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.dto.FileRenameDto;
import by.bsuir.kostyademens.dto.FolderRenameDto;
import by.bsuir.kostyademens.dto.ItemDeleteDto;
import by.bsuir.kostyademens.model.security.SecureUserDetails;
import by.bsuir.kostyademens.service.FileService;
import by.bsuir.kostyademens.service.FolderService;
import by.bsuir.kostyademens.service.SimpleStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;


    @PatchMapping("/rename")
    public void rename(@ModelAttribute FolderRenameDto folder) {
        folderService.rename(folder);

        //TODO то же самое, что и в файл контроллере
    }

    @DeleteMapping("/delete")
    public void delete(@ModelAttribute ItemDeleteDto item) {
        folderService.delete(item);
    }
}
