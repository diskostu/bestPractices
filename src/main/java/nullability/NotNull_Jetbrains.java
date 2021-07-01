package nullability;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.Util;

/**
 * <p>dependency needed: {@code org.jetbrains:annotations}</p>
 *
 * <p>The annotation {@link NotNull} is mainly being used for static code analysis.</p>
 *
 * <p>
 * The behaviour of IntelliJ for this annotation is configured via these inspections:
 *     <ul>
 *         <li>{@code Java | Probable bugs | Nullability problems | @NotNull/@Nullable problems}</li>
 *         <li>{@code Java | Probable bugs | Constant conditions & exceptions}</li>
 *     </ul>
 * </p>
 * <p>
 * As said before, this is used for static analysis, and IntelliJ is doing just fine (see method examples in the
 * class). Furthermore, it is possible to add assertions while compiling, so that runtime errors may occur when a
 * {@link NotNull} contract is violated (see the official docs). But because of this manual step (configuring the
 * compiler), this is not recommended.
 *
 * @see <a href="https://www.jetbrains.com/help/idea/nullable-and-notnull-annotations.html">official docs</a>
 */
@Log4j2
public class NotNull_Jetbrains {

    /**
     * {@link NotNull} can not only be used at methods and method parameters, but also on fields and local variables.
     * Again, this is for static analysis only.
     */
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    @NotNull
    private final String fieldShouldNotBeNull;


    public NotNull_Jetbrains() {
        // a warning should appear, if "Java | Probable bugs | Constant conditions & exceptions" is enabled
        this.fieldShouldNotBeNull = null;
    }


    public static void main(String[] args) {
        // works just fine
        methodWithNotNullParameter("hello");

        // a warning should appear, if "Java | Probable bugs | Constant conditions & exceptions" is enabled

        // additionally, if "Add runtime assertions" under
        // "File | Settings | Build, Execution, Deployment | Compiler" is enabled for
        // org.jetbrains.annotations.NotNull, this call will result in a IllegalArgumentException - when running
        // from IntelliJ.
        methodWithNotNullParameter(null);


        // because of the fact that methodReturnsAValue() is annotated with @NotNull - and only because of this -
        // IntelliJ says:
        // "Condition 'methodReturnsAValue("hello") == null' is always 'false'"
        // be careful. IntelliJ does not really check if the called method is really returning a non-null value.
        // It *could* return null, but as IntelliJ cannot check this, it only checks the annotation.
        if (methodReturnsAValue("hello") == null) {
            log.info("this should not happen");
        }
    }


    private static void methodWithNotNullParameter(@NotNull final String s) {
        log.info("s = " + s);
    }


    /**
     * Because of the @{@link NotNull} annotation, every caller of this method will get a warning if it does a null
     * check on the return value. Because in IntelliJ's opinion, a null check is not necessary.
     * <p>
     * But, this method can of course return {@code null} (see code below). So be careful with the IntelliJ warnings
     * here.
     */
    @NotNull
    private static String methodReturnsAValue(final String s) {
        // although we annotated this method with @NotNull, it is possible that it returns null because of this
        // (intentionally) shitty code.

        // IntelliJ says:
        // "Expression 'Util.throwACoin(null, "hello")' might evaluate to null but is returned by the method declared
        // as @NotNull"
        // corresponding setting: Java | Probable bugs | Constant conditions & exceptions
        // press "Alt + Enter" on the method to see some possible quick fixes
        return Util.throwACoin(null, s);
    }


    private static void localFieldIsNotNull(@Nullable final String s) {
        // IntelliJ says:
        // "Expression 's' might evaluate to null but is assigned to a variable that is annotated with @NotNull"
        @NotNull String s2 = s;

        // no warning is triggered here, despite the annotation @NotNull on the local variable. This is because the
        // method parameter is annotated with @Nullable, and IntelliJ keeps track of the data flow.
        // If it was annotated with @NotNull too, we would see a warning here (try it :))
        if (s2 == null) {
            log.info("s2 is null");
        }
    }
}
