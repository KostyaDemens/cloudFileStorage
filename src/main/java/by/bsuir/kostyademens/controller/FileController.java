package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.service.ItemService;
import by.bsuir.kostyademens.service.SimpleStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {


    private final SimpleStorageService storageService;
    private final ItemService itemService;
//    private final FolderService folderService;

//    @SneakyThrows
//    @GetMapping("download")
//    public ResponseEntity<byte[]> download(@ModelAttribute ItemDto itemDto) {
//        InputStream stream = storageService.getFile(itemDto.getFullPath());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
//        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(itemDto.getName()).build().toString());
//        return ResponseEntity.ok().headers(httpHeaders).body(stream.readAllBytes());
//    }
}
