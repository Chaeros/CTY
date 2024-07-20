package ssafy.closetoyou.global.error.exception;

public enum ErrorCode {
    INVALID_REFRESH_TOKEN(400, "리프레시 토큰이 유효하지 않습니다"),
    INVALID_ACCESS_TOKEN(400, "Access 토큰이 유효하지 않습니다"),
    LOGIN_FAIL(400,"로그인 실패! 이메일이나 비밀번호를 확인해주세요."),
    OAUTH_LOGIN_FAIL(400,"존재하지않거나 만료된 code를 사용해 인증에 실패했습니다."),
    DUPLICATE_EMAIL(404,"이미 존재하는 이메일입니다."),
    INVALID_EMAIL_FORM(404,"유효하지 않은 형태의 이메일입니다."),
    NOT_EXIST_USER(404,"존재하지 않는 사용자입니다."),
    INVALID_TOKEN(401, "토큰이 유효하지 않습니다."),
    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),
    REGISTER_EXPIRED_TOKEN(403, "만료된 리프레쉬 토큰입니다."),
    USER_NOT_FOUND(404, "해당하는 정보의 사용자를 찾을 수 없습니다."),
    NO_ERROR_TYPE(404, "오류 발생"),
    AUTHENTICATION_FAILED(404, "인증에 실패했습니다."),
    FILE_EMPTY(404, "해당 파일이 비어 있습니다."),
    MAIL_SEND_FAIL(404,"메일 전송에 실패했습니다."),
    MAIL_CHECK_FAIL(404,"메일 인증에 실패했습니다."),
    INVALID_CERTIFICATION_NUMBER(404,"잘못된 인증번호를 입력하셨습니다."),
    TEST_FAIL(404,"테스트 API요청이 실패했습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 에러");

    private int status;
    private String reason;

    public int getStatus() {
        return this.status;
    }

    public String getReason() {
        return this.reason;
    }

    private ErrorCode(final int status, final String reason) {
        this.status = status;
        this.reason = reason;
    }
}
