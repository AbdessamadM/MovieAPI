package com.challenge.RestfulAPI.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author abdessamadM on 23/06/2020
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "movie")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title may not be null")
    @Column(name="title", length = 250)
    private String title;

    @Column(name="year", length = 4)
    private String year;

    @Column(name="imdb_id", length = 20)
    private String imdbId;

    @Column(name="type", length = 20)
    private String type;

    @Column(name="poster_url", length = 250)
    private String posterUrl;

}
