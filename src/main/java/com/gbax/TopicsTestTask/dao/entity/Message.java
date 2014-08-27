package com.gbax.TopicsTestTask.dao.entity;

import org.codehaus.jackson.annotate.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message implements java.io.Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String message;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private Date date = new Date();

    @Version
    private Integer version;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
