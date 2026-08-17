package com.chema.db.backend.service;

import com.chema.db.backend.dto.RegisterRequest;
import com.chema.db.backend.dto.UserResponse;
import com.chema.db.backend.exception.ResourceNotFoundException;
import com.chema.db.backend.model.User;
import com.chema.db.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(RegisterRequest request) {

        User user = UserMapper.toEntity(request);
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );
        User savedUser = userRepository.save(user);
        return UserMapper.toResponse(savedUser);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", username));
    }
}
