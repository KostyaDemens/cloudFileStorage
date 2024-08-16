package by.bsuir.kostyademens.controller;

import by.bsuir.kostyademens.dto.FileRenameDto;
import by.bsuir.kostyademens.dto.FolderRenameDto;
import by.bsuir.kostyademens.model.security.SecureUserDetails;
import by.bsuir.kostyademens.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;


    @PatchMapping("/rename")
    public void rename(@ModelAttribute FolderRenameDto folder,
    @AuthenticationPrincipal SecureUserDetails userDetails) {
        folderService.rename(folder, userDetails.getUser());
    }
}
