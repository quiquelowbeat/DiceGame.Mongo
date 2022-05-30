package com.dicegame.mongo.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class GameDto implements Serializable {

    @Schema(description = "Dice 1 result.")
    private int dice1;
    @Schema(description = "Dice 2 result.")
    private int dice2;
    @Schema(description = "If the sum of dice 1 and dice 2 is (lucky) 7, you WIN, otherwise you LOSE (sorry).")
    private String result;

}
