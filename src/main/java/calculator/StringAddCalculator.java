package calculator;

import java.util.Objects;
import java.util.stream.Stream;

public class StringAddCalculator {

    private static final String DEFAULT_DELIMITERS_REGEXP = "[,:]";
    private static final String CUSTOM_DELIMITER_START = "//";
    private static final String CUSTOM_DELIMITER_END = "\\n";

    public static int add(String input) {
        if (Objects.isNull(input) || input.isBlank()) {
            return 0;
        }

        String[] numbers = parseNumberStrings(input);
        return sum(toPositives(numbers)).getNumber();
    }

    private static String[] parseNumberStrings(String input) {
        if (hasCustomDelimiter(input)) {
            return removeCustomDelimiter(input).split(extractCustomDelimiter(input));
        }

        return input.split(DEFAULT_DELIMITERS_REGEXP);
    }

    private static Positive[] toPositives(String[] numberStrings) {
        return Stream.of(numberStrings)
                     .map(Positive::new)
                     .toArray(Positive[]::new);
    }

    private static Positive sum(Positive[] numbers) {
        return Stream.of(numbers)
                     .reduce(new Positive(), Positive::sum);
    }

    private static boolean hasCustomDelimiter(String input) {
        if (Objects.isNull(input) || input.isBlank()) {
            return false;
        }

        return input.startsWith("//") && input.contains("\\n");
    }

    private static String extractCustomDelimiter(String input) {
        return input.substring(CUSTOM_DELIMITER_START.length(), input.indexOf(CUSTOM_DELIMITER_END));
    }

    private static String removeCustomDelimiter(String input) {
        return input.substring(input.indexOf(CUSTOM_DELIMITER_END) + CUSTOM_DELIMITER_END.length());
    }
}