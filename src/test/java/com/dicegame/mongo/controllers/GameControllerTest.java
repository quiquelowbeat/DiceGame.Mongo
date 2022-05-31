package com.dicegame.mongo.controllers;

import com.dicegame.mongo.model.dto.GameDto;
import com.dicegame.mongo.model.services.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameServiceImpl gameService;

    private GameDto gameDto;
    private static final Logger logger = Logger.getLogger(GameControllerTest.class.getName());


    @BeforeEach
    void setUp() {
        gameDto = new GameDto();
        gameDto.setDice1(1);
        gameDto.setDice2(6);
        gameDto.setResult("WIN");
    }

    @DisplayName("JUnit test for newGame() method")
    @Test
    void newGame() throws Exception {
        when(gameService.newGame("1L")).thenReturn(gameDto);

        String response = mockMvc.perform(post("/players/{id}/games", "1L")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dice1").value(1))
                .andExpect(jsonPath("$.dice2").value(6))
                .andExpect(jsonPath("$.result").value("WIN"))
                .andReturn().getResponse().getContentAsString();
        logger.info(response);
    }

    @DisplayName("JUnit test for newGame_nullObject method")
    @Test
    void newGame_nullObject() throws Exception {
        when(gameService.newGame("1L")).thenThrow(NullPointerException.class);

        mockMvc.perform(post("/players/{id}/games", "1L"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("JUnit test for getGamesByPlayerId method")
    @Test
    void getGamesByPlayerId() throws Exception {
        GameDto gameDto2 = new GameDto();
        gameDto2.setDice1(1);
        gameDto2.setDice2(5);
        gameDto2.setResult("LOSE");
        List<GameDto> gameDtoList = Arrays.asList(gameDto, gameDto2);

        when(gameService.getGamesByPlayerId("1L")).thenReturn(gameDtoList);

        String response = mockMvc.perform(get("/players/{id}/games", "1L")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        logger.info(response);
    }

    @DisplayName("JUnit test for getGamesByPlayerId_NullObject method")
    @Test
    void getGamesByPlayerId_withToken_NullObject() throws Exception {
        when(gameService.getGamesByPlayerId("1L")).thenThrow(NullPointerException.class);
        mockMvc.perform(get("/players/{id}/games", "1L"))
                .andExpect(status().isNoContent());
    }

}