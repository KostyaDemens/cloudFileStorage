package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.service.SimpleStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    private final SimpleStorageService storageService;

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

//    @PostMapping("/add")
//    public String addFile(@RequestParam ("file") MultipartFile file) {
//        storageService.uploadFile(file);
//        return "redirect:/main";
//    }

}
