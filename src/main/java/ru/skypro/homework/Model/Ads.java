package ru.skypro.homework.Model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    private String title;

    private String description;

    private String image;

    private Float price;

    @Transient
    @OneToMany(mappedBy = "ads")
    private Collection<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ads)) return false;
        Ads ads = (Ads) o;
        return getPk().equals(ads.getPk()) && getTitle().equals(ads.getTitle()) && getDescription().equals(ads.getDescription()) && Objects.equals(getImage(), ads.getImage()) && Objects.equals(getPrice(), ads.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPk(), getTitle(), getDescription(), getImage(), getPrice());
    }

    @Override
    public String toString() {
        return "Ads{" +
                "pk=" + pk +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", comments=" + comments +
                ", author=" + author +
                '}';
    }
}
