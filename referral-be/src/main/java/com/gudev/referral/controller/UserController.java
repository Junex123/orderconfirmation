package com.gudev.referral.controller;

import com.gudev.referral.dto.UserDto;
import com.gudev.referral.model.User;
import com.gudev.referral.request.CreateUserRequest;
import com.gudev.referral.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;


    public UserController(ModelMapper modelMapper,
                          UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        final var userDTO = modelMapper.map(userService.getUserByUsername(username), UserDto.class);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/referral/{referralCode}")
    public ResponseEntity<?> getAllByReferralCode(@PathVariable String referralCode) {
        final List<UserDto> userDTOs = userService.getAllByReferralCode(referralCode)
                .stream().map(x -> modelMapper.map(x, UserDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }
    
    @GetMapping("/referred/{referredCode}")
    public ResponseEntity<?> getAllByReferredCode(@PathVariable String referredCode) {
        final List<UserDto> userDTOs = userService.getAllByReferredCode(referredCode)
                .stream().map(x -> modelMapper.map(x, UserDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        final var user = modelMapper.map(createUserRequest, User.class);
        final var userDto = modelMapper.map(userService.createUser(user), UserDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }


}
