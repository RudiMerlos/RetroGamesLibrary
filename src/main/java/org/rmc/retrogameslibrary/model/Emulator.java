package org.rmc.retrogameslibrary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "emulator")
@NamedQueries({@NamedQuery(name = "Emulator.findByName",
        query = "SELECT e FROM Emulator e WHERE e.name LIKE :name")})
public class Emulator {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String path;

    public Emulator(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
