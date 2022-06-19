package com.dicegame.mongo.model.repositories;

import com.dicegame.mongo.model.domains.Player;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
// Those tests will use an embedded in-memory MongoDB process by default if it is available.
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class PlayerRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void notEmpty(){
        Assertions.assertThat(mongoTemplate.findAll(Player.class)).isNotEmpty();
    }
}