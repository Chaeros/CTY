package ssafy.closetoyou.global.common.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int status; // 상태코드
    private boolean success; // 성공여부
    private T result; // 반환 데이터
    private String message; // 결과 메시지
}
