package com.globant.final_thesis_work.Controllers;

import com.globant.final_thesis_work.Dto.JwtDto;
import com.globant.final_thesis_work.Dto.UserLoginDto;
import com.globant.final_thesis_work.Exceptions.UserNotFoundException;
import com.globant.final_thesis_work.Persistence.Model.User;
import com.globant.final_thesis_work.Security.JWT.JwtProvider;
import com.globant.final_thesis_work.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<JwtDto> entry(@Valid @RequestBody UserLoginDto loginDto, BindingResult result) {
        JwtDto jwtDto;
        if (result.hasErrors()) {
            return new ResponseEntity("Some fields are empty", HttpStatus.BAD_REQUEST);
        }
        try {
            jwtDto = login(loginDto);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.ACCEPTED);
    }

    private JwtDto login(UserLoginDto loginDto) throws Exception {
        JwtDto jwtDto = null;
        User user = userService.getUserByUsername(loginDto.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            jwtDto = JwtDto.builder()
                    .token(token)
                    .username(user.getUsername())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .authorities(userDetails.getAuthorities())
                    .build();
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed generating token: " + e.getMessage());
        }
        return jwtDto;
    }
}
