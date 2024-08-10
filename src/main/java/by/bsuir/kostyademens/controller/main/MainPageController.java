package by.bsuir.kostyademens.controller.main;

import by.bsuir.kostyademens.service.FileService;
import by.bsuir.kostyademens.service.SimpleStorageService;
import by.bsuir.kostyademens.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainPageController {

    private final FileService fileService;
    private final UserService userService;

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute(
                "file", fileService.findAllFilesFromRoot(userService.getUserFromContext())
        );


        return "main";
    }

    @GetMapping("")
    public String switchFolder(Model model, @RequestParam(value = "path") String prefix) {
        model.addAttribute("file",
                fileService.findAllFilesFromSubfolder(userService.getUserFromContext(), prefix));
        return "main";
    }

//    @PostMapping("/add")
//    public String addFile(@RequestParam("file") MultipartFile file) {
//
//
//        return "redirect:/main";
//    }

}
