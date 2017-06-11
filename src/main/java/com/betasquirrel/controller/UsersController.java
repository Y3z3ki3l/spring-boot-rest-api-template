package com.betasquirrel.controller;

import com.betasquirrel.model.User;
import com.betasquirrel.repository.UserRepository;
import com.betasquirrel.util.ValidationError;
import com.betasquirrel.util.ValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Anshad Vattapoyil on 10/06/17 2:58 PM.
 */

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all users
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Create new user
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public List<User> saveUser(@Valid @RequestBody final User user) {
        userRepository.save(user);
        return userRepository.findAll();
    }

    /**
     * Get a specific user
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") Integer userId) {
        return userRepository.findOne(userId);
    }

    /**
     * Find and update a user
     *
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public User updateUser(@RequestBody final User user) {
        if (userRepository.exists(user.getId())) {
            userRepository.updateUser(user.getName(), user.getEmail(), user.getMobile(), user.getId());
            return userRepository.findOne(user.getId());
        }
        return null;
    }

    /**
     * Delete a user
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public List<User> deleteUser(@PathVariable("id") Integer userId) {
        userRepository.delete(userId);
        return userRepository.findAll();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(MethodArgumentNotValidException exception) {
        return createValidationError(exception);
    }

    private ValidationError createValidationError(MethodArgumentNotValidException e) {
        return ValidationErrorBuilder.fromBindingErrors(e.getBindingResult());
    }
}
