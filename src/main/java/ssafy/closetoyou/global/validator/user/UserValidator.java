package ssafy.closetoyou.global.validator.user;

import lombok.RequiredArgsConstructor;
import ssafy.closetoyou.domain.user.domain.User;
import ssafy.closetoyou.domain.user.exception.NotExistUserException;
import ssafy.closetoyou.domain.user.repository.UserRepository;

@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void exist(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw NotExistUserException.EXCEPTION;
        }
    }
}
