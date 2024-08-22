package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.dto.FolderCreateDto;
import by.bsuir.kostyademens.dto.FolderRenameDto;
import by.bsuir.kostyademens.dto.ItemDeleteDto;
import by.bsuir.kostyademens.model.path.ItemPath;
import by.bsuir.kostyademens.model.security.SecureUserDetails;
import by.bsuir.kostyademens.service.FolderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String add(@ModelAttribute FolderCreateDto folder,
                      @AuthenticationPrincipal SecureUserDetails userDetails) {

        folderService.createFolder(folder, userDetails.getUser());

        ItemPath path = new ItemPath(folder.getFolderLocation());
        String params = path.getPathWithoutUserFolder();

        return "redirect:/" + ((params.isEmpty() ? "" : "?path=" + params));
    }
}
