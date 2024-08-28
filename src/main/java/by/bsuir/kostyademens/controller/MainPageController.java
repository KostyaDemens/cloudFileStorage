package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.dto.file.FileSearchDto;
import by.bsuir.kostyademens.dto.item.ItemDto;
import by.bsuir.kostyademens.model.security.SecureUserDetails;
import by.bsuir.kostyademens.service.ItemService;
import by.bsuir.kostyademens.service.SimpleStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
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
        LinkedHashMap<String, String> breadCrumbs = itemService.getBreadCrumbs();

        model.addAttribute("itemDto", items);
        model.addAttribute("breadcrumbs", breadCrumbs);
        model.addAttribute("owner", userDetails.getUser());
        model.addAttribute("url", path);

        return "main";
    }

    @GetMapping("/search/")
    public String search(@RequestParam(value = "query") String query,
                         @AuthenticationPrincipal SecureUserDetails userDetails,
                         Model model) {

        if (query.isEmpty()) {
            return "redirect:/";
        }

        List<FileSearchDto> files = itemService.search(query.toLowerCase(), userDetails.getUser());

        model.addAttribute("files", files);

        return "search";
    }

}
