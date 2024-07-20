package ssafy.closetoyou.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.closetoyou.domain.user.domain.User;
import ssafy.closetoyou.domain.user.domain.enums.Role;
import ssafy.closetoyou.domain.user.dto.request.UserModifyRequestDto;
import ssafy.closetoyou.domain.user.dto.request.UserSignUpRequestDto;
import ssafy.closetoyou.domain.user.dto.response.UserResponseDto;
import ssafy.closetoyou.domain.user.exception.DuplicateEmailException;
import ssafy.closetoyou.domain.user.exception.NotExistUserException;
import ssafy.closetoyou.domain.user.repository.UserRepository;
import ssafy.closetoyou.global.validator.email.EmailValidator;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 일반 사용자 회원가입 메서드
    public UserResponseDto signUp(UserSignUpRequestDto userSignUpRequestDto) throws Exception {

        EmailValidator.isValidEmail(userSignUpRequestDto.email());

        if (userRepository.findByEmail(userSignUpRequestDto.email()).isPresent()) {
            throw DuplicateEmailException.EXCEPTION;
        }

        User user = User.builder()
                .email(userSignUpRequestDto.email())
                .passphrase(userSignUpRequestDto.password())
                .regdate(LocalDateTime.now())
                .role(Role.GUEST)
                .build();

        user.passwordEncode(passwordEncoder);
        return userRepository.save(user).transferToUserResponseDto();
    }

    // 회원 찾기 메서드
    public User searchUserByUserId(Long userId) throws Exception {
        return userRepository.findById(userId)
                .orElseThrow(() -> DuplicateEmailException.EXCEPTION);
    }

    // 유저 수정 메서드
    @Transactional
    public UserResponseDto updateUser(Long userId, UserModifyRequestDto userModifyRequestDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.updateUserInfo(userModifyRequestDto);
            return user.transferToUserResponseDto();
        } else {
            throw NotExistUserException.EXCEPTION;
        }
    }

    public User searchUserByUserEmail(String email) {
        return userRepository.findByEmailAndOauthServerType(email,null)
                .orElseThrow(() -> DuplicateEmailException.EXCEPTION);
    }
}
