package com.gbax.TopicsTestTask.dao.entity;

import org.codehaus.jackson.annotate.*;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message implements java.io.Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String message;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "messageID")
    private Topic topic;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String messsage) {
        this.message = messsage;
    }

    public Topic getTopic() {
        return topic;
    }

    @JsonIgnore
    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    @JsonIgnore
    public void setUser(User user) {
        this.user = user;
    }
}
