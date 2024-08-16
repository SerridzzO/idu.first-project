package com.company.firstprojectspring.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    private Integer cardId;
    private String holderName;
    private String cardType;
    private String cardNumber;
    private String cardCode;
    private String cardExpiryDate;

    private Integer userId;


}
