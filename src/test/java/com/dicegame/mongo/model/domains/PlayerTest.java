package com.dicegame.mongo.model.domains;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlayerTest {

    @Test
    void getInstanceWithName() {
        // when
        Player player = Player.getInstance("FooFighter");
        // then
        assertEquals("FooFighter", player.getName());
        assertNotNull(player);
    }

    @Test
    void getInstanceWithoutName() {
        // when
        Player player = Player.getInstance(null);
        player.setName("ANONYMOUS");
        // then
        assertEquals("ANONYMOUS", player.getName());
        assertNotNull(player);
    }
}