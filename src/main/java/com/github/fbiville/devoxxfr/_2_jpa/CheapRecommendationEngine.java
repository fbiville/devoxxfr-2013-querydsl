package com.github.fbiville.devoxxfr._2_jpa;

import com.github.fbiville.devoxxfr.domain.Coder;
import com.github.fbiville.devoxxfr.domain.Liking;
import com.github.fbiville.devoxxfr.domain.QCoder;
import com.github.fbiville.devoxxfr.domain.QLiking;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.SimpleExpression;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.Collection;

import static com.github.fbiville.devoxxfr.importer.HibernateImporter.importCoders;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Locale.FRENCH;

public class CheapRecommendationEngine {

    private SessionFactory sessionFactory;
    private String firstName;
    private String lastName;
    private final Session currentSession;

    public CheapRecommendationEngine(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.currentSession = sessionFactory.openSession();
    }

    public CheapRecommendationEngine withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CheapRecommendationEngine withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CheapRecommendationEngine withDataImported() throws IOException {
        importCoders(sessionFactory);
        return this;
    }

    public Collection<Coder> findSimilarCoders() {
        checkState(parametersAreSet());

        HibernateQuery query = new HibernateQuery(currentSession);
        QCoder target = QCoder.coder;
        QCoder rest = new QCoder("rest");
        return query.from(target, rest)
            .where(
                    target.firstName.toLowerCase().eq(firstName.toLowerCase(FRENCH))
                    .and(target.lastName.toLowerCase().eq(lastName.toLowerCase(FRENCH)))
                    .and(rest.ne(target))
                    .and(rest.likings.any().in(target.likings))
            )
            .listDistinct(rest);
    }

    public Collection<?> readLikings() {
        checkState(parametersAreSet());

        QCoder coder = new QCoder("originalCoder");
        return new HibernateQuery(currentSession)
            .from(coder)
            .where(
                    coder.firstName.toLowerCase().eq(firstName.toLowerCase(FRENCH))
                    .and(coder.lastName.toLowerCase().eq(lastName.toLowerCase(FRENCH)))
            ).list(coder.likings);
    }

    public void close() {
        this.currentSession.close();
    }

    private boolean parametersAreSet() {
        return firstName != null && lastName != null;
    }
}
