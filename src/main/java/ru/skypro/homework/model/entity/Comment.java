package ru.skypro.homework.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long pk;

    private LocalDateTime createdAt;

    private String text;

    @ManyToOne
    @JoinColumn(name = "ads")
    private Ads ads;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;


    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment1 = (Comment) o;
        return getPk().equals(comment1.getPk());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPk());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "pk=" + pk +
                ", createdAt=" + createdAt +
                ", text='" + text + '\'' +
                ", ads=" + ads +
                ", author=" + author +
                '}';
    }
}
