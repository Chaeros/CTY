package ssafy.closetoyou.global.error;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class ErrorResponse {

    private final int status;
    private final boolean success = false;
    private final Map<String, Object> result;
    private final String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.result = Collections.emptyMap(); // 빈 맵으로 초기화
    }

    public boolean isSuccess() {
        Objects.requireNonNull(this);
        return false;
    }

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public Map<String, Object> getResult() {
        return this.result;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", success=" + success +
                ", result=" + result +
                ", message='" + message + '\'' +
                '}';
    }

}

