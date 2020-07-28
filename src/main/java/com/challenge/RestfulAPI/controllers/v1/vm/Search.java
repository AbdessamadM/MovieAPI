package com.challenge.RestfulAPI.controllers.v1.vm;

import com.challenge.RestfulAPI.api.v1.models.MovieDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author abdessamadM on 24/06/2020
 *
 * Cette classe sert comme Wrapper.
 * OMDb API renvoie un objet de niveau supérieur contenant une liste des movies.
 * Pour gérer cette situation j'ai dû créer cette classe wrapper contenant la liste des movies 'MovieDTO";
 * L'annotation @JsonProperty va servir à matcher le nom de la propriété json avec l'attribut de la classe Search
 */
@JsonIgnoreProperties // Annotation pour indiquer d'ignorer les propriétes qui ne match pas la réponse JSON
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search implements Serializable {

    private static final long serialVersionUID = 1L;

    // Liste des films qui contiendront les données de l'Api
    @JsonProperty("Search")
    private List<MovieDTO> movieDTOS;

    // Le nombre d'objet de film retourné par l'Api
    @JsonProperty("totalResults")
    private String totalResults;

    // Response: renvoie True si la requête renvoie un ou plusieurs objet movie, False sinon
    @JsonProperty("Response")
    private String searchResponse;
}

