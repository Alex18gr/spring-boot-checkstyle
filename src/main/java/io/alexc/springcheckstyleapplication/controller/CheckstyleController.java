package io.alexc.springcheckstyleapplication.controller;

import io.alexc.springcheckstyleapplication.checkstyle.CheckstyleRunner;
import io.alexc.springcheckstyleapplication.checkstyle.result.CheckstyleResult;
import io.alexc.springcheckstyleapplication.responseentity.UploadResponseMessage;
import io.alexc.springcheckstyleapplication.service.ZipFileService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
public class CheckstyleController {

    @Autowired
    private ZipFileService zipFileService;

    @PostMapping("/check-zip")
    public ResponseEntity<CheckstyleResult> checkZipProject(@RequestParam("file") MultipartFile file) {

        // unzip the received file to a temporary location
        Path path = zipFileService.unzipToTempDirectory(file);

        // execute the style checks
        CheckstyleRunner checkstyleRunner = new CheckstyleRunner(path);
        CheckstyleResult result = checkstyleRunner.run();

        // return the check results as response body
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

}
