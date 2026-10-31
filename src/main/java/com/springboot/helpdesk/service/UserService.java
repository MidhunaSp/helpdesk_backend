package com.springboot.helpdesk.service;

import com.springboot.helpdesk.dto.request.AdminDto;
import com.springboot.helpdesk.dto.response.DetailsDto;
import com.springboot.helpdesk.enums.Role;
import com.springboot.helpdesk.exception.InactiveUserException;
import com.springboot.helpdesk.exception.ResourceNotFoundException;
import com.springboot.helpdesk.model.User;
import com.springboot.helpdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserObj(String username, String password, Role role){
        User user =  new User(username,password,role);
        return userRepository.save(user);
    }

    public void addAdmin(AdminDto adminDto) {
        // Prepare User using AdminDto
        User user = new User(
                adminDto.username(),
                passwordEncoder.encode(adminDto.password()),
                Role.ADMIN
        );

        // Save user in DB
        userRepository.save(user);
    }

    public DetailsDto getUserDetails(String loggedInUsername) {
        DetailsDto detailsDto = userRepository.getUserDetails(loggedInUsername);
        if(!detailsDto.isActive())
            throw new InactiveUserException("User is disabled");
        return detailsDto;
    }

    public void updateUserStatus(String username, boolean active) {
        User user = userRepository.loadUserByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        user.setActive(active);
        userRepository.save(user);
    }
}

