package com.bytesize.journal.controller;

import com.bytesize.journal.entity.User;
import com.bytesize.journal.rest.response.WeatherResponse;
import com.bytesize.journal.service.UserService;
import com.bytesize.journal.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController
{
    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("health")
    public String healthCheck()
    {

        WeatherResponse weatherResponse = weatherService.getWeather("Bengaluru");
        String greeting = "";
        if(weatherResponse!=null){
            greeting = " Weather feels like " + weatherResponse.getCurrent().getTemperature();
        }
        return "Hi" + greeting;
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.newUser(user);
    }
}
