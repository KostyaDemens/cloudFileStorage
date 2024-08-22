package by.bsuir.kostyademens.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FolderCreateDto {

    private String name;

    private String folderLocation;
}
