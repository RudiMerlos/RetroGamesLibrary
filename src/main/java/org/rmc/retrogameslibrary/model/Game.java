package org.rmc.retrogameslibrary.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game")
@NamedQueries({@NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g"),
        @NamedQuery(name = "Game.findByTitle",
                query = "SELECT g FROM Game g WHERE g.title LIKE :title"),
        @NamedQuery(name = "Game.findByYear", query = "SELECT g FROM Game g WHERE g.year = :year"),
        @NamedQuery(name = "Game.findByGender",
                query = "SELECT g FROM Game g WHERE g.gender LIKE :gender"),
        @NamedQuery(name = "Game.findByPlatform",
                query = "SELECT g FROM Game g WHERE g.platform LIKE :platform"),
        @NamedQuery(name = "Game.searchByTitle",
                query = "SELECT g FROM Game g WHERE LOWER(g.title) LIKE :title")})
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "YEAR")
    private Integer year;

    @Column(columnDefinition = "VARCHAR(50)")
    private String gender;

    @Column
    private String screenshot;

    @Column
    private String path;

    @Column
    private String command;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Platform platform;

    public Game(String title, String description, Integer year, String gender, String screenshot,
            String path, String command, Platform platform) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.gender = gender;
        this.screenshot = screenshot;
        this.path = path;
        this.command = command;
        this.platform = platform;
    }
}
