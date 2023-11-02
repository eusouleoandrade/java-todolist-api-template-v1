package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class SetDoneTodoRequest {

    @Getter
    public Boolean done;
}