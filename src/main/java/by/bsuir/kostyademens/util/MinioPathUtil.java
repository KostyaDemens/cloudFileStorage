package by.bsuir.kostyademens.util;

public class MinioPathUtil {

    public static String getUserRootPassword(Long id) {
        return "user-" + id + "-files/";
    }
}
