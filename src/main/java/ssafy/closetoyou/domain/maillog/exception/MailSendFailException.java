package ssafy.closetoyou.domain.maillog.exception;

import ssafy.closetoyou.global.error.exception.CloseToYouException;
import ssafy.closetoyou.global.error.exception.ErrorCode;

public class MailSendFailException extends CloseToYouException {
    public static final CloseToYouException EXCEPTION = new MailSendFailException();

    private MailSendFailException() {
        super(ErrorCode.MAIL_SEND_FAIL);
    }
}
