package by.bsuir.kostyademens.model;

import by.bsuir.kostyademens.model.path.ItemPath;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class ItemPathTest {

    private static String userRootFolder;

    @BeforeAll
    static void setUp() {
        userRootFolder = "user-6-files";
    }

    @Test
    void getItemPathShouldReturnSlashIfItEqualsToUserRootFolder() {
        ItemPath itemPath = new ItemPath(userRootFolder);

        String path = itemPath.getItemPath(userRootFolder);

        MatcherAssert.assertThat(path, Matchers.equalTo("/"));
    }

    @Test
    void getItemPathShouldReturnPathIfItIsNotEqualsToUserRootFolder() {
        ItemPath itemPath = new ItemPath(userRootFolder + "path/to/the/folder/");

        String path = itemPath.getItemPath(userRootFolder);

        MatcherAssert.assertThat(path, Matchers.equalTo("path/to/the/folder/"));
    }



    @Test
    void getPathWithoutUserFolderShouldSubstringUserRootAndCurrentFolder() {
        ItemPath itemPath = new ItemPath(userRootFolder + "/path/to/the/folder/");

        String path = itemPath.getPathWithoutUserAndCurrentFolder();

        MatcherAssert.assertThat(path, Matchers.equalTo("path/to/the/"));
    }
}