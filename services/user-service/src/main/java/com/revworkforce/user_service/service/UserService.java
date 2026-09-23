package com.revworkforce.user_service.service;

import com.revworkforce.user_service.dto.UserRegistrationRequest;
import com.revworkforce.user_service.dto.UserResponse;
import com.revworkforce.user_service.entity.User;
import com.revworkforce.user_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.revworkforce.user_service.dto.LoginRequest;
import com.revworkforce.user_service.dto.LoginResponse;
import com.revworkforce.user_service.security.JwtService;
import java.util.List;
import com.revworkforce.user_service.dto.ProfileUpdateRequest;
import com.revworkforce.user_service.dto.UserRoleRequest;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse registerUser(UserRegistrationRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole("EMPLOYEE");
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setActive(true);

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        if (!user.isActive()) {
            throw new RuntimeException("User account is inactive");
        }

        String token = jwtService.generateToken(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );

        return new LoginResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }

    public UserResponse getUserProfile(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    public UserResponse updateUserStatus(Long id, boolean active) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(active);

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    public UserResponse updateProfile(
            String username,
            ProfileUpdateRequest request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    public UserResponse updateUserRole(Long id, String role) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String normalizedRole = role.toUpperCase();

        if (!normalizedRole.equals("EMPLOYEE")
                && !normalizedRole.equals("MANAGER")
                && !normalizedRole.equals("ADMIN")) {

            throw new RuntimeException(
                    "Invalid role. Allowed roles: EMPLOYEE, MANAGER, ADMIN"
            );
        }

        user.setRole(normalizedRole);

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    private UserResponse mapToResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setActive(user.isActive());

        return response;
    }
}