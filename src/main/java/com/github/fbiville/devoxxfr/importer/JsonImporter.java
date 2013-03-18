package com.github.fbiville.devoxxfr.importer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fbiville.devoxxfr.domain.Coder;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

public class JsonImporter {

    public static Collection<Coder> importCoders() throws IOException {
        URL resource = JsonImporter.class.getResource("/codestory2013.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(resource, new TypeReference<Collection<Coder>>() {});
    }

}
