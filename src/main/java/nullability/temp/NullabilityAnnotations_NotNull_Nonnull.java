package nullability.temp;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

/**
 * Hier werden verschiedene Anwendungsfaelle der Annotationen {@link NotNull} und {@link NonNull} aufgezeigt.
 */
@Log4j2
public class NullabilityAnnotations_NotNull_Nonnull {

    public static void main(String[] args) {
        checkParameterAnnotations();
        checkReturnAnnotations();
    }


    /**
     * Mit den Parameter-Annotations {@link NotNull} und {@link NonNull} koennen zwei Dinge gesteuert werden:
     * <ol>
     *     <li>statische Codeanalyse (z.B. fuer IDE, SpotBugs, ...)</li>
     *     <li>per Compiler hinzugefuegte Assertions, die das Laufzeitverhalten beeinflussen</li>
     * </ol>
     */
    private static void checkParameterAnnotations() {
        // normaler gueltiger Aufruf, alles gut
        parameter_JetBrainsAnnotation("Hallo");
        parameter_LombokAnnotation(("Hallo"));

        // jetzt folgen Aufrufe mit null - das ist fuer die gerufenen Methoden nicht erlaubt und wird per Methodenparameter-Annotation verhindert.

        // IDE beschwert sich hier bereits, wenn folgendes setting aktiv ist: Java | Probable bugs | Constant conditions & exceptions
        // je nach Wunsch kann die IDE mit warning oder error reagieren
        parameter_JetBrainsAnnotation(null);

        // auch hier beschwert sich die IDE, wenn das o.g. setting aktiv ist
        parameter_LombokAnnotation(null);
    }


    private static void checkReturnAnnotations() {
        // gueltiger Aufruf der Methode
        final String returnStringJetBrains1 = return_NotNull_JetBrains("Hallo");
        // ungueltiger Aufruf der Methode: obwohl der Methodenparameter nicht mit NotNull/NonNull annotiert ist, erkennt die IDE
        final String returnStringJetBrains2 = return_NotNull_JetBrains(null);
        // IDE zeigt eine warning an
        if (returnStringJetBrains1 == null) {
            log.info("returnStringJetBrains: " + returnStringJetBrains1);
        }

        // IDE zeigt eine hier ebenfalls eine warning an
        final String returnStringLombok = return_NotNull_Lombok(null);
        if (returnStringLombok == null) {
            log.info("returnStringLombok: " + returnStringLombok);
        }
    }


    /**
     * Benutzt {@link NotNull} (JetBrains) fuer den Parameter. Wenn die Methode mit {@code null} aufgerufen wird, fliegt eine {@link IllegalArgumentException}.
     */
    private static void parameter_JetBrainsAnnotation(@NotNull final String s) {
        log.info("s: " + s);
    }


    /**
     * Benutzt {@link NonNull} (Lombok) fuer den Parameter. Wenn die Methode mit {@code null} aufgerufen wird, fliegt eine {@link NullPointerException}.
     */
    private static void parameter_LombokAnnotation(@NonNull final String s) {
        log.info("s: " + s);
    }


    @NotNull private static String return_NotNull_JetBrains(final String s) {
        return s;
    }


    @NonNull private static String return_NotNull_Lombok(final String s) {
        return s;
    }
}
