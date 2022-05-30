package com.dicegame.mongo.model.services;

import com.dicegame.mongo.components.Mapper;
import com.dicegame.mongo.model.domains.Game;
import com.dicegame.mongo.model.domains.Player;
import com.dicegame.mongo.model.dto.GameDto;
import com.dicegame.mongo.model.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private Mapper mapper;

    @Mock
    private PlayerServiceImpl playerService;

    @InjectMocks
    private GameServiceImpl gameService;

    private Player player;

    @BeforeEach
    void setup() {
        // player
        player = Player.getInstance("FooFighter");
        player.setPlayerId("1L");
        player.setDate(Calendar.getInstance().getTime());
        player.setTotalWins(0);
        player.setWinningPercentage(0.00);
        Game game = Game.getInstance();
        player.getGames().add(game);
    }

    @DisplayName("JUnit test for newGame method")
    @Test
    void newGame() {
        // given
        given(playerService.findPlayer("1L")).willReturn(player);
        given(playerRepository.save(player)).willReturn(player);
        // when
        gameService.newGame("1L");
        // then
        assertThat(player.getGames().size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getGamesByPlayerId method")
    @Test
    void getGamesByPlayerId() {
        // given
        given(playerService.findPlayer("1L")).willReturn(player);
        // when
        List<GameDto> gameList = gameService.getGamesByPlayerId(player.getPlayerId());
        // then
        assertThat(gameList).isNotEmpty();
    }
}