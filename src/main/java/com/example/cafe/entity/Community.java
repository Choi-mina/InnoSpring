package com.example.cafe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Community {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int communityId;
    public String communityTitle;
    public String communityContent;
    @ManyToOne
    @JoinColumn(name = "community_author", referencedColumnName = "memId")
    private Member communityAuthor;
    public Timestamp createDate;
    public Timestamp updateDate;

    @OneToMany(mappedBy = "parentCpost", cascade = CascadeType.ALL)
    private List<Comments> comments = new ArrayList<>();


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
