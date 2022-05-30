package com.dicegame.mongo.model.repositories;

import com.dicegame.mongo.model.domains.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {
    Optional<Player> findByPlayerId(String id);
}
