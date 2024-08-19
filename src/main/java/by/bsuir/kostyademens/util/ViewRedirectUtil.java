package by.bsuir.kostyademens.util;

import by.bsuir.kostyademens.model.path.ItemPath;

public class ViewRedirectUtil {

    public static String buildUrl(String path) {
        ItemPath itemPath = new ItemPath(path);
        String param = itemPath.getPathWithoutUserFolder();

        if (param.isEmpty()) {
            return "redirect:/";
        } else {
            return "redirect:/?path=" + param;
        }
    }
}
