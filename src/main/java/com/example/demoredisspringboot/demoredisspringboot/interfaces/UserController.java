package com.example.demoredisspringboot.demoredisspringboot.interfaces;

import com.example.demoredisspringboot.demoredisspringboot.entity.User;
import com.example.demoredisspringboot.demoredisspringboot.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserDao userDao;

    @PostMapping
    public User save(@RequestBody User user){
        return userDao.save(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userDao.findAll();
    }

    @GetMapping("/{id}")
    public User findUser(@PathVariable String id){
        return userDao.findUserById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id){
        return userDao.deleteUser(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user){
        User userToUpdate = userDao.findUserById(id);
        if (userToUpdate == null) {
            throw new NoSuchElementException("User with id " + id + " not found");
        }
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setProfilePicture(user.getProfilePicture());
        userToUpdate.setFollowers(user.getFollowers());
        userToUpdate.setFollowing(user.getFollowing());
        return userDao.save(userToUpdate);
    }

}
