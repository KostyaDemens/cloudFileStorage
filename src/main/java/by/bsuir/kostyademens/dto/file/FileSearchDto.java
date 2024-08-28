package by.bsuir.kostyademens.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FileSearchDto {

    private String path;
    private String name;
}
