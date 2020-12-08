package org.rmc.retrogameslibrary.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "platform")
public class Platform {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String model;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String company;

    @Column(columnDefinition = "YEAR", nullable = false)
    private int year;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Game> games = new ArrayList<>();

    // Helper methods
    public void addGame(Game game) {
        games.add(game);
        game.setPlatform(this);
    }

    public void removeGame(Game game) {
        games.remove(game);
        game.setPlatform(null);
    }
}
