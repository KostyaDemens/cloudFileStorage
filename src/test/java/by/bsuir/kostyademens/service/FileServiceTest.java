package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.item.ItemRenameDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.nio.file.FileAlreadyExistsException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private ItemService itemService;

    @Mock
    private SimpleStorageService storageService;

    @Test
    void renameMethodShouldSetNewNameCorrectly() throws FileAlreadyExistsException {
        ItemRenameDto file = new ItemRenameDto("old/path/file.txt", "newfile");

        when(itemService.isItemAlreadyExist(anyString(), anyString())).thenReturn(false);
        fileService.rename(file);

        assertThat(file.getNewPath(), equalTo("old/path/newfile.txt"));
        verify(storageService, times(1)).renameFile(anyString(), anyString());
    }

}