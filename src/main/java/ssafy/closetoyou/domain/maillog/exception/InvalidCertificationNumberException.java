package ssafy.closetoyou.domain.maillog.exception;

import ssafy.closetoyou.global.error.exception.CloseToYouException;
import ssafy.closetoyou.global.error.exception.ErrorCode;

public class InvalidCertificationNumberException extends CloseToYouException {
    public static final CloseToYouException EXCEPTION = new InvalidCertificationNumberException();

    private InvalidCertificationNumberException() {
        super(ErrorCode.INVALID_CERTIFICATION_NUMBER);
    }
}
