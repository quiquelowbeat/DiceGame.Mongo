package com.dicegame.mongo.model.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PlayerDto implements Serializable {

    private String playerId;
    private String name;
    private Date date;
    private Double winningPercentage;

}


