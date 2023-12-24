package com.project.agencija.service;

import com.project.agencija.model.Role;
import com.project.agencija.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findById(Long id) {
        return this.roleRepository.findById(id).orElse(null);
    }

    public List<Role> findByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public Role save(Role r){ return this.roleRepository.save(r);}
}
