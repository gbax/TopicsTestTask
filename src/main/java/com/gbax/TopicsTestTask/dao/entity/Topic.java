package com.gbax.TopicsTestTask.dao.entity;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

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

    @ManyToOne
    @JoinColumnsOrFormulas({
            @JoinColumnOrFormula(formula =
            @JoinFormula(value = "(select m.id from message m where m.topic_id = id order by m.date desc LIMIT 1)"))
    })
    private Message message;

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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
