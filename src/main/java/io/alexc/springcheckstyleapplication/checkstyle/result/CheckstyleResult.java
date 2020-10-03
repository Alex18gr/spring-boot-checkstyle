package io.alexc.springcheckstyleapplication.checkstyle.result;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CheckstyleResult {

    private final List<CheckError> errors;

    public CheckstyleResult() {
        errors = new ArrayList<>();
    }

    public void addResult(CheckError error) {
        errors.add(error);
    }

}
