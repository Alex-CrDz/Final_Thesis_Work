package com.globant.final_thesis_work.Persistence.Model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ROLE")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRole")
    private long idRole;
    @Column(name = "nameRole")
    private String nameRole;
    @Column(name = "descriptionRole")
    private String descriptionRole;
}
