package com.example.medjool.services.implementation;

import com.example.medjool.dto.UserDetailsDto;
import com.example.medjool.model.User;
import com.example.medjool.repository.UserRepository;
import com.example.medjool.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;

    @Autowired
    public UserManagementServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<UserDetailsDto>> getAllUsers() {
        List<UserDetailsDto> userDetailsDtos = userRepository.findAll().stream()
                .map(user -> new UserDetailsDto(
                        user.getUserId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getRole().getRoleName().toString(),
                        user.isAccountNonLocked()
                        )
                )
                .toList();
        return new ResponseEntity<>(userDetailsDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDetailsDto> getUserById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Object> activateUserAccount(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(u->{
            u.setAccountNonLocked(true);
            userRepository.save(u);
        });
        return new ResponseEntity<>("User account activated", HttpStatus.OK);
    }
}
