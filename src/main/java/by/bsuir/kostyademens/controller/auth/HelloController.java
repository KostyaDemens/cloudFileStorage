package by.bsuir.kostyademens.controller.auth;

import by.bsuir.kostyademens.service.SimpleStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HelloController {

    private final SimpleStorageService storageService;


    @GetMapping("/get-buckets")
    public String helloServlet(Model model) {
        model.addAttribute("buckets", storageService.getAllBuckets());
        return "hello";
    }
}
