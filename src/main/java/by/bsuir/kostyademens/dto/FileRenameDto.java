package by.bsuir.kostyademens.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileRenameDto {

    private String oldName;
    private String newName;
    private String path;
}
