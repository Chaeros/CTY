package ssafy.closetoyou.domain.maillog.exception;

import ssafy.closetoyou.global.error.exception.CloseToYouException;
import ssafy.closetoyou.global.error.exception.ErrorCode;

public class MailAuthenticationFailException extends CloseToYouException {
    public static final CloseToYouException EXCEPTION = new MailAuthenticationFailException();

    private MailAuthenticationFailException() {
        super(ErrorCode.INVALID_CERTIFICATION_NUMBER);
    }
}
