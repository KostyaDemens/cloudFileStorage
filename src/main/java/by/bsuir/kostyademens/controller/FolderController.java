package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.dto.folder.FolderCreateDto;
import by.bsuir.kostyademens.dto.folder.FolderRenameDto;
import by.bsuir.kostyademens.dto.item.ItemDeleteDto;
import by.bsuir.kostyademens.model.path.ItemPath;
import by.bsuir.kostyademens.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;


    @PatchMapping("/rename")
    public String rename(@ModelAttribute FolderRenameDto folder) {
        folderService.rename(folder);

        ItemPath path = new ItemPath(folder.getNewName());
        String params = path.getPathWithoutUserFolder();

        return "redirect:/" + ((params.isEmpty() ? "" : "?path=" + params));
    }

    @DeleteMapping("/delete")
    public String delete(@ModelAttribute ItemDeleteDto item) {
        folderService.delete(item);

        ItemPath path = new ItemPath(item.getFullPath());
        String params = path.getPathWithoutUserFolder();

        return "redirect:/" + ((params.isEmpty() ? "" : "?path=" + params));
    }

    @PostMapping("/add")
    public String add(@ModelAttribute FolderCreateDto folder) {

        folderService.createFolder(folder);

        ItemPath path = new ItemPath(folder.getFolderLocation());
        String params = path.getPathWithoutUserFolder();

        return "redirect:/" + ((params.isEmpty() ? "" : "?path=" + params));
    }
}
