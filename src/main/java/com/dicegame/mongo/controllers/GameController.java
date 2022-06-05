package com.dicegame.mongo.controllers;

import com.dicegame.mongo.model.dto.GameDto;
import com.dicegame.mongo.model.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @Operation(summary = "Creates new game by player id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New game created successfully."),
            @ApiResponse(responseCode = "404", description = "Player not found")})
    @PostMapping("/players/{id}/games")
    public ResponseEntity<GameDto> newGame(@PathVariable String id){
        try{
            return new ResponseEntity<>(gameService.newGame(id), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Reads all games by player id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows dice roll record."),
            @ApiResponse(responseCode = "204", description = "Empty record.")})
    @GetMapping("/players/{id}/games")
    public ResponseEntity<List<GameDto>> getGamesByPlayerId(@PathVariable String id){
        try{
            return new ResponseEntity<>(gameService.getGamesByPlayerId(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
}
