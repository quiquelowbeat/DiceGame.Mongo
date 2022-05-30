package com.dicegame.mongo.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PlayerDto implements Serializable {

    @Schema(description = "Player Id.")
    private String playerId;
    @Schema(description = "Player's name.")
    private String name;
    @Schema(description = "Date of registration.")
    private Date date;
    @Schema(description = "Player's winning percentage.")
    private Double winningPercentage;

}


