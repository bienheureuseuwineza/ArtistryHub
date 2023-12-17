package com.cc.creativecraze.dto;

import jakarta.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDto {
    private int id;
    private String name;
    private String ownerEmail;
    private String age;
    private String nationality;
    private byte[] pdf;
    private byte[] image;
    private String message;

}
