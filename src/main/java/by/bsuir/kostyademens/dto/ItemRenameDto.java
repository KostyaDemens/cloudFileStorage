package by.bsuir.kostyademens.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ItemRenameDto {

    private String oldPath;

    private String newName;
}
