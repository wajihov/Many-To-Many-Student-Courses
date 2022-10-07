package fr.gopartner.core.utils;

import java.util.List;

public class CollectionUtils {

    private CollectionUtils() {
    }

    public static boolean isNullOrEmpty(List<? extends Object> list) {
        return list == null || list.isEmpty();
    }
}
