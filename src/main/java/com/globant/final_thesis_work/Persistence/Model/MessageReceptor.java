package com.globant.final_thesis_work.Persistence.Model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "MESSAGE_RECEPTOR")
@Data
@Builder
public class MessageReceptor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReceptor")
    private long idReceptor;
    /* ----- RELATIONSHIPS ----- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMessage", referencedColumnName = "idMessage")
    private Message message;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User receptor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTypeReceptor", referencedColumnName = "idTypeReceptor")
    private TypeReceptor typeReceptor;

    public MessageReceptor() {
    }

    public MessageReceptor(long idReceptor, Message message, User receptor, TypeReceptor typeReceptor) {
        this.idReceptor = idReceptor;
        this.message = message;
        this.receptor = receptor;
        this.typeReceptor = typeReceptor;
    }
}
