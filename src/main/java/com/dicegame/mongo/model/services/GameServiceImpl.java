package com.dicegame.mongo.model.services;

import com.dicegame.mongo.components.Mapper;
import com.dicegame.mongo.model.domains.Game;
import com.dicegame.mongo.model.domains.Player;
import com.dicegame.mongo.model.dto.GameDto;
import com.dicegame.mongo.model.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final PlayerRepository playerRepository;
    private final PlayerServiceImpl playerService;

    @Autowired
    private final Mapper mapper;

    @Override
    public GameDto newGame(String playerId) {
        Player player = playerService.findPlayer(playerId);
        Game game = Game.getInstance();
        if(game.getResult().equals("WIN")) player.setTotalWins(player.getTotalWins() + 1);
        if(player.getGames() != null) player.getGames().add(game);
        player.setWinningPercentage(player.calculateWinningPercentage());
        playerRepository.save(player);
        return mapper.toGameDto(game);
    }

    @Override
    public List<GameDto> getGamesByPlayerId(String playerId){
        Player player = playerService.findPlayer(playerId);
        List<GameDto> gameDtoList = player.getGames().stream()
                .map(mapper::toGameDto)
                .collect(Collectors.toList());
        if(gameDtoList.isEmpty()) throw new NullPointerException("Game list is empty.");
        return gameDtoList;
    }
}
