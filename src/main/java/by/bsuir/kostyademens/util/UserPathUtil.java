package by.bsuir.kostyademens.util;

public class UserPathUtil {

    public static String getUserRootPassword(Long id) {
        return "user-" + id + "-files/";
    }
}
