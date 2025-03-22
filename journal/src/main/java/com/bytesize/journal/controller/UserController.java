package com.bytesize.journal.controller;

import com.bytesize.journal.entity.User;
import com.bytesize.journal.service.UserService;
import com.bytesize.journal.serviceImpl.UserAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthServiceImpl userAuthService;

    @PostMapping
    public ResponseEntity<?> createNewUser(@RequestBody User user)
    {
        try {
            userService.newUser(user);
            return new ResponseEntity<>(userAuthService.getUserName(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(user, HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser( @RequestBody User newUser)
    {
        User userInDb = userService.findByUserName(userAuthService.getUserName());
        if(userInDb!=null)
        {
            userInDb.setUserName(newUser.getUserName());
            userInDb.setPassword(newUser.getPassword());
            userService.newUser(userInDb);
            return new ResponseEntity<>("User updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser()
    {
        User userInDb = userService.findByUserName(userAuthService.getUserName());
        if(userInDb!=null)
        {
            userService.deleteUser(userInDb);
            return new ResponseEntity<>("User Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

}
