package ssafy.closetoyou.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ssafy.closetoyou.domain.user.dto.request.UserModifyRequestDto;
import ssafy.closetoyou.domain.user.dto.request.UserSignUpRequestDto;
import ssafy.closetoyou.domain.user.dto.response.UserResponseDto;
import ssafy.closetoyou.domain.user.service.UserService;
import ssafy.closetoyou.global.common.response.ApiResponse;
import ssafy.closetoyou.global.jwt.service.JwtService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    // 일반 사용자 회원가입 API
    @PostMapping("/normal/sign-up")
    public ApiResponse<Void> userSignUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto) throws Exception {
        log.info("[sign-up] email : {}", userSignUpRequestDto.email());
        userService.signUp(userSignUpRequestDto);
        return new ApiResponse<>(200,true,null,"일반 이용자 회원가입 성공");
    }

    // 사용자 정보 호출 API
    @GetMapping("/info")
    public ApiResponse<UserResponseDto> getUserInformation(HttpServletRequest httpServletRequest) throws Exception {
        log.info("[user info call]");
        String accessToken = jwtService.extractAccessToken(httpServletRequest).orElse(null);
        try {
            Long userId = jwtService.extractUserId(accessToken).orElse(null);
            log.info("[user info] userId : {}", userId);
            UserResponseDto userResponseDto = userService.searchUserByUserId(userId).transferToUserResponseDto();
            return new ApiResponse<>(200, true, userResponseDto, "사용자 정보 불러오기 성공");
        } catch (Exception e) {
            log.error("Error fetching user information", e);
            return new ApiResponse<>(404, false, null, e.getMessage());
        }
    }

    // 사용자 정보 수정 API
    @PutMapping
    public ApiResponse<UserResponseDto> modifyUserInformation(@RequestBody UserModifyRequestDto userModifyRequestDto, HttpServletRequest httpServletRequest){
        log.info("[user info modify]");
        String accessToken = jwtService.extractAccessToken(httpServletRequest).orElse(null);
        try {
            Long userId = jwtService.extractUserId(accessToken).orElse(null);
            log.info("[user info] userId : {}", userId);
            UserResponseDto userResponseDto = userService.updateUser(userId, userModifyRequestDto);
            return new ApiResponse<>(200, true, userResponseDto, "사용자 정보 수정 성공");
        } catch (Exception e) {
            log.error("Error fetching user information", e);
            return new ApiResponse<>(404, false, null, e.getMessage());
        }
    }
}