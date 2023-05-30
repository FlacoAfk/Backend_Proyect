package com.example.podcast.controladores;


import com.example.podcast.entidades.UserRecord;
import com.example.podcast.solicitudes.AuthRequest;
import com.example.podcast.solicitudes.UserCreateRequest;
import com.example.podcast.servicios.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserRecord create(@RequestBody UserCreateRequest request){
        return userService.create(request);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Validated @RequestBody AuthRequest authRequest) throws Exception {
        log.info("Inside UserController::signIn AuthRequest: {} ", authRequest.toString());
        return ok().body(userService.signIn(authRequest));
    }

    @GetMapping("/{id}")
    public UserRecord findById(@PathVariable long id) throws Exception {
        return userService.findById(id);
    }

    @GetMapping("/document")
    public UserRecord findByDocument(@RequestParam String document) throws Exception {
        return userService.findByDocument(document);
    }

    @GetMapping
    public List<UserRecord> findAll() {
        return userService.findAll();
    }

}
