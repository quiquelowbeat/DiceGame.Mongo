package com.dicegame.mongo.model.services;

import com.dicegame.mongo.components.Mapper;
import com.dicegame.mongo.model.domains.Player;
import com.dicegame.mongo.model.dto.PlayerDto;
import com.dicegame.mongo.model.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private final PlayerRepository playerRepository;

    @Autowired
    private final Mapper mapper;

    @Override
    public PlayerDto createPlayer(String name) {
        String playerName = (name == null) ? "ANONYMOUS" : name;
        Player player = Player.getInstance(playerName);
        return mapper.toPlayerDto(playerRepository.save(player));
    }

    @Override
    public PlayerDto updatePlayerName(PlayerDto playerDto) {
        Player playerUpdated = null;
        playerUpdated = findPlayer(playerDto.getPlayerId());
        playerUpdated.setName(playerDto.getName());
        return mapper.toPlayerDto(playerRepository.save(playerUpdated));
    }

    @Override
    public void deleteAllGamesByPlayerId(String playerId){
        Player player = findPlayer(playerId);
        player.getGames().clear();
        player.setWinningPercentage(0.0d);
        player.setTotalWins(0);
        playerRepository.save(player);
    }

    @Override
    public List<PlayerDto> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(mapper::toPlayerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Double> getRankingOfAllPlayers() {
        Map<String, Double> rankingMap = new LinkedHashMap<>();
        List<Player> playerList = playerRepository.findAll().stream()
                .sorted(Comparator.comparing(Player::getWinningPercentage).reversed())
                .collect(Collectors.toList());
        if(playerList.isEmpty()) throw new NullPointerException("Player's list is empty.");
        playerList.forEach(p -> rankingMap.put("playerId: " + p.getPlayerId(), p.getWinningPercentage()));
        return rankingMap;
    }

    @Override
    public PlayerDto getLoser() {
        Player player = null;
        Optional<Player> playerOptional = playerRepository.findAll().stream()
                .min(Comparator.comparing(Player::getWinningPercentage));
        if(playerOptional.isPresent()){
            player = playerOptional.get();
        }
        return mapper.toPlayerDto(player);
    }

    @Override
    public PlayerDto getWinner() {
        Player player = null;
        Optional<Player> playerOptional = playerRepository.findAll().stream()
                 .max(Comparator.comparing(Player::getWinningPercentage));
         if(playerOptional.isPresent()){
            player = playerOptional.get();
         }
         return mapper.toPlayerDto(player);
    }

    @Override
    public Player findPlayer(String playerId){
        Optional<Player> playerOptional = playerRepository.findByPlayerId(playerId);
        Player player = null;
        if(!playerOptional.isPresent()){
            System.err.println("Player not found.");
            return null;
        }
        player = playerOptional.get();
        return player;
    }
}
