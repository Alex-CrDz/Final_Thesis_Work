package com.globant.final_thesis_work.Services.Interfaces;

import com.globant.final_thesis_work.Dto.UserSignUpDto;
import com.globant.final_thesis_work.Persistence.Model.User;

import java.util.List;

public interface UserService {
    User getLoggedUser() throws RuntimeException;

    User getUserByUsername(String username) throws RuntimeException;

    List<User> getUsersByUsernamesList(List<String> usernames) throws RuntimeException;

    UserSignUpDto saveUser(UserSignUpDto registerDto) throws Exception;
}
