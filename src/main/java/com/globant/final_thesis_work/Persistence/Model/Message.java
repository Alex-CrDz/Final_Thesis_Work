package com.globant.final_thesis_work.Persistence.Model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "MESSAGE")
@Data
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMessage")
    private long idMessage;
    @Column(name = "subject")
    private String subject;
    @Column(name = "body")
    private String body;
    @Column(name = "creationDate")
    private LocalDateTime creationDate;
    @Column(name = "deleted")
    private boolean deleted;

    /* ----- RELATIONSHIPS ----- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender", referencedColumnName = "username")
    private User sender;

    @OneToMany(mappedBy = "idLabel", cascade = CascadeType.ALL)
    private List<LabelMessage> labelMessage;

    @OneToMany(mappedBy = "idReceptor", cascade = CascadeType.ALL)
    private List<MessageReceptor> receptors;

    public Message() {
    }

    public Message(long idMessage, String subject, String body, LocalDateTime creationDate, boolean deleted, User sender, List<LabelMessage> labelMessage, List<MessageReceptor> receptors) {
        this.idMessage = idMessage;
        this.subject = subject;
        this.body = body;
        this.creationDate = creationDate;
        this.deleted = deleted;
        this.sender = sender;
        this.labelMessage = labelMessage;
        this.receptors = receptors;
    }
}
