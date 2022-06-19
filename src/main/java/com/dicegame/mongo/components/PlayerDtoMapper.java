package com.dicegame.mongo.components;

import com.dicegame.mongo.model.domains.Player;
import com.dicegame.mongo.model.dto.PlayerDto;
import org.springframework.stereotype.Component;

@Component
public class PlayerDtoMapper {

    public PlayerDto toPlayerDto(Player player){
        PlayerDto playerDto = new PlayerDto();
        playerDto.setPlayerId(player.getPlayerId());
        playerDto.setName(player.getName());
        playerDto.setDate(player.getDate());
        playerDto.setWinningPercentage(player.getWinningPercentage());
        return playerDto;
    }
}