package com.ceres.domain.entity.message;

import com.ceres.domain.LocalDatePersistenceConverter;
import com.ceres.domain.LocalTimePersistenceConverter;
import com.ceres.domain.entity.AbstractEntity;
import com.ceres.domain.entity.human.Human;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Created by leonardo on 25/04/15.
 */
@Entity
@Table(name = "CHAT",
        indexes = {@Index(name = "SENDER_INDEX", columnList = "sender_id", unique = false),
                @Index(name = "RECEIVER_INDEX", columnList = "receiver_id", unique = false)})
@ApiModel(value = "chatMessage")
@XmlRootElement(namespace = "ceres")
public class ChatMessage extends AbstractEntity implements Comparable<ChatMessage> {

    @Column(name = "TIME", nullable = false, columnDefinition = "TIME NOT NULL")
    @Convert(converter = LocalTimePersistenceConverter.class)
    @ApiModelProperty(value = "hour", example = "hh:mm:ss")
    private LocalTime hour;

    @Column(name = "DATE", nullable = false, columnDefinition = "DATE NOT NULL")
    @Convert(converter = LocalDatePersistenceConverter.class)
    @ApiModelProperty(value = "date", example = "dd-mm-aaaa")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_HUMAN_RECEIVER_ID"), nullable = false)
    @JsonIgnore
    private Human receiver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_HUMAN_SENDER_ID"), nullable = false)
    @JsonIgnore
    private Human sender;

    @Column(nullable = false, name = "MESSAGE")
    private String message;

    ChatMessage() {
    }

    public ChatMessage(String message, Human receiver, Human sender) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
        this.setHour(LocalTime.now());
        this.setDate(LocalDate.now());
    }

    public ChatMessage(String message, Human receiver, Human sender, LocalTime time) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
        this.hour = time;
        this.setDate(LocalDate.now());
    }


    public Human getReceiver() {
        return receiver;
    }

    public void setReceiver(Human receiver) {
        this.receiver = receiver;
    }

    public Human getSender() {
        return sender;
    }

    public void setSender(Human sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(getHour(), that.getHour()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHour(), getDate(), getMessage());
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "hour=" + hour +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public int compareTo(ChatMessage o) {
        return this.hour.compareTo(o.hour);
    }
}
