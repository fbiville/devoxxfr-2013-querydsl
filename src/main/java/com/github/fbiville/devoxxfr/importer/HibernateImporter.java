package com.github.fbiville.devoxxfr.importer;

import com.github.fbiville.devoxxfr.domain.Coder;
import com.github.fbiville.devoxxfr.domain.Liking;
import com.google.common.base.Function;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.Collection;

import static com.google.common.base.Predicates.equalTo;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.find;

public class HibernateImporter {

    public static void importCoders(SessionFactory sessionFactory) throws IOException {
        Session session = sessionFactory.openSession();
        Collection<Coder> coders = JsonImporter.importCoders();

        Iterable<Liking> likings = saveLikings(session, coders);
        saveCoders(session, coders, likings);

        session.flush();
        session.close();
    }

    private static Iterable<Liking> saveLikings(Session session, Collection<Coder> coders) {
        Iterable<Liking> likings = getLikings(coders);
        for (Liking liking : likings) {
            session.saveOrUpdate(liking);
        }
        return likings;
    }

    private static Iterable<Liking> getLikings(Collection<Coder> coders) {
        return from(coders).transformAndConcat(new Function<Coder, Iterable<Liking>>() {
            @Override
            public Iterable<Liking> apply(Coder input) {
                return input.getLikings();
            }
        }).toSet();
    }

    private static void saveCoders(Session session, Collection<Coder> coders, final Iterable<Liking> likings) {
        for (Coder coder : coders) {
            Collection<Liking> managedLikings = findPersistedLikings(likings, coder);
            coder.setLikings(managedLikings);
            session.saveOrUpdate(coder);
        }
    }

    private static Collection<Liking> findPersistedLikings(final Iterable<Liking> likings, Coder coder) {
        return transform(coder.getLikings(), new Function<Liking, Liking>() {
            @Override
            public Liking apply(Liking input) {
            return find(likings, equalTo(input));
            }
        });
    }
}
