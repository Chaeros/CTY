package ssafy.closetoyou.domain.user.exception;

import ssafy.closetoyou.global.error.exception.CloseToYouException;
import ssafy.closetoyou.global.error.exception.ErrorCode;

public class NotExistUserException extends CloseToYouException {

    public static final NotExistUserException EXCEPTION = new NotExistUserException();

    private NotExistUserException(){super(ErrorCode.NOT_EXIST_USER);}
}
