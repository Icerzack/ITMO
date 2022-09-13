package com.lab4.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roleses")
public class Role {
    @Id
    @GeneratedValue(generator="some_seq_gen_name2")
    @SequenceGenerator(name="some_seq_gen_name2", sequenceName="SOME_SEQ_ROLE", allocationSize=1)
    private long id;

    private String name;

    public Role(String name) {
        this.name = name;
    }
}
