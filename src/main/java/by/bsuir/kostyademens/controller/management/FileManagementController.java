package by.bsuir.kostyademens.controller.management;

import by.bsuir.kostyademens.dto.ItemDto;
import by.bsuir.kostyademens.service.FileService;
import by.bsuir.kostyademens.service.SimpleStorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class FileManagementController {


    private final SimpleStorageService storageService;

    @SneakyThrows
    @GetMapping("download")
    public ResponseEntity<byte[]> download(@ModelAttribute ItemDto itemDto) {
        InputStream stream = storageService.getFile(itemDto.getFullPath());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(itemDto.getName()).build().toString());
        return ResponseEntity.ok().headers(httpHeaders).body(stream.readAllBytes());
    }


    @PatchMapping("rename")
    public String rename(@ModelAttribute ItemDto itemDto, @RequestParam String newName) {

        return "main";
    }
}
