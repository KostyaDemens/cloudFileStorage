package by.bsuir.kostyademens.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ItemDto {

    private String name;

    private String path;
    private String fullPath;

    private boolean isDir;

    //TODO - добавить валидацю введеных названий папок и т.д
}
