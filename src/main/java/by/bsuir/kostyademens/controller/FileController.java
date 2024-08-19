package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.dto.ItemDeleteDto;
import by.bsuir.kostyademens.dto.ItemDownloadDto;
import by.bsuir.kostyademens.dto.FileRenameDto;
import by.bsuir.kostyademens.model.path.ItemPath;
import by.bsuir.kostyademens.service.FileService;
import by.bsuir.kostyademens.service.SimpleStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

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
            //TODO Обработать исключение
            throw new RuntimeException();
        }
    }

    @PatchMapping("/rename")
    public void rename(@ModelAttribute FileRenameDto item) {
        fileService.rename(item);
        //TODO Подумать над тем, как можно оставаться на текущей странице
    }

    @DeleteMapping("/delete")
    public String delete(@ModelAttribute ItemDeleteDto item) {
        storageService.deleteFile(item.getFullPath());

        ItemPath path = new ItemPath(item.getFullPath());

        String params = path.getPathWithoutUserFolder();

        if (params.isEmpty()) {
            return "redirect:/";
        } else {
            return "redirect:/?path=" + params;
        }

        //TODO удаляется папка, если в ней находится только один файл
    }
}
