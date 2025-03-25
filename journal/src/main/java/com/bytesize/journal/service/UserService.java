package com.bytesize.journal.service;

import com.bytesize.journal.entity.User;
import com.bytesize.journal.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }
    public void newUser(User user) {
        user.setRoles(List.of("USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public void saveAdmin(User user){
        user.setRoles(List.of("ADMIN"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

}
