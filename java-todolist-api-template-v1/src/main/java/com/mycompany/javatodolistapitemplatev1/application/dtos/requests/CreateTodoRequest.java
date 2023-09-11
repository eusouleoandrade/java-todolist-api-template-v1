package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class CreateTodoRequest {

    @Getter
    @Setter
    public String title;
}