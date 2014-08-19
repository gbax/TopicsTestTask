package com.gbax.TopicsTestTask.dao.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User  implements java.io.Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    private List<Message> messages;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    private List<Topic> topic;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Topic> getTopic() {
        return topic;
    }

    public void setTopic(List<Topic> topic) {
        this.topic = topic;
    }
}
