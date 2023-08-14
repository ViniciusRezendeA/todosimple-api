package com.viniciusrezende.todosimple.services;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viniciusrezende.todosimple.repositories.UserRepository;
import com.viniciusrezende.todosimple.services.exceptions.DataBindingViolationExcetion;
import com.viniciusrezende.todosimple.services.exceptions.ObjectNotFoundException;
import com.viniciusrezende.todosimple.models.User;

import com.viniciusrezende.todosimple.models.enums.ProfileEnum;
@Service
public class UserService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepositores;

    public User findById(Long id) {
        Optional<User> user = this.userRepositores.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuario não encontrado Id: " + id + " Tipo: " + User.class.getName()));
    }

    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepositores.save(obj);

        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newoObj = findById(obj.getId());
        newoObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        return this.userRepositores.save(newoObj);
    }

    public void delete(Long id) {
        User obj = findById(id);
        try {
            this.userRepositores.delete(obj);

        } catch (Exception e) {
            throw new DataBindingViolationExcetion("Não e possivel excluir pois há entidades relacionada!");
        }
    }
}
