package by.bsuir.kostyademens.dto.item;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ItemRenameDto implements Serializable {

    private String oldPath;

    @Pattern(regexp = "^[a-zA-Z! -]*$", message = "File name should contain only english letters")
    @NotEmpty
    private String newPath;
}
