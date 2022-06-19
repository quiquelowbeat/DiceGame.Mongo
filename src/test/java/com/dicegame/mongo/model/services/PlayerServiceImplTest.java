package com.dicegame.mongo.model.services;

import com.dicegame.mongo.components.PlayerDtoMapper;
import com.dicegame.mongo.model.domains.Game;
import com.dicegame.mongo.model.domains.Player;
import com.dicegame.mongo.model.dto.PlayerDto;
import com.dicegame.mongo.model.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerDtoMapper playerDtoMapper;

    @InjectMocks
    private PlayerServiceImpl playerService;

    private Player player;
    private Player player2;
    private PlayerDto playerDto;
    private PlayerDto playerDto2;

    @BeforeEach
    void setup(){
        // player
        player = Player.getInstance("FooFighter");
        player.setPlayerId("1L");
        player.setDate(Calendar.getInstance().getTime());
        player.setTotalWins(4);
        player.setWinningPercentage(35.00);
        player.getGames().add(Game.getInstance());
        player.getGames().add(Game.getInstance());
        player.getGames().add(Game.getInstance());

        // player2
        player2 = Player.getInstance("Quique");
        player2.setPlayerId("2L");
        player2.setWinningPercentage(55.00);

        // playerDto
        playerDto = new PlayerDto();
        playerDto.setPlayerId(player.getPlayerId());
        playerDto.setName(player.getName());
        playerDto.setDate(player.getDate());
        playerDto.setWinningPercentage(player.getWinningPercentage());

        // playerDto2
        playerDto2 = new PlayerDto();
        playerDto2.setPlayerId(player2.getPlayerId());
        playerDto2.setName(player2.getName());
        playerDto2.setDate(player2.getDate());
        playerDto2.setWinningPercentage(player2.getWinningPercentage());
    }

    @DisplayName("JUnit test for createPlayer method")
    @Test
    void createPlayer() {
        // given
        given(playerDtoMapper.toPlayerDto(playerRepository.save(player))).willReturn(playerDto);
        // when
        PlayerDto createdPlayer = playerService.createPlayer(player.getName());
        // then
        assertThat(createdPlayer).isNotNull();
    }

    @DisplayName("JUnit test for updatePlayerName method")
    @Test
    void updatePlayerName() {
        // given
        given(playerRepository.findByPlayerId("1L")).willReturn(Optional.of(player));
        playerDto.setName("NotFoo");
        given(playerDtoMapper.toPlayerDto(playerRepository.save(player))).willReturn(playerDto);
        // when
        PlayerDto updatedPlayer = playerService.updatePlayerName(playerDto);

        // then
        assertEquals("NotFoo", updatedPlayer.getName());
    }

    @DisplayName("JUnit test for deleteAllGamesByPlayerId method")
    @Test
    void deleteAllGamesByPlayerId() {
        // given
        given(playerRepository.findByPlayerId("1L")).willReturn(Optional.of(player));
        // when
        playerService.deleteAllGamesByPlayerId("1L");
        // then
        assertThat(player.getGames().size()).isZero();
    }

    @DisplayName("JUnit test for getAllPlayers method")
    @Test
    void getAllPlayers() {
        // given
        given(playerRepository.findAll()).willReturn(Arrays.asList(player, player2));
        // when
        List<PlayerDto> playerList = playerService.getAllPlayers();
        // then
        assertThat(playerList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getRankingOfAllPlayers method")
    @Test
    void getRankingOfAllPlayers() {
        // given
        given(playerRepository.findAll()).willReturn(Arrays.asList(player, player2));
        // when
        Map<String, Double> playerMap = playerService.getRankingOfAllPlayers();
        // then
        assertThat(playerMap.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getLoser method")
    @Test
    void getLoser() {
        // given
        List<Player> playerList = Arrays.asList(player, player2);
        given(playerRepository.findAll()).willReturn(playerList);
        given(playerDtoMapper.toPlayerDto(player)).willReturn(playerDto);
        // when
        PlayerDto playerLoser = playerService.getLoser();
        // then
        assertThat(playerLoser.getPlayerId()).isEqualTo("1L");
    }

    @DisplayName("JUnit test for getWinner method")
    @Test
    void getWinner() {
        // given
        List<Player> playerList = Arrays.asList(player, player2);
        given(playerRepository.findAll()).willReturn(playerList);
        given(playerDtoMapper.toPlayerDto(player2)).willReturn(playerDto2);
        // when
        PlayerDto playerWinnerr = playerService.getWinner();
        // then
        assertThat(playerWinnerr.getPlayerId()).isEqualTo("2L");
    }

    @DisplayName("JUnit test for findPlayer method")
    @Test
    void findPlayer() {
        // given
        given(playerRepository.findByPlayerId("1L")).willReturn(Optional.of(player));
        // when
        Player playerFound = playerService.findPlayer("1L");
        // then
        assertEquals(player, playerFound);
    }

    @DisplayName("JUnit test for findPlayer_NotFound method")
    @Test
    void findPlayer_NotFound() {
        // given
        // when
        Player playerFound = playerService.findPlayer("3L");
        // then
        assertThat(playerFound).isNull();
    }
}