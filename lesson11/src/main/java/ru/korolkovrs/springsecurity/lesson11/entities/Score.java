package ru.korolkovrs.springsecurity.lesson11.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "scores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    @Id
    private Long id;

    @Column(name = "score")
    private Integer score;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
}
