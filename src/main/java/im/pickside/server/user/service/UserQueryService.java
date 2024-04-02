package im.pickside.server.user.service;

import im.pickside.server.user.entity.User;
import im.pickside.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import im.pickside.server.exception.NotFoundException;

import static im.pickside.server.exception.ExceptionStatus.NOT_FOUND_ACCOUNT;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public User fetchAccountEntity(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ACCOUNT));
    }

    public boolean existByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }


}
