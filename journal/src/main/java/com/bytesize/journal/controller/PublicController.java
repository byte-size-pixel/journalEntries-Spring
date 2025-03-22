package com.bytesize.journal.controller;

import com.bytesize.journal.entity.User;
import com.bytesize.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController
{
    @Autowired
    private UserService userService;

    @GetMapping("health")
    public String healthCheck()
    {
        return "Ok";
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.newUser(user);
    }
}
