package com.example.medjool.services.implementation;

import com.example.medjool.dto.UserDetailsDto;
import com.example.medjool.exception.UserAccountCannotBeDeletedException;
import com.example.medjool.model.User;
import com.example.medjool.repository.UserRepository;
import com.example.medjool.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserManagementServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
                        user.isAccountNonLocked(),
                        user.getLastLogin()
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

    @Override
    public ResponseEntity<Object> updateUserDetails(Long id, UserDetailsDto userDetailsDto) {
        Optional<User> user = userRepository.findById(id);

        user.ifPresent(u->{
            u.setFirstName(userDetailsDto.getFirstName());
            u.setLastName(userDetailsDto.getLastName());
            u.setEmail(userDetailsDto.getEmail());
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            userRepository.save(u);
        });

        return new ResponseEntity<>("User details updated", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> holdUserAccount(Long id) {

        // Fetch the user from the database;
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(u->{
            u.setAccountNonLocked(false);
        });

        return new ResponseEntity<>("User account hold", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteUserAccount(Long id) {
        try{
            userRepository.deleteById(id);
            return new ResponseEntity<>("User account deleted", HttpStatus.OK);
        }catch (UserAccountCannotBeDeletedException e){
            return new ResponseEntity<>("User account cannot be deleted", HttpStatus.BAD_REQUEST);
        }
    }
}
