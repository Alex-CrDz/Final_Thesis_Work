package com.globant.final_thesis_work.Controllers;

import com.globant.final_thesis_work.Dto.UserSignUpDto;
import com.globant.final_thesis_work.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/sign_up")
public class SignUpController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity signUp(@Valid @RequestBody UserSignUpDto signUpDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity("Some fields are empty", HttpStatus.BAD_REQUEST);
        }
        try {
            userService.saveUser(signUpDto);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("User Signed Up", HttpStatus.CREATED);
    }
}
