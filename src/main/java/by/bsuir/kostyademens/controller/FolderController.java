package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.dto.file.FileUploadDto;
import by.bsuir.kostyademens.dto.folder.FolderCreateDto;
import by.bsuir.kostyademens.dto.item.ItemDeleteDto;
import by.bsuir.kostyademens.dto.item.ItemRenameDto;
import by.bsuir.kostyademens.model.path.ItemPath;
import by.bsuir.kostyademens.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;


    @PatchMapping("/rename")
    public String rename(@ModelAttribute ItemRenameDto item) {
        folderService.rename(item);

        ItemPath path = new ItemPath(item.getNewPath());
        String params = path.getPathWithoutUserAndCurrentFolder();

        return "redirect:/" + ((params.isEmpty() ? "" : "?path=" + params));
    }

    @DeleteMapping("/delete")
    public String delete(@ModelAttribute ItemDeleteDto item) {
        folderService.delete(item);

        ItemPath path = new ItemPath(item.getFullPath());
        String params = path.getPathWithoutUserAndCurrentFolder();

        return "redirect:/" + ((params.isEmpty() ? "" : "?path=" + params));
    }

    @PostMapping("/add")
    public String add(@ModelAttribute FolderCreateDto folder) {

        folderService.createFolder(folder);

        ItemPath path = new ItemPath(folder.getFolderLocation());
        String params = path.getPathWithoutUserAndCurrentFolder();

        return "redirect:/" + ((params.isEmpty() ? "" : "?path=" + params));
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("files") List<MultipartFile> files,
                         @ModelAttribute FileUploadDto fileUpload) {

        folderService.upload(files, fileUpload);

        return "redirect:/" + ((fileUpload.getPath().isEmpty() ? "" : "?path=" + fileUpload.getPath()));
    }
}
