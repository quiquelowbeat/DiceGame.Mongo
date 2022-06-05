package com.dicegame.mongo.controllers;

import com.dicegame.mongo.model.dto.PlayerDto;
import com.dicegame.mongo.model.services.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @Operation(summary = "Create new player.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New player created."),
            @ApiResponse(responseCode = "500", description = "Error during process.")})
    @PostMapping({"/players", "/players/{name}"})
    public ResponseEntity<PlayerDto> createPlayer(@PathVariable(required = false) String name){
        try{
            return new ResponseEntity<>(playerService.createPlayer(name), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Updates player's name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Name updated."),
            @ApiResponse(responseCode = "404", description = "Player not found.")})
    @PutMapping("/players")
    public ResponseEntity<PlayerDto> updatePlayerName(@RequestBody PlayerDto playerDto){
        try{
            return new ResponseEntity<>(playerService.updatePlayerName(playerDto), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deletes player's dice roll record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Games deleted."),
            @ApiResponse(responseCode = "404", description = "Player not found.")})
    @DeleteMapping("/players/{id}/games")
    public ResponseEntity<HttpStatus> deleteAllGamesByPlayerId(@PathVariable String id){
        try{
            playerService.deleteAllGamesByPlayerId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Reads all players.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Players not found."),
            @ApiResponse(responseCode = "200", description = "Shows players list.")})
    @GetMapping("/players")
    public ResponseEntity<List<PlayerDto>> getAllPlayers(){
        List<PlayerDto> playerDtoList = new ArrayList<>(playerService.getAllPlayers());
        if(playerDtoList.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(playerDtoList, HttpStatus.OK);
        }
    }

    @Operation(summary = "Reads ranking.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking shown correctly."),
            @ApiResponse(responseCode = "204", description = "Ranking is empty.")})
    @GetMapping("/players/ranking")
    public ResponseEntity<Map<String, Double>> getRankingOfAllPlayers(){
        try{
            return new ResponseEntity<>(playerService.getRankingOfAllPlayers(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Reads loser.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows loser successfully."),
            @ApiResponse(responseCode = "204", description = "Ranking is empty.")})
    @GetMapping("/players/ranking/loser")
    public ResponseEntity<PlayerDto> getLoser(){
        try{
            return new ResponseEntity<>(playerService.getLoser(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Reads winner.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows winner successfully."),
            @ApiResponse(responseCode = "204", description = "Ranking is empty.")})
    @GetMapping("/players/ranking/winner")
    public ResponseEntity<PlayerDto> getWinner(){
        try{
            return new ResponseEntity<>(playerService.getWinner(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
}
