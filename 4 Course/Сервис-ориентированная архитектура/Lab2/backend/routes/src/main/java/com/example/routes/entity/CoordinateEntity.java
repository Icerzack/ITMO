package com.example.routes.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Accessors(chain = true)
public class CoordinateEntity {
    @Column(name = "x", nullable = false)
    private int x;

    @Column(name = "y", nullable = false)
    private float y;
}
