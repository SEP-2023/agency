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
import java.util.concurrent.CompletableFuture;

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

    public void incrementLoginFailedAttempts(String email) {
        Client user = findByEmail(email);
        if (user != null){
            user.setFailedLoginAttempts(user.getFailedLoginAttempts()+1);
            if (user.getFailedLoginAttempts() == 3){
                String message = "ERROR - Nalog korisnika sa email adresom " + email + " je zakljucan";
                //loggerService.logError(message);
                user.setLocked(true);
                unlockUser(user);
            }
            save(user);
        }
    }

    private void unlockUser(Client user) {
        Timer timer = new Timer();
        CompletableFuture.runAsync(() -> timer.schedule(new TimerTask() {
            public void run() {
                user.setLocked(false);
                user.setFailedLoginAttempts(0);
                save(user);
                System.out.println("30 minutes have passed!");
                //loggerService.logInfo("INFO - Nalog korisnika sa email adresom " + user.getEmail() + " is otkljucan");
            }
        }, 30 * 60 * 1000L));
    }
}
