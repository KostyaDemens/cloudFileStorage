package by.bsuir.kostyademens.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FolderRenameDto {

    private String oldPath;

    private String newName;

    private String folderPath;
}
