package renankummer.experiments.springdatajpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import renankummer.experiments.springdatajpa.dto.GenreStatistics;
import renankummer.experiments.springdatajpa.entities.Game;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("from Game g join fetch g.genres")
    List<Game> findGamesWithGenres();
    @Query("""
        select
            genre.genre,
            count(genre.genre) as totalGames
        from Game game
            join game.genres genre
        group by genre.genre
        """)
    List<GenreStatistics> countGamesPerGenre();
}
