package com.dicegame.mongo.model.services;

import com.dicegame.mongo.model.dto.GameDto;

import java.util.List;

public interface GameService {

    GameDto newGame(String playerId);
    List<GameDto> getGamesByPlayerId(String playerId);

}
