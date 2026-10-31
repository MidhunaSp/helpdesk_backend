package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.dto.request.AdminDto;
import com.springboot.helpdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class AdminController {
    private final UserService userService;

    @PostMapping("/api/admin/add")
    public void addAdmin(@RequestBody AdminDto adminDto) {
        userService.addAdmin(adminDto);
    }

    @PutMapping("/api/admin/user/{username}/status")
    public void updateUserStatus(@PathVariable String username,
                                 @RequestParam boolean active) {
        userService.updateUserStatus(username, active);
    }
}