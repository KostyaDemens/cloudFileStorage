package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.dto.file.FileUploadDto;
import by.bsuir.kostyademens.dto.item.ItemDeleteDto;
import by.bsuir.kostyademens.dto.item.ItemDownloadDto;
import by.bsuir.kostyademens.dto.item.ItemRenameDto;
import by.bsuir.kostyademens.model.path.ItemPath;
import by.bsuir.kostyademens.service.FileService;
import by.bsuir.kostyademens.service.SimpleStorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final SimpleStorageService storageService;
    private final FileService fileService;

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@ModelAttribute ItemDownloadDto item) {
        try {
            InputStream stream = storageService.getFile(item.getPath());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
            httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(item.getName()).build().toString());
            return ResponseEntity.ok().headers(httpHeaders).body(stream.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @GetMapping("/rename")
    public String getRenameForm(@ModelAttribute ItemRenameDto renameDto,
                                Model model) {

        if (!model.containsAttribute("renameDto")) {
            model.addAttribute("renameDto", renameDto);
        }

        return "component/fileRename";
    }

    @PatchMapping("/rename")
    public String rename(@ModelAttribute("renameDto") @Valid ItemRenameDto renameDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        ItemPath path = new ItemPath(renameDto.getOldPath());
        String params = path.getPathWithoutUserAndCurrentFolder();


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("renameDto", renameDto);
            return "component/fileRename";
        }

        try {
            fileService.rename(renameDto);
            return "redirect:/" + ((params.isEmpty() ? "" : "?path=" + params));
        } catch (FileAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "File with such name already exist");
            redirectAttributes.addFlashAttribute("renameDto", renameDto);
            return "redirect:/file/rename?" + renameDto.getOldPath();
        }

    }

    @DeleteMapping("/delete")
    public String delete(@ModelAttribute ItemDeleteDto item) {
        fileService.delete(item);

        ItemPath path = new ItemPath(item.getFullPath());
        String params = path.getPathWithoutUserAndCurrentFolder();

        return "redirect:/" + ((params.isEmpty() ? "" : "?path=" + params));
    }

    @GetMapping("/upload")
    public String getUploadForm(@ModelAttribute FileUploadDto fileUploadDto,
                                Model model) {

        model.addAttribute("fileUpload", fileUploadDto);

        return "component/fileUpload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("files") List<MultipartFile> files,
                         @ModelAttribute FileUploadDto fileUpload) {

        fileService.upload(files, fileUpload);

        return "redirect:/" + ((fileUpload.getPath().isEmpty() ? "" : "?path=" + fileUpload.getPath()));
    }
}
