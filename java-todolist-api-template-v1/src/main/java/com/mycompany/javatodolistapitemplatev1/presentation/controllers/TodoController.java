package com.mycompany.javatodolistapitemplatev1.presentation.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {

        List<Todo> todos = new ArrayList<Todo>();
        todos.add(new Todo(1, "Todo one", true));
        todos.add(new Todo(2, "Todo two", false));
        todos.add(new Todo(1, "Todo three", true));

        return new ResponseEntity<List<Todo>>(todos, HttpStatus.OK);
    }
}