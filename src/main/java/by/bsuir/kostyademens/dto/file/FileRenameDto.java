package by.bsuir.kostyademens.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class FileRenameDto {

    private String oldPath;

    private String newPath;
}
