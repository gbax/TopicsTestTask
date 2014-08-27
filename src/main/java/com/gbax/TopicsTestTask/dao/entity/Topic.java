package com.gbax.TopicsTestTask.dao.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
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

    @OneToMany(mappedBy = "topic")
    private List<Message> messages;

    //TODO Сделать обновляемое поле!
/*    @ManyToOne
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumnsOrFormulas({
            @JoinColumnOrFormula(formula =
            @JoinFormula(value = "(select m.id from message m where m.topic_id = id order by m.date desc LIMIT 1)"))
    })
    private Message message;*/

    @Version
    private Integer version;

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

/*    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }*/

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
