package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class UpdateTodoRequest {

    @Getter
    @Setter
    public String title;

    @Getter
    @Setter
    public Boolean done;
}