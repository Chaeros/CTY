package ssafy.closetoyou.global.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ssafy.closetoyou.domain.user.domain.User;
import ssafy.closetoyou.domain.user.repository.UserRepository;
import ssafy.closetoyou.global.login.userdetail.CustomUserDetail;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    /*
        로그인 Form에 입력한 email을 바탕으로,
        소셜타입이 없으면서 회원가입이 되어있어 DB에 저장된 유저를 가져옵니다.
        입력한 email과 password의 일치 여부를 자동으로 검사하며,
        성공 시 LoginSuccessHandler로 이동하고,
        실패 시 LoginFailureHandler를 이동합니다.
     */
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmailAndOauthServerType(email,null).orElse(null);
        if ( user == null ){
            log.error("[Login fail]User not found for email: {}", email);
            throw new UsernameNotFoundException("User not found for email: " + email);
        }

        return new CustomUserDetail(user);
    }

}
