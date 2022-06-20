package com.dicegame.mongo.model.domains;

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
        return (this.dice1 + this.dice2) == 7 ? "WIN" : "LOSE";
    }

    // Static Factory Method
    public static Game getInstance(){
        return new Game();
    }

}
