package com.viniciusrezende.todosimple.services;

import java.util.Optional;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viniciusrezende.todosimple.models.Task;
import com.viniciusrezende.todosimple.models.User;
import com.viniciusrezende.todosimple.repositories.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepositores;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepositores.findById(id);
        return task.orElseThrow(() -> new RuntimeException(
                "Tarefa não encontrado Id:" + id + "Tipo:" + Task.class.getName()));
    }
    public List<Task> findAllByUserId(Long userId){
        List<Task> tasks = this.taskRepositores.findByUser_Id(userId);
        return tasks;
    }
    @Transactional
    public Task create(Task obj) {
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepositores.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        newObj.setStatus(obj.getStatus());
        return this.taskRepositores.save(newObj);
    }

    public void delete(Long id) {
        Task obj = findById(id);
        try {
            this.taskRepositores.delete(obj);

        } catch (Exception e) {
            throw new RuntimeException("Não e possivel excluir pois há entidades relacionada!");
        }
    }
}
