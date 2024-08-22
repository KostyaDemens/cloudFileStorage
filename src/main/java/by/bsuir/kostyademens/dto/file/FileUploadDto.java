package by.bsuir.kostyademens.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadDto {

    private Long ownerId;

    private String path;
}
