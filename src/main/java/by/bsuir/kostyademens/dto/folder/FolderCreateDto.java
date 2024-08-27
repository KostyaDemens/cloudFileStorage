package by.bsuir.kostyademens.dto.folder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FolderCreateDto {

    @Pattern(regexp = "^[a-zA-Z! -]*$", message = "Folder name should contain only english letters")
    @NotEmpty
    private String name;

    private String folderLocation;

    private Long ownerId;
}
