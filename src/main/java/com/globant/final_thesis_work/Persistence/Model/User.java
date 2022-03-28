package com.globant.final_thesis_work.Persistence.Model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "[USER]")
@Data
@Builder
public class User {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "idNumber")
    private String idNumber;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "address")
    private String address;
    @Column(name = "zipCode")
    private String zipCode;
    @Column(name = "state")
    private String state;
    @Column(name = "country")
    private String country;
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "creationDate")
    private LocalDateTime creationDate;
    @Column(name = "lastModifiedDate")
    private LocalDateTime lastModifiedDate;

    /*--------- RELATIONSHIP --------*/
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "idRole"))
    private List<Role> roles;

    @OneToMany(mappedBy = "idLabel", cascade = CascadeType.ALL)
    private List<LabelMessage> labels;

    @OneToMany(mappedBy = "idMessage", cascade = CascadeType.ALL)
    private List<Message> sendMessages;

    @OneToMany(mappedBy = "idReceptor", cascade = CascadeType.ALL)
    private List<MessageReceptor> receivedMessages;

    public User() {
    }

    public User(String username, String password, String idNumber, String firstName, String lastName, String address, String zipCode, String state, String country, boolean enabled, LocalDateTime creationDate, LocalDateTime lastModifiedDate, List<Role> roles, List<LabelMessage> labels, List<Message> sendMessages, List<MessageReceptor> receivedMessages) {
        this.username = username;
        this.password = password;
        this.idNumber = idNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zipCode = zipCode;
        this.state = state;
        this.country = country;
        this.enabled = enabled;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.roles = roles;
        this.labels = labels;
        this.sendMessages = sendMessages;
        this.receivedMessages = receivedMessages;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", enabled=" + enabled +
                ", creationDate=" + creationDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
