package com.project.agencija.service;


import com.project.agencija.model.Client;
import com.project.agencija.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username);
    }

    public void save(Client user){
        userRepository.save(user);
    }

    public Client findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


    public List<Client> getAllClients(){
        return this.userRepository.findAll();
    }

}
