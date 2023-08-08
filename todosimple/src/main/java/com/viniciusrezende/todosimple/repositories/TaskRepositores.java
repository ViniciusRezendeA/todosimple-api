package com.viniciusrezende.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viniciusrezende.todosimple.models.Task;
import java.util.List;


@Repository
public interface TaskRepositores extends JpaRepository<Task,Long> {

    
    List<Task> findByUser_Id(Long id);

   
}
