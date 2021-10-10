package com.pavelbelov.recipe.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Pavel Belov on 25.09.2021
 */
@Data
@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

}
