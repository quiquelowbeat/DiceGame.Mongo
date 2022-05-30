package com.dicegame.mongo.model.domains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Setter
@ToString
@Document(collection = "players")
public class Player {

    @Id
    private String playerId;
    private String name;
    private Date date;
    private int totalWins;
    private Double winningPercentage;
    private List<Game> games;

    public Player(String name){
        this.name = name;
        this.date = Calendar.getInstance().getTime();
        totalWins = 0;
        this.winningPercentage = 0.0d;
        this.games = new ArrayList<>();
    }

    public double calculateWinningPercentage(){
        double result = 0;
        if(this.games != null){
            result = (totalWins / (double) games.size()) * 100;
            if(Double.isNaN(result) || Double.isInfinite(result)){
                result = 0.0d;
            }
        }
        return result;
    }

    // Static Factory Method - devuelve una instancia del objeto Player.
    public static Player getInstance(String name){
        // Objects.requireNonNullElse(name, "ANONIMOUS") - crea el objeto con name o "ANONIMOUS" si name es null.
        return new Player(Objects.requireNonNullElse(name, "ANONIMOUS"));
    }
}
