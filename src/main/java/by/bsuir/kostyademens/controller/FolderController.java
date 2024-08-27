package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.dto.file.FileUploadDto;
import by.bsuir.kostyademens.dto.folder.FolderCreateDto;
import by.bsuir.kostyademens.dto.item.ItemDeleteDto;
import by.bsuir.kostyademens.dto.item.ItemRenameDto;
import by.bsuir.kostyademens.exception.FolderAlreadyExistsException;
import by.bsuir.kostyademens.model.path.ItemPath;
import by.bsuir.kostyademens.service.FolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/add")
    public String getUploadForm(@ModelAttribute FolderCreateDto folderDto,
                                Model model) {

        model.addAttribute("folderDto", folderDto);
        return "create";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("folderDto") @Valid FolderCreateDto folder,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {

        ItemPath path = new ItemPath(folder.getFolderLocation());
        String params = path.getPathWithoutUserAndCurrentFolder();

        if (bindingResult.hasErrors()) {
            return "create";
        }

        try {
            folderService.createFolder(folder);
        } catch (FolderAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Folder with such name already exists");
            return "redirect:/folder/add?" + folder.getFolderLocation() + folder.getOwnerId();
        }

        return "redirect:/" + ((params.isEmpty() ? "" : "?path=" + params));


    }


    @PostMapping("/upload")
    public String upload(@RequestParam("files") List<MultipartFile> files,
                         @ModelAttribute FileUploadDto fileUpload) {

        folderService.upload(files, fileUpload);

        return "redirect:/" + ((fileUpload.getPath().isEmpty() ? "" : "?path=" + fileUpload.getPath()));
    }
}
