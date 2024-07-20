package ssafy.closetoyou.global.exception.email;

import ssafy.closetoyou.global.error.exception.CloseToYouException;
import ssafy.closetoyou.global.error.exception.ErrorCode;

public class InvalidEmailFormException extends CloseToYouException {
    public static final CloseToYouException EXCEPTION = new InvalidEmailFormException();

    private InvalidEmailFormException() {
        super(ErrorCode.INVALID_EMAIL_FORM);
    }
}

