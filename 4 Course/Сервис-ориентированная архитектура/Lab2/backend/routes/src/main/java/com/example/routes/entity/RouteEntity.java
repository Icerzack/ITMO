package com.example.routes.entity;

import java.time.ZonedDateTime;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Accessors(chain = true)
@Table(name = "routes")
public class RouteEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "from_location_id", referencedColumnName = "id", nullable = false)
    private LocationEntity locationFrom;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "to_location_id", referencedColumnName = "id", nullable = false)
    private LocationEntity locationTo;

    @Column(name = "distance", nullable = false)
    private Float distance;
}
