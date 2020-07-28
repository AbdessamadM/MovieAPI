package com.challenge.RestfulAPI.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @Author abdessamadM on 23/06/2020
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Comment may not be null")
    @Column(name="comment", length = 1000)
    private String comment;

    @NotNull(message = "Date may not be null")
    private LocalDate date;

    @ManyToOne()
    private Movie movie;
}
