package com.github.fbiville.devoxxfr.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

public class Liking {

    private final LikingType relationship;
    private final int rank;
    private final String description;

    @JsonCreator
    public Liking(@JsonProperty("TYPE") LikingType relationship, @JsonProperty("RANK") int rank, @JsonProperty("DESCRIPTION") String description) {
        this.relationship = relationship;
        this.rank = rank;
        this.description = description;
    }

    public LikingType getRelationship() {
        return relationship;
    }

    public int getRank() {
        return rank;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Liking liking = (Liking) other;

        return equal(getRelationship(), liking.getRelationship()) &&
                equal(getRank(), liking.getRank()) &&
                equal(getDescription(), liking.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getRelationship(), getRank(), getRelationship());
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("relationship type", getRelationship())
                .add("rank", getRank())
                .add("object of passion", getDescription())
                .toString();
    }
}
