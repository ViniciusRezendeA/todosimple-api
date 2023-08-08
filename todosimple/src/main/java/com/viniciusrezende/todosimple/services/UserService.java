package com.viniciusrezende.todosimple.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viniciusrezende.todosimple.repositories.UserRepositores;
import com.viniciusrezende.todosimple.models.User;
import com.viniciusrezende.todosimple.repositories.TaskRepositores;

@Service
public class UserService {

    @Autowired
    private UserRepositores userRepositores;

    @Autowired
    private TaskRepositores taskRepositores;

    public User findById(Long id) {
        Optional<User> user = this.userRepositores.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
                "Usuario não encontrado Id:" + id + "Tipo:" + User.class.getName()));
    }

    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj = this.userRepositores.save(obj);
        this.taskRepositores.saveAll(obj.getTasks());
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newoObj = findById(obj.getId());
        newoObj.setPassword(obj.getPassword());
        return this.userRepositores.save(newoObj);
    }

    public void delete(Long id) {
        User obj = findById(id);
        try {
            this.userRepositores.delete(obj);

        } catch (Exception e) {
            throw new RuntimeException("Não e possivel excluir pois há entidades relacionada!");
        }
    }
}
