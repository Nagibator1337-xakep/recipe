package com.pavelbelov.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Pavel Belov on 25.09.2021
 */
@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    // Lob is Large Object. Default value for a String field in JPA is 256 bytes, if we want to use more we use
    // @Lob annotation
    @Lob
    private String recipeNotes;
}
