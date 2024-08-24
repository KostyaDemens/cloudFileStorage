package by.bsuir.kostyademens.model;

import lombok.RequiredArgsConstructor;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.LinkedHashMap;

import static org.hamcrest.Matchers.equalTo;

class BreadCrumbTest {

    private static BreadCrumb breadCrumb;

    @BeforeAll
    static void setUp() {
        breadCrumb = new BreadCrumb();
    }

    @Test
    void shouldCreateBreadCrumbWithSimplePath() {
        String path = "user-6-files/";
        String userRootFolder = "user-6-files/";

        LinkedHashMap<String, String> result = breadCrumb.createBreadCrumbs(path, userRootFolder);

        LinkedHashMap<String, String> expected = new LinkedHashMap<>();
        expected.put("/", "Home");

        MatcherAssert.assertThat(result, equalTo(expected));
    }

    @Test
    void shouldCreateBreadCrumbWithCompletePath() {
        String path = "user-6-files/path/to/the/folder/";
        String userRootFolder = "user-6-files/";

        LinkedHashMap<String, String> result = breadCrumb.createBreadCrumbs(path, userRootFolder);

        LinkedHashMap<String, String> expected = new LinkedHashMap<>();
        expected.put("/", "Home");
        expected.put("path/", "path");
        expected.put("path/to/", "to");
        expected.put("path/to/the/", "the");
        expected.put("path/to/the/folder/", "folder");

        MatcherAssert.assertThat(result, equalTo(expected));
    }

}