package com.flowforge.service;

import com.flowforge.dto.UserRequestDTO;
import com.flowforge.dto.UserResponseDTO;
import com.flowforge.entity.User;
import com.flowforge.exception.UserNotFoundException;
import com.flowforge.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE
    public UserResponseDTO createUser(@Valid UserRequestDTO requestDTO) {

        // DTO -> Entity
        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());

        // Save to database
        User savedUser = userRepository.save(user);

        // Entity -> Response DTO
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(savedUser.getId());
        responseDTO.setName(savedUser.getName());
        responseDTO.setEmail(savedUser.getEmail());

        return responseDTO;
    }

    // GET BY ID
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // GET ALL
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // DELETE USER
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);

    }

}