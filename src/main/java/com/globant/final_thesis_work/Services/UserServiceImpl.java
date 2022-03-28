package com.globant.final_thesis_work.Services;

import com.globant.final_thesis_work.Dto.UserSignUpDto;
import com.globant.final_thesis_work.Exceptions.PasswordNotMatchException;
import com.globant.final_thesis_work.Exceptions.UserNotFoundException;
import com.globant.final_thesis_work.Exceptions.UsernameAlreadyExistsException;
import com.globant.final_thesis_work.Persistence.Model.User;
import com.globant.final_thesis_work.Persistence.RoleRepository;
import com.globant.final_thesis_work.Persistence.UserRepository;
import com.globant.final_thesis_work.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserServiceImpl implements UserService {

    public static final String STANDARD_USER_ROLE = "STD_USER";
    public static final boolean ENABLED_USER = true;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getLoggedUser() throws RuntimeException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDetails loggedUser = null;
        if (principal instanceof UserDetails) {
            loggedUser = (UserDetails) principal;
        }
        AtomicReference<User> user = new AtomicReference<User>();
        userRepo.findById(loggedUser.getUsername()).ifPresentOrElse(dbUser -> user.set(dbUser), () -> {
            throw new UserNotFoundException();
        });
        return user.get();
    }

    @Override
    public User getUserByUsername(String username) throws RuntimeException {
        AtomicReference<User> user = new AtomicReference<User>();
        userRepo.findByUsernameAndEnabled(username, ENABLED_USER).ifPresentOrElse(dbUser -> user.set(dbUser), () -> {
            throw new UserNotFoundException();
        });
        return user.get();
    }

    @Override
    public List<User> getUsersByUsernamesList(List<String> usernames) throws RuntimeException {
        List<User> toSend = new ArrayList<User>();
        for (String username : usernames) {
            toSend.add(getUserByUsername(username));
        }
        return toSend;
    }

    @Override
    public UserSignUpDto saveUser(UserSignUpDto signUpDto) throws Exception {
        if (signUpDto.getPassword().isEmpty()
                || !signUpDto.getPassword().equals(signUpDto.getRepeatPassword())) {
            throw new PasswordNotMatchException();
        } else if (!signUpDto.getUsername().isEmpty()
                && userRepo.existsById(signUpDto.getUsername())) {
            throw new UsernameAlreadyExistsException();
        } else {
            User toSave = User.builder()
                    .username(signUpDto.getUsername())
                    .password(passwordEncoder.encode(signUpDto.getPassword()))
                    .idNumber(signUpDto.getIdNumber())
                    .firstName(signUpDto.getFirstName())
                    .lastName(signUpDto.getLastName())
                    .address(signUpDto.getAddress())
                    .zipCode(signUpDto.getZipCode())
                    .state(signUpDto.getState())
                    .country(signUpDto.getCountry())
                    .enabled(true)
                    .creationDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .roles(roleRepo.findByNameRole(STANDARD_USER_ROLE).stream().toList())
                    .build();
            try {
                if (toSave.getRoles().isEmpty()) {
                    throw new RuntimeException("Failed to Assign a Role");
                }
                userRepo.save(toSave);
            } catch (Exception e) {
                throw new Exception("User not saved: " + e.getMessage());
            }
        }
        return signUpDto;
    }
}
