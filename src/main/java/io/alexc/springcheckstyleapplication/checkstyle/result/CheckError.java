package io.alexc.springcheckstyleapplication.checkstyle.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CheckError {

    private String sourceName;
    private String message;
    private String fileName;
    private Integer line;
    private Integer column;

}
