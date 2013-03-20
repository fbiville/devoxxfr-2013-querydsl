package com.github.fbiville.devoxxfr.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.mysema.query.annotations.QueryEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Objects.equal;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Locale.FRENCH;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.TABLE;

@Entity
@Table(name = "coders")
public class Coder {
    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private String town;
    private Collection<Liking> likings;

    Coder() {
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

    @Id
    @GeneratedValue(strategy = TABLE)
    public Long getId() {
        return id;
    }

    @Column
    public String getLastName() {
        return lastName;
    }

    @Column
    public String getFirstName() {
        return firstName;
    }

    @Column
    public String getEmail() {
        return email;
    }

    @Column
    public String getTown() {
        return town;
    }

    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "coders_likings",
            joinColumns = {@JoinColumn(name="coder_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="liking_id", referencedColumnName="id")})
    public Collection<Liking> getLikings() {
        return likings;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setLikings(Collection<Liking> likings) {
        this.likings = likings;
    }

    @Override
    public boolean equals(Object otherCoder) {
        if (this == otherCoder) return true;
        if (otherCoder == null || getClass() != otherCoder.getClass()) return false;

        Coder coder = (Coder) otherCoder;

        return equal(getEmail(), coder.getEmail()) &&
                equal(getTown(), coder.getTown()) &&
                equal(getFirstName(), coder.getFirstName()) &&
                equal(getLastName(), coder.getLastName()) &&
                equal(getLikings(), coder.getLikings());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmail(), getTown(), getFirstName(), getLastName(), getLikings());
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName().toUpperCase(FRENCH) + " [" + getTown() + "]";
    }
}
