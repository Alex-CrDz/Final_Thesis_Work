package com.globant.final_thesis_work.Persistence.Model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "LABEL_MESSAGE")
@Data
@Builder
public class LabelMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLabel")
    private long idLabel;
    @Column(name = "label")
    private String label;
    /* ----- RELATIONSHIPS ----- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMessage", referencedColumnName = "idMessage")
    private Message message;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    public LabelMessage() {
    }

    public LabelMessage(long idLabel, String label, Message message, User user) {
        this.idLabel = idLabel;
        this.label = label;
        this.message = message;
        this.user = user;
    }
}
