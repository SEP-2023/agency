package com.project.agencija.repository;

import com.project.agencija.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Client,Long> {

    Client findUserByEmail(String email);
}
