package com.gbax.TopicsTestTask.dao.entity;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    @OneToMany(mappedBy="topic", fetch = FetchType.LAZY)
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
}
