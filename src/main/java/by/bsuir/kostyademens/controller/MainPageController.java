package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.service.SimpleStorageService;
import by.bsuir.kostyademens.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainPageController {

    private final SimpleStorageService storageService;
    private final UserService userService;

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute(
                "file",
                storageService.getAllFiles(userService.getUserFromContext()
                )
        );


        return "main";
    }

    @PostMapping("/add")
    public String addFile(@RequestParam("file") MultipartFile file) {


        return "redirect:/main";
    }

}
