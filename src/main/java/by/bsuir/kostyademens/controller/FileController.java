package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.dto.ItemDownloadDto;
import by.bsuir.kostyademens.dto.FileRenameDto;
import by.bsuir.kostyademens.service.FileService;
import by.bsuir.kostyademens.service.SimpleStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
