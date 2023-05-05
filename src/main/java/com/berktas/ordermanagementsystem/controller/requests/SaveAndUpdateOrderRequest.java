package com.berktas.ordermanagementsystem.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveAndUpdateOrderRequest {

    private LocalDateTime createDate;

    private double totalPrice;

}
