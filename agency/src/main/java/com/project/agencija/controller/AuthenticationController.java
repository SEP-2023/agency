package com.project.agencija.controller;

import com.project.agencija.dto.ClientRequestDTO;
import com.project.agencija.dto.UserInfoDTO;
import com.project.agencija.dto.UserTokenState;
import com.project.agencija.model.Client;
import com.project.agencija.service.LoggerService;
import com.project.agencija.service.RoleService;
import com.project.agencija.service.UserService;
import com.project.agencija.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final LoggerService logger = new LoggerService(this.getClass());

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @PostMapping("/register")
    public ResponseEntity<Client> addUser(@RequestBody ClientRequestDTO userRequest) {
        Client existUser = this.userService.findByEmail(userRequest.getEmail());
        if (existUser != null) {
            logger.warn("Registration failed: user with email " + userRequest.getEmail() + " already exists");
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        Client client = modelMapper.map(userRequest, Client.class);
        client.setLocked(false);
        client.setFailedLoginAttempts(0);

        client.setPassword(passwordEncoder.encode(client.getPassword()));

        logger.success("User with email " + client.getEmail() + " successfully registered");
        client.setRoles(roleService.findByName("ROLE_GOVERNMENT"));
        userService.save(client);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> loginUser(@RequestBody UserInfoDTO userInfoDTO) {

        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userInfoDTO.getEmail(), userInfoDTO.getPassword()));
        } catch(InternalAuthenticationServiceException e){
            logger.error("Failed login for user with email " + userInfoDTO.getEmail());
            return ResponseEntity.badRequest().body(null);
        } catch (DisabledException e){
            logger.error("Failed login for user with email " + userInfoDTO.getEmail());
            return  ResponseEntity.ok(null);
        } catch (Exception e){
            logger.error("Failed login for user with email " + userInfoDTO.getEmail());
            userService.incrementLoginFailedAttempts(userInfoDTO.getEmail());
            return ResponseEntity.badRequest().body(null);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Client user = (Client) authentication.getPrincipal();
        if(user.getDeleted()){
            logger.error("User with email " + userInfoDTO.getEmail() + " was deleted");
            return ResponseEntity.badRequest().body(null);
        }
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        logger.success("User with email " + userInfoDTO.getEmail() + " successfully logged in");
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @GetMapping(value = "/current")
    @PreAuthorize("hasRole('ROLE_GOUVERNMENT')")
    public ResponseEntity<Client> getCurrentUser(Principal user){
        return new ResponseEntity<>(userService.findByEmail(user.getName()),HttpStatus.OK);
    }
}
