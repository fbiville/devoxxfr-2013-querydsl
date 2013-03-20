package com.github.fbiville.devoxxfr.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.persistence.*;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;
import static java.util.Locale.FRENCH;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.TABLE;

@Entity
@Table(name = "likings")
public class Liking {
    private Long id;
    private LikingType relationship;
    private int rank;
    private String description;

    Liking() {

    }

    @JsonCreator
    public Liking(@JsonProperty("TYPE") LikingType relationship,
                  @JsonProperty("RANK") int rank,
                  @JsonProperty("DESCRIPTION") String description) {
        this.relationship = relationship;
        this.rank = rank;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = TABLE)
    public Long getId() {
        return id;
    }

    @Enumerated(STRING)
    public LikingType getRelationship() {
        return relationship;
    }

    @Column
    public int getRank() {
        return rank;
    }

    @Column
    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRelationship(LikingType relationship) {
        this.relationship = relationship;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Liking liking = (Liking) other;

        return equal(getRelationship(), liking.getRelationship()) &&
                equal(getDescription().toLowerCase(FRENCH), liking.getDescription().toLowerCase(FRENCH));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getRelationship(), getRelationship());
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("-", getRelationship().name() + "->")
                .add(" ", getDescription())
                .toString();
    }
}
