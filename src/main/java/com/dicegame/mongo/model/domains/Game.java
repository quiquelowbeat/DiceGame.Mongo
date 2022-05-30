package com.dicegame.mongo.model.domains;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Game {

    private int dice1;
    private int dice2;
    private String result;

    public Game(){
        this.dice1 = getRandomNumberFromOneToSeven();
        this.dice2 = getRandomNumberFromOneToSeven();
        this.result = rollTheDice();
    }

    public int getRandomNumberFromOneToSeven(){
        return (int) (Math.random() * 7) + 1;
    }

    public String rollTheDice(){
        String result;
        if((this.dice1 + this.dice2) == 7){
            result = "WIN";
        } else {
            result = "LOSE";
        }
        return result;
    }

    // Static Factory Method
    public static Game getInstance(){
        return new Game();
    }

}
