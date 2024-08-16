package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.dto.ItemDto;
import by.bsuir.kostyademens.model.security.SecureUserDetails;
import by.bsuir.kostyademens.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainPageController {

    private final ItemService itemService;

    @GetMapping
    public String mainPage(
            @AuthenticationPrincipal SecureUserDetails userDetails,
            @RequestParam(value = "path", required = false) String path,
            Model model
    ) {

        List<ItemDto> items = itemService.findAllFiles(userDetails.getUser(), path);

        model.addAttribute("itemDto", items);

        return "main";
    }

//    @PostMapping("/add")
//    public String addFile(@RequestParam("file") MultipartFile file) {
//
//
//        return "redirect:/main";
//    }

}
