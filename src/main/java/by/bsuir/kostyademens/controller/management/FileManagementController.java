package by.bsuir.kostyademens.controller.management;

import by.bsuir.kostyademens.dto.ItemDto;
import by.bsuir.kostyademens.service.SimpleStorageService;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class FileManagementController {

    private final SimpleStorageService storageService;


    @GetMapping("download")
    public String downloadFile(@ModelAttribute ItemDto itemDto) {
        storageService.downloadFile(itemDto);
        return "main";
    }
}
