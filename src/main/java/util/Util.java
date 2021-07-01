package util;

import org.jetbrains.annotations.Nullable;

public class Util {

    @Nullable
    public static String throwACoin(@Nullable final String upperside, @Nullable final String lowerside) {
        return System.currentTimeMillis() % 2 == 0 ? upperside : lowerside;
    }
}
