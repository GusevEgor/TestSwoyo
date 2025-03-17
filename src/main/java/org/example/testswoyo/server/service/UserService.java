package org.example.testswoyo.server.service;

import lombok.RequiredArgsConstructor;
import org.example.testswoyo.server.dto.request.LoginUserRequest;
import org.example.testswoyo.server.entity.User;
import org.example.testswoyo.server.mapper.UserMapper;
import org.example.testswoyo.server.repository.UserRepository;
import org.example.testswoyo.server.utils.ServerGlobalResponseHandler;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ServerGlobalResponseHandler serverGlobalResponseHandler;

    public String login(LoginUserRequest loginRequest) {
        if (checkUserExists(loginRequest.getUsername())) {
            return serverGlobalResponseHandler.success("Login success");
        }
        User user = userMapper.convertFromCreateLoginUserRequestToEntity(loginRequest);
        userRepository.save(user);
        return serverGlobalResponseHandler.success("Login success");
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public Boolean checkUserExists(String username) {
        return userRepository.existsByName(username);
    }

}
