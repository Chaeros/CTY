package ssafy.closetoyou.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CloseToYouException extends RuntimeException{
    private ErrorCode errorCode;
}
