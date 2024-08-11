package by.bsuir.kostyademens.controller.main;

import by.bsuir.kostyademens.service.FileService;
import by.bsuir.kostyademens.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainPageController {

    private final FileService fileService;
    private final UserService userService;

    @GetMapping
    public String mainPage(Model model, @RequestParam(value = "path", required = false) String path) {

        model.addAttribute(
                "item", fileService.findAllFilesFromRoot(userService.getUserFromContext(), path)
        );

        System.out.println(path);

        return "main";
    }

//    @GetMapping("")
//    public String switchFolder(Model model, @RequestParam(value = "path") String prefix) {
//        model.addAttribute("file",
//                fileService.findAllFilesFromSubfolder(userService.getUserFromContext(), prefix));
//        return "main";
//    }

//    @PostMapping("/add")
//    public String addFile(@RequestParam("file") MultipartFile file) {
//
//
//        return "redirect:/main";
//    }

}
