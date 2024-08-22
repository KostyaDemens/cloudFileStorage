package by.bsuir.kostyademens.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDownloadDto {

    private String path;
    private String name;

    //TODO все item-ы унаследовать от родительского ItemDTO
}
