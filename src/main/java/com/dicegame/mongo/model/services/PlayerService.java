package com.dicegame.mongo.model.services;

import com.dicegame.mongo.model.domains.Player;
import com.dicegame.mongo.model.dto.PlayerDto;

import java.util.List;
import java.util.Map;

public interface PlayerService {

    PlayerDto createPlayer(String name);
    PlayerDto updatePlayerName(PlayerDto playerDto);
    void deleteAllGamesByPlayerId(String playerId);
    List<PlayerDto> getAllPlayers();
    Map<String, Double> getRankingOfAllPlayers();
    PlayerDto getLoser();
    PlayerDto getWinner();
    Player findPlayer(String playerId);

}
