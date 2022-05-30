package com.dicegame.mongo.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GameDto implements Serializable {

    private int dice1;
    private int dice2;
    private String result;

}
