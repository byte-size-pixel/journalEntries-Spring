package com.bytesize.journal.service;

import com.bytesize.journal.entity.User;
import com.bytesize.journal.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User findByUserName(String userName)
    {
        return userRepo.findByUserName(userName);
    }

    public void deleteUser(String userName){
        userRepo.delete(findByUserName(userName));
    }

    public void saveUser(User user)
    {
         userRepo.save(user);
    }

}
