package com.example.cafe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentsId;
    private String commentsContent;
    @ManyToOne
    @JoinColumn(name = "comments_author", referencedColumnName = "memId")
    private Member commentsAuthor;
    @ManyToOne
    @JoinColumn(name = "parent_cpost", referencedColumnName = "communityId")
    private Community parentCpost;
    @ManyToOne
    @JoinColumn(name = "parent_apost", referencedColumnName = "artistId")
    private Artist parentApost;
    private Timestamp createDate;
    private Timestamp updateDate;

    @PrePersist
    protected void onCreate() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        this.createDate = currentTimestamp;
        this.updateDate = currentTimestamp;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDate = new Timestamp(System.currentTimeMillis());  // Only update the updateDate when the entity is updated
    }
}
