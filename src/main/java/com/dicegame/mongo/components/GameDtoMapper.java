package com.dicegame.mongo.components;

import com.dicegame.mongo.model.domains.Game;
import com.dicegame.mongo.model.dto.GameDto;
import org.springframework.stereotype.Component;

@Component
public class GameDtoMapper {

    public GameDto toGameDto(Game game){
        GameDto gameDto = new GameDto();
        gameDto.setDice1(game.getDice1());
        gameDto.setDice2(game.getDice2());
        gameDto.setResult(game.getResult());
        return gameDto;
    }
}