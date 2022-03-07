package com.slavik.misHorariosApi.controller;

import com.slavik.misHorariosApi.data.UserRepository;
import com.slavik.misHorariosApi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping
    public @ResponseBody String postUser(@RequestBody User user) {
        repository.save(user);
        return "Saved";
    }

    @GetMapping
    public @ResponseBody Iterable<User> getAll() {
        return repository.findAll();
    }

    @PostMapping("/login")
    public @ResponseBody User logIn(@RequestBody User user) {
        User result = repository.findUser(user.getEmail(), user.getPasswd());
        if (result == null){
            user.setId(-1);
            return user;
        }

        return result;
    }

    @PostMapping("/signup")
    public @ResponseBody User signUp(@RequestBody User user) {

        repository.save(user);
        return logIn(user);
    }
}
