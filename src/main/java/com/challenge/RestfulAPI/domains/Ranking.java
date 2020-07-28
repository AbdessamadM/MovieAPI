package com.challenge.RestfulAPI.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author abdessamadM on 25/06/2020
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ranking")
public class Ranking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="movie_id")
    private Integer movieId;

    @Column(name="total_comments")
    private Integer totalComments;

    @Column(name="`rank`")
    private Integer rank;
}
