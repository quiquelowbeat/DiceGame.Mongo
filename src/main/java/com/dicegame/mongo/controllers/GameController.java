package com.dicegame.mongo.controllers;

import com.dicegame.mongo.model.dto.GameDto;
import com.dicegame.mongo.model.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/players/{id}/games")
    public ResponseEntity<GameDto> newGame(@PathVariable String id){
        try{
            return new ResponseEntity<>(gameService.newGame(id), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/players/{id}/games")
    public ResponseEntity<List<GameDto>> getGamesByPlayerId(@PathVariable String id){
        try{
            return new ResponseEntity<>(gameService.getGamesByPlayerId(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
}
