package com.berktas.ordermanagementsystem.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveAndUpdateCustomerRequest {

    private String name;
    private int age;
}
