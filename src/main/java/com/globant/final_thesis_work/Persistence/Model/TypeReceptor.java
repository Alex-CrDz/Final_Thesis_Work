package com.globant.final_thesis_work.Persistence.Model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "TYPE_RECEPTOR")
@Data
public class TypeReceptor {
    @Transient
    public static final String TO = "TO";
    @Transient
    public static final String CC = "CC";
    @Transient
    public static final String BCC = "BCC";

    @Id
    @Column(name = "idTypeReceptor")
    private long idTypeReceptor;
    @Column(name = "nameTypeReceptor")
    private String nameTypeReceptor;
}
