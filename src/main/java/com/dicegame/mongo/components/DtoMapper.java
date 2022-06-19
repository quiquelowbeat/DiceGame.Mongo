package com.dicegame.mongo.components;

import com.dicegame.mongo.model.domains.Game;
import com.dicegame.mongo.model.domains.Player;
import com.dicegame.mongo.model.dto.GameDto;
import com.dicegame.mongo.model.dto.PlayerDto;

import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public PlayerDto toPlayerDto(Player player){
        PlayerDto playerDto = new PlayerDto();
        playerDto.setPlayerId(player.getPlayerId());
        playerDto.setName(player.getName());
        playerDto.setDate(player.getDate());
        playerDto.setWinningPercentage(player.getWinningPercentage());
        return playerDto;
    }

    public GameDto toGameDto(Game game){
        GameDto gameDto = new GameDto();
        gameDto.setDice1(game.getDice1());
        gameDto.setDice2(game.getDice2());
        gameDto.setResult(game.getResult());
        return gameDto;
    }
}