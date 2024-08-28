package by.bsuir.kostyademens.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class FileUploadDto implements Serializable {

    private Long ownerId;

    private String path;
}
