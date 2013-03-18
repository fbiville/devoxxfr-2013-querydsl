package com.github.fbiville.devoxxfr.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Coder {

    private final String lastName;
    private final String firstName;
    private final String email;
    private final String town;
    private final List<Liking> likings;

    public Coder() {
        lastName = "";
        firstName = "";
        email = "";
        town = "";
        likings = newArrayList();
    }

    @JsonCreator
    public Coder(@JsonProperty("NOM") String lastName,
                    @JsonProperty("PRENOM") String firstName,
                    @JsonProperty("EMAIL") String email,
                    @JsonProperty("VILLE") String town,
                    @JsonProperty("LIKINGS") List<Liking> likings) {

        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.town = town;
        this.likings = likings;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getTown() {
        return town;
    }

    public List<Liking> getLikings() {
        return likings;
    }


}
