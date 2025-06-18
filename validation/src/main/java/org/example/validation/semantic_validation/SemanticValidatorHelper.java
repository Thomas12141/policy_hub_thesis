package org.example.validation.semantic_validation;

import org.example.validation.semantic_validation.model.Constraint;
import org.example.validation.semantic_validation.model.Duty;
import org.example.validation.semantic_validation.model.Permission;
import org.example.validation.semantic_validation.model.Prohibition;

import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

public class SemanticValidatorHelper {

    private SemanticValidatorHelper() {
    }

    protected static final Set<String> SUPPORTED_LOCALES;

    static {
        SUPPORTED_LOCALES = new HashSet<>(Arrays.stream(Locale.
            getISOCountries()).map(String::toUpperCase).toList());

        SUPPORTED_LOCALES.add("EU");
    }

    public static boolean isSupportedLocale(String rightValue) {
        return SUPPORTED_LOCALES.contains(rightValue.toUpperCase());
    }

    public static boolean areDatesTimesValid(List<Prohibition> prohibitions, List<Permission> permissions, List<Duty> duties) {
        List<Constraint> constraints = Stream.of(prohibitions, permissions, duties).
                flatMap(Collection::stream).flatMap(rule -> rule.getConstraints().stream()).toList();

        constraints = constraints.stream().filter(constraint -> constraint.getLeftOperand().equals("DateTime")).toList();



        List<Constraint> equalsBiggerThen = constraints.stream().filter(constraint -> constraint.getOperator().matches("gt|gteq")).toList();

        List<Constraint> equalsSmallerThen = constraints.stream().filter(constraint -> constraint.getOperator().matches("lt|lteq")).toList();

        if(equalsSmallerThen.size() > 1 || equalsBiggerThen.size() > 1) {
            return false;
        }

        if(constraints.size() < 2 || equalsBiggerThen.isEmpty() || equalsSmallerThen.isEmpty()) {
            return true;
        }

        Instant bottomLimit = Instant.parse(equalsBiggerThen.getFirst().getRightOperand());

        Instant topLimit = Instant.parse(equalsSmallerThen.getFirst().getRightOperand());

        return topLimit.isAfter(bottomLimit);
    }

    public static boolean isValueGreaterThenZero(String rightValue) {
        Double value;
        try {
            value = Double.parseDouble(rightValue);
        } catch (NumberFormatException e) {
            return false;
        }

        return value > 0;
    }
}
