package by.bsuir.kostyademens.dto.folder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FolderRenameDto {
    private String oldPath;

    private String newName;
}
