package by.bsuir.kostyademens.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ItemDto {

    private String name;
    private boolean isDir;
}
