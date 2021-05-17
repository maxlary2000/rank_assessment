package com.rank.assess.player;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class PlayerConfig {
    @Bean
    CommandLineRunner commandLineRunner(PlayerRepository playerRepository){
        return args -> {
            Player max = new Player("max.lary", new BigDecimal(1000));
            Player john = new Player("john.carter", BigDecimal.ZERO);
            Player chantele = new Player("chantele.daniller", new BigDecimal(1000));

            playerRepository.save(max);
            playerRepository.save(john);
            playerRepository.save(chantele);
        };
    };
}
