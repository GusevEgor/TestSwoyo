package org.example.testswoyo.server.entity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "answers")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title_of_answer")
    private String titleOfAnswer;

    @Column(name = "number of votes")
    private Integer numberOfVotes;

    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;
}
