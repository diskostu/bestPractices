package nullability.temp;

import org.jetbrains.annotations.NotNull;

/**
 * TODO Beschreibung
 *
 * @author denny.kluge@axa.de
 */
public class Demo {

    public static void main(String[] args) {
        lala(null);
    }


    private static void lala(@NotNull String s) {
        System.out.println("s = " + s);
    }
}
