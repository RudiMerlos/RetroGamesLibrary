package org.rmc.retrogameslibrary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "YEAR")
    private int year;

    @Column(columnDefinition = "VARCHAR(50)")
    private String gendre;

    @Column
    private String screenshot;

    @Column
    private String path;

    @ManyToOne
    private Platform platform;

    public Game(String title, String description, int year, String gendre, String screenshot,
            String path, Platform platform) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.gendre = gendre;
        this.screenshot = screenshot;
        this.path = path;
        this.platform = platform;
    }
}
