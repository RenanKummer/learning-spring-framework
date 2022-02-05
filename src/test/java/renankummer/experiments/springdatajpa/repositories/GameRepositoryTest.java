package renankummer.experiments.springdatajpa.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import renankummer.experiments.springdatajpa.dto.GenreStatistics;
import renankummer.experiments.springdatajpa.entities.Game;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class GameRepositoryTest {
    @Autowired private GameRepository gameRepository;

    @Test
    void findGamesWithGenres_EmptyTable_ReturnsEmptyList() {
        var games = gameRepository.findGamesWithGenres();
        assertThat(games).isEmpty();
    }

    @Test
    @Sql("/data/GameRepository/games-without-genres.sql")
    void findGamesWithGenres_AllWithoutGenres_ReturnsEmptyList() {
        var games = gameRepository.findGamesWithGenres();
        assertThat(games).isEmpty();
    }

    @Test
    @Sql("/data/GameRepository/games-with-genres.sql")
    void findGamesWithGenres_HasGenres_ReturnsGamesWithGenres() {
        var games = gameRepository.findGamesWithGenres();

        assertAll(
                () -> assertThat(games).isNotEmpty(),
                () -> assertThat(games).extracting(Game::getGenres).noneMatch(List::isEmpty)
        );
    }

    @Test
    void countGamesPerGenre_EmptyTable_ReturnsEmptyList() {
        var games = gameRepository.countGamesPerGenre();
        assertThat(games).isEmpty();
    }

    @Test
    @Sql("/data/GameRepository/games-without-genres.sql")
    void countGamesPerGenre_AllWithoutGenres_ReturnsEmptyList() {
        var games = gameRepository.countGamesPerGenre();
        assertThat(games).isEmpty();
    }

    @Test
    @Sql("/data/GameRepository/games-with-genres.sql")
    void countGamesPerGenre_HasGenres_ReturnsGamesWithGenres() {
        var games = gameRepository.countGamesPerGenre();

        assertAll(
                () -> assertThat(games).isNotEmpty(),
                () -> assertThat(games).extracting(GenreStatistics::getGenre).isNotNull(),
                () -> assertThat(games).extracting(GenreStatistics::getTotalGames).allMatch(total -> total > 0)
        );
    }
}
