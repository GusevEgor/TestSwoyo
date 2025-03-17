package org.example.testswoyo.server.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.testswoyo.server.dto.request.LoginUserRequest;
import org.example.testswoyo.server.entity.User;
import org.example.testswoyo.server.mapper.UserMapper;
import org.example.testswoyo.server.repository.UserRepository;
import org.example.testswoyo.server.utils.ServerGlobalResponseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ServerGlobalResponseHandler serverGlobalResponseHandler;

    @InjectMocks
    private UserService userService;

    private LoginUserRequest loginRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginRequest = new LoginUserRequest("testUser");
    }

    @Test
    void testLogin_UserExists() {
        // Настроим мок для проверки существования пользователя
        when(userRepository.existsByName(loginRequest.getUsername())).thenReturn(true);

        // Настроим мок для возвращения успешного сообщения
        when(serverGlobalResponseHandler.success("Login success")).thenReturn("Login success");

        // Вызов метода
        String result = userService.login(loginRequest);

        // Проверка результата
        assertEquals("Login success", result);

        // Проверяем, что метод existsByName был вызван с правильным аргументом
        verify(userRepository).existsByName(loginRequest.getUsername());
    }

    @Test
    void testLogin_UserDoesNotExist() {
        // Настроим мок для того, что пользователь не существует
        User userMock = Mockito.mock(User.class);
        when(userRepository.existsByName(loginRequest.getUsername())).thenReturn(false);

        // Настроим поведение мока для сохранения пользователя
        when(userMapper.convertFromCreateLoginUserRequestToEntity(loginRequest)).thenReturn(userMock);
        when(userRepository.save(userMock)).thenReturn(userMock);
        when(serverGlobalResponseHandler.success("Login success")).thenReturn("Login success");

        // Вызов метода
        String result = userService.login(loginRequest);

        // Проверка результата
        assertEquals("Login success", result);

        // Проверяем, что метод save был вызван
        verify(userRepository).save(userMock);
    }
}