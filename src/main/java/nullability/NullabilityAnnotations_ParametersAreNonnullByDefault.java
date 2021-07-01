package nullability;

import lombok.extern.log4j.Log4j2;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * <p>Hier wird auf Klassenebene die Annotation {@link ParametersAreNonnullByDefault} gesetzt (dependency notwendig: {@code com.google.code.findbugs:jsr305}). Diese Annotation
 * bewirkt, dass die Parameter aller Methoden als "duerfen nicht null sein" markiert werden. Doch Achtung: im Gegensatz zu {@link org.jetbrains.annotations.NotNull} oder
 * {@link lombok.NonNull} wirkt sich diese Markierung nur auf statische Codeanalys aus. Daher ist diese Annotation nicht empfohlen und ist hier nur der Vollstaendigkeit halber
 * erwaehnt.</p>
 *
 * <p>Durch die Annotation erhaelt man warnings in der IDE (setting: {@code Java | Probable bugs | Constant conditions & exceptions}), jedoch kann man den Compiler leider
 * nicht dazu bringen, da eine Assertion hinzuzufuegen - zur Laufzeit wird also keine Exception fliegen.</p>
 * <p>Man kann zwar hier {@code File | Settings | Build, Execution, Deployment | Compiler} bei {@code NotNull} die Annotation {@link ParametersAreNonnullByDefault} aufnehmen,
 * aber das macht keinen Unterschied. Bug bei IntelliJ?</p>
 *
 * @author denny.kluge@axa.de
 */
@Log4j2
@ParametersAreNonnullByDefault
public class NullabilityAnnotations_ParametersAreNonnullByDefault {

    public static void main(String[] args) {
        // normaler gueltiger Aufruf, alles gut
        nullability("Hallo");

        // erzeugt eine IDE-warning (setting: Java | Probable bugs | Constant conditions & exceptions)
        // zur Laufzeit jedoch keine Exception
        nullability(null);
    }


    /**
     * Hier ist der Parameter nicht extra annotiert; fuer die statische Codeanalyse zieht {@link ParametersAreNonnullByDefault} auf Klassenebene.
     */
    private static void nullability(final String s) {
        log.info("s: " + s);
    }
}