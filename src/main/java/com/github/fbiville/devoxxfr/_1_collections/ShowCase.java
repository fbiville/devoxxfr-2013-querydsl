package com.github.fbiville.devoxxfr._1_collections;

import com.github.fbiville.devoxxfr.domain.Coder;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static com.github.fbiville.devoxxfr.importer.JsonImporter.importCoders;
import static com.mysema.query.alias.Alias.$;
import static com.mysema.query.alias.Alias.alias;
import static com.mysema.query.collections.MiniApi.from;
import static java.util.Locale.FRENCH;

public class ShowCase {

    public static void main(String[] args) throws IOException {

        Collection<Coder> coders = importCoders();

        Map<String, Coder> matchingCoders = codersByTownMappedByLastName(coders, "paris");

        for (Map.Entry<String, Coder> coderTuple : matchingCoders.entrySet()) {
            Coder coder = coderTuple.getValue();
            System.out.println(coder.getFirstName() + " " + coderTuple.getKey().toUpperCase(FRENCH) + " [" + coder.getTown().toUpperCase(FRENCH) + "]");
        }


    }

    private static Map<String, Coder> codersByTownMappedByLastName(Collection<Coder> coders, String town) {
        Coder pathContext = alias(Coder.class, "coders");

        return from(pathContext, coders)
                .where($(pathContext.getTown()).isNotEmpty()
                        .and($(pathContext.getTown()).equalsIgnoreCase(town)))
                .orderBy($(pathContext.getFirstName()).asc())
                .map($(pathContext.getLastName()), $(pathContext));
    }
}
