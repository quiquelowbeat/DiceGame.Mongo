package com.dicegame.mongo.controllers;

import com.dicegame.mongo.model.dto.PlayerDto;
import com.dicegame.mongo.model.services.PlayerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerServiceImpl playerService;

    private PlayerDto playerDto, playerDto2;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(PlayerControllerTest.class.getName());

    @BeforeEach
    void setUp() {

        playerDto = new PlayerDto();
        playerDto.setPlayerId("1L");
        playerDto.setName("FooFighter");
        playerDto.setDate(Calendar.getInstance().getTime());
        playerDto.setWinningPercentage(50.00);

        playerDto2 = new PlayerDto();
        playerDto2.setPlayerId("2L");
        playerDto2.setName("Foo2");
        playerDto2.setDate(Calendar.getInstance().getTime());
        playerDto2.setWinningPercentage(100.0);
    }

    @DisplayName("JUnit test for createPlayer_withName method")
    @Test
    void createPlayer_withName() throws Exception {
        when(playerService.createPlayer("FooFighter")).thenReturn(playerDto);
        String response = mockMvc.perform(post("/players/{name}", "FooFighter")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("FooFighter"))
                .andReturn().getResponse().getContentAsString();
        logger.info(response);
    }

    @DisplayName("JUnit test for updatePlayerName method")
    @Test
    void updatePlayerName() throws Exception {
        playerDto.setName("FooFoo");
        when(playerService.updatePlayerName(playerDto)).thenReturn(playerDto);

        String response = mockMvc.perform(put("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playerDto))
                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("FooFoo"))
                .andReturn().getResponse().getContentAsString();
        logger.info(response);
    }

    @DisplayName("JUnit test for deleteAllGamesByPlayerId method")
    @Test
    void deleteAllGamesByPlayerId() throws Exception {
        mockMvc.perform(delete("/players/{id}/games", "1L"))
                .andExpect(status().isNoContent());
    }

    @DisplayName("JUnit test for deleteAllGamesByPlayerId_wrongId method")
    @Test
    void deleteAllGamesByPlayerId_wrongId() throws Exception {
        doThrow(NullPointerException.class).when(playerService).deleteAllGamesByPlayerId("2L");
        mockMvc.perform(delete("/players/{id}/games", "2L"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("JUnit test for getAllPlayers method")
    @Test
    void getAllPlayers() throws Exception {
        List<PlayerDto> playerDtoList = Arrays.asList(playerDto, playerDto2);
        given(playerService.getAllPlayers()).willReturn(playerDtoList);
        String response = mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andReturn().getResponse().getContentAsString();
        logger.info(response);
    }

    @DisplayName("JUnit test for getRankingOfAllPlayers method")
    @Test
    void getRankingOfAllPlayers() throws Exception {
        mockMvc.perform(get("/players/ranking"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("JUnit test for getLoser method")
    @Test
    void getLoser() throws Exception {
        given(playerService.getLoser()).willReturn(playerDto);
        mockMvc.perform(get("/players/ranking/loser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("FooFighter"))
                .andDo(print());
    }

    @DisplayName("JUnit test for getWinner method")
    @Test
    void getWinner() throws Exception {
        given(playerService.getWinner()).willReturn(playerDto2);
        mockMvc.perform(get("/players/ranking/winner"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Foo2"))
                .andDo(print());
    }
}