package com.revworkforce.user_service.service;

import com.revworkforce.user_service.dto.LoginRequest;
import com.revworkforce.user_service.dto.LoginResponse;
import com.revworkforce.user_service.dto.UserRegistrationRequest;
import com.revworkforce.user_service.dto.UserResponse;
import com.revworkforce.user_service.entity.User;
import com.revworkforce.user_service.repository.UserRepository;
import com.revworkforce.user_service.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("john123");
        user.setEmail("john@example.com");
        user.setPassword("encodedPassword");
        user.setRole("EMPLOYEE");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhone("9876543210");
        user.setActive(true);
    }

    @Test
    void registerUser_shouldCreateUserSuccessfully() {

        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("john123");
        request.setEmail("john@example.com");
        request.setPassword("password123");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPhone("9876543210");

        when(userRepository.existsByUsername("john123"))
                .thenReturn(false);

        when(userRepository.existsByEmail("john@example.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("password123"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserResponse response = userService.registerUser(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("john123", response.getUsername());
        assertEquals("EMPLOYEE", response.getRole());

        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("password123");
    }

    @Test
    void registerUser_shouldRejectDuplicateUsername() {

        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("john123");
        request.setEmail("new@example.com");
        request.setPassword("password123");
        request.setFirstName("John");
        request.setLastName("Doe");

        when(userRepository.existsByUsername("john123"))
                .thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.registerUser(request)
        );

        assertEquals("Username already exists", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_shouldReturnJwtToken() {

        LoginRequest request = new LoginRequest();
        request.setUsername("john123");
        request.setPassword("password123");

        when(userRepository.findByUsername("john123"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("password123", "encodedPassword"))
                .thenReturn(true);

        when(jwtService.generateToken(
                1L,
                "john123",
                "EMPLOYEE"
        )).thenReturn("jwt-token");

        LoginResponse response = userService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals(1L, response.getUserId());
        assertEquals("john123", response.getUsername());
        assertEquals("EMPLOYEE", response.getRole());
    }

    @Test
    void login_shouldRejectInvalidPassword() {

        LoginRequest request = new LoginRequest();
        request.setUsername("john123");
        request.setPassword("wrongPassword");

        when(userRepository.findByUsername("john123"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "wrongPassword",
                "encodedPassword"
        )).thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.login(request)
        );

        assertEquals(
                "Invalid username or password",
                exception.getMessage()
        );

        verify(jwtService, never())
                .generateToken(any(), any(), any());
    }

    @Test
    void login_shouldRejectInactiveUser() {

        user.setActive(false);

        LoginRequest request = new LoginRequest();
        request.setUsername("john123");
        request.setPassword("password123");

        when(userRepository.findByUsername("john123"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "password123",
                "encodedPassword"
        )).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.login(request)
        );

        assertEquals(
                "User account is inactive",
                exception.getMessage()
        );
    }

    @Test
    void getUserProfile_shouldReturnUserProfile() {

        when(userRepository.findByUsername("john123"))
                .thenReturn(Optional.of(user));

        UserResponse response =
                userService.getUserProfile("john123");

        assertEquals("john123", response.getUsername());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
    }
}