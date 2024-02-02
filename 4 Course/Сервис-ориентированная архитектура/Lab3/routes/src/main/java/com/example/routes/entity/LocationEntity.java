package com.example.routes.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Accessors(chain = true)
@Table(name = "locations")
public class LocationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @JoinColumn(name = "coordinates")
    private CoordinateEntity coordinates;

    @Column(name = "name")
    private String name;
}
