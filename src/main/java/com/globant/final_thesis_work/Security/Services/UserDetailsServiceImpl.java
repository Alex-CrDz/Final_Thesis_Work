package com.globant.final_thesis_work.Security.Services;

import com.globant.final_thesis_work.Exceptions.UserNotFoundException;
import com.globant.final_thesis_work.Persistence.Model.User;
import com.globant.final_thesis_work.Security.Model.UserSessionDetails;
import com.globant.final_thesis_work.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.getUserByUsername(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        return UserSessionDetails.build(user);
    }
}
