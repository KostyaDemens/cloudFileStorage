package by.bsuir.kostyademens.dto.folder;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FolderCreateDto {

    private String name;

    private String folderLocation;

    private Long ownerId;
}
