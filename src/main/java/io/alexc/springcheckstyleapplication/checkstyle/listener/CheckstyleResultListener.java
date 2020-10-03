package io.alexc.springcheckstyleapplication.checkstyle.listener;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import io.alexc.springcheckstyleapplication.checkstyle.result.CheckError;
import io.alexc.springcheckstyleapplication.checkstyle.result.CheckstyleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckstyleResultListener implements AuditListener {

    private final Logger logger = LoggerFactory.getLogger(CheckstyleResultListener.class);
    private final CheckstyleResult results = new CheckstyleResult();

    @Override
    public void auditStarted(AuditEvent auditEvent) {
        logger.info("Starting audit...");
    }

    @Override
    public void auditFinished(AuditEvent auditEvent) {
        logger.info("Audit finished.");
    }

    @Override
    public void fileStarted(AuditEvent auditEvent) {
        logger.info("Auditing file {}...", auditEvent.getFileName());
    }

    @Override
    public void fileFinished(AuditEvent auditEvent) {
        logger.info("Auditing file {} finished.", auditEvent.getFileName());
    }

    @Override
    public void addError(AuditEvent auditEvent) {

        logger.info("Validation error {}: In {}. {}:{}", auditEvent.getSourceName(),
                auditEvent.getFileName(),
                auditEvent.getLine(),
                auditEvent.getColumn());

        results.addResult(new CheckError(
                auditEvent.getSourceName(),
                auditEvent.getMessage(),
                auditEvent.getFileName(),
                auditEvent.getLine(),
                auditEvent.getColumn()
        ));

    }

    @Override
    public void addException(AuditEvent auditEvent, Throwable throwable) {
        logger.error("Exception while audit: {}", throwable.getMessage());
    }

    public CheckstyleResult getResult() {
        return results;
    }
}
