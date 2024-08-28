package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.dto.file.FileUploadDto;
import by.bsuir.kostyademens.dto.folder.FolderCreateDto;
import by.bsuir.kostyademens.dto.item.ItemDeleteDto;
import by.bsuir.kostyademens.dto.item.ItemRenameDto;
import by.bsuir.kostyademens.exception.EmptyFolderException;
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


    @GetMapping("/rename")
    public String getRenameForm(@ModelAttribute ItemRenameDto renameDto,
                                Model model) {

        if (!model.containsAttribute("renameDto")) {
            model.addAttribute("renameDto", renameDto);
        }

        return "component/folderRename";
    }

    @PatchMapping("/rename")
    public String rename(@ModelAttribute("renameDto") @Valid ItemRenameDto renameDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        ItemPath path = new ItemPath(renameDto.getOldPath());
        String params = path.getPathWithoutUserAndCurrentFolder();


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("renameDto", renameDto);
            return "component/folderRename";
        }

        try {
            folderService.rename(renameDto);
            return "redirect:/" + ((params.isEmpty() ? "" : "?path=" + params));
        } catch (FolderAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "File with such name already exist");
            redirectAttributes.addFlashAttribute("renameDto", renameDto);
            return "redirect:/folder/rename?" + renameDto.getOldPath();
        }

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

        if (!model.containsAttribute("folderDto")) {
            model.addAttribute("folderDto", folderDto);
        }
        return "component/create";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("folderDto") @Valid FolderCreateDto folder,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("folderDto", folder);
            return "component/create";
        }

        try {
            folderService.createFolder(folder);
        } catch (FolderAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Folder with such name already exists");
            redirectAttributes.addFlashAttribute("folderDto", folder);
            return "redirect:/folder/add?" + folder.getFolderLocation() + folder.getOwnerId();
        }

        ItemPath path = new ItemPath(folder.getFolderLocation());
        String params = path.getPathWithoutUserAndCurrentFolder();

        return "redirect:/" + ((params.isEmpty() ? "" : "?path=" + params));
    }

    @GetMapping("/upload")
    public String getUploadForm(@ModelAttribute FileUploadDto fileUploadDto,
                                Model model) {

        if (!model.containsAttribute("folderUpload")) {
            model.addAttribute("folderUpload", fileUploadDto);
        }

        return "component/folderUpload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("files") List<MultipartFile> files,
                         @ModelAttribute FileUploadDto fileUpload,
                         RedirectAttributes redirectAttributes) {

        try {
            folderService.upload(files, fileUpload);
        } catch (EmptyFolderException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("folderUpload", fileUpload);
            return "redirect:/folder/upload?" + fileUpload.getPath() + fileUpload.getOwnerId();
        }

        return "redirect:/" + ((fileUpload.getPath().isEmpty() ? "" : "?path=" + fileUpload.getPath()));
    }
}
