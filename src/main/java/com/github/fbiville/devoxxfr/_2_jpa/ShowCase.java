package com.github.fbiville.devoxxfr._2_jpa;

import com.github.fbiville.devoxxfr.domain.Coder;
import com.github.fbiville.devoxxfr.domain.Liking;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

import java.io.IOException;
import java.util.Collection;

import static com.github.fbiville.devoxxfr.framework.hibernate.EntityScanner.scanEntities;

public class ShowCase {

    public static void main(String[] args) throws IOException {
        SessionFactory sessionFactory = bootstrapSessionFactory();

        String firstName = "Florent";
        String lastName = "Biville";

        CheapRecommendationEngine engine = new CheapRecommendationEngine(sessionFactory)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withDataImported();

        Collection<Coder> coders = engine.findSimilarCoders();
        Collection<?> likings = engine.readLikings();

        engine.close();


        System.out.println(" ");
        System.out.println(firstName + " " + lastName);
        for (Object liking : likings) {
            System.out.println(liking);
        }

        for (Coder cod3r : coders) {
            System.out.println();
            System.out.println(cod3r);
            for (Liking liking : cod3r.getLikings()) {
                if (likings.contains(liking)) {
                    System.out.println(liking);
                }
            }

        }
    }

    private static SessionFactory bootstrapSessionFactory() {
        Configuration configuration = configure();
        ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
        return sessionFactory;
    }

    private static Configuration configure() {
        Configuration configuration = new Configuration();
        configuration.configure("/hibernate.xml");
        for (Class<?> entityClass : scanEntities("com.github.fbiville.devoxxfr.domain")) {
            configuration.addAnnotatedClass(entityClass);
        }
        return configuration;
    }

}
