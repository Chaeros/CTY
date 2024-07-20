package ssafy.closetoyou.domain.user.exception;

import ssafy.closetoyou.global.error.exception.CloseToYouException;
import ssafy.closetoyou.global.error.exception.ErrorCode;

public class DuplicateEmailException extends CloseToYouException {

    public static final CloseToYouException EXCEPTION = new DuplicateEmailException();

    private DuplicateEmailException(){super(ErrorCode.DUPLICATE_EMAIL);}

}
