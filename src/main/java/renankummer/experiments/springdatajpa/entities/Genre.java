package renankummer.experiments.springdatajpa.entities;

import javax.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "genre", unique = true)
    private String genre;

    public long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
