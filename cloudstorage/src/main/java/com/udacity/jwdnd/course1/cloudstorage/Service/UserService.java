package com.udacity.jwdnd.course1.cloudstorage.Service;

import com.udacity.jwdnd.course1.cloudstorage.Entity.User;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.Util.HashService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@AllArgsConstructor
public class UserService {
    private UserMapper userMapper;
    private HashService hashService;

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    public int createUser(User user, Model model) {
        if (userMapper.getUser(user.getUsername()) != null) {
           model.addAttribute("signUpError", "The username already exist");
           return -1;
        }

        int addedUser = userMapper.createUser(hashUserPassword(user));
        if (addedUser > 0) {
            model.addAttribute("signUpSuccess", true);
            return addedUser;
        }

        model.addAttribute("signUpError", "there was an error, please try again");
        return -1;
    }

    private User hashUserPassword(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        user.setPassword(hashedPassword);
        user.setSalt(encodedSalt);
        return user;
    }
}
