package ssafy.closetoyou.global.exception.token;

import ssafy.closetoyou.global.error.exception.CloseToYouException;
import ssafy.closetoyou.global.error.exception.ErrorCode;

public class ExpiredTokenException extends CloseToYouException {
    public static final CloseToYouException EXCEPTION = new ExpiredTokenException();

    private ExpiredTokenException() {
        super(ErrorCode.INVALID_ACCESS_TOKEN);
    }
}
