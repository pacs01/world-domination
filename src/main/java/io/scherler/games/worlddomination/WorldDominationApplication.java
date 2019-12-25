package io.scherler.games.worlddomination;

import io.scherler.games.worlddomination.entities.repositories.NaturalRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = NaturalRepositoryImpl.class)
public class WorldDominationApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorldDominationApplication.class, args);
    }
}
