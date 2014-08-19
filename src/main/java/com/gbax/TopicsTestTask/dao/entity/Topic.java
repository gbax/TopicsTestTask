package com.gbax.TopicsTestTask.dao.entity;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "topic")
public class Topic  implements java.io.Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Message> messages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
