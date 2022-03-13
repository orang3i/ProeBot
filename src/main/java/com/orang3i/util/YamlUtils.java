package com.orang3i.util;

import com.orang3i.ProeBot;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

public final class YamlUtils {
    public static Map<String,Object> loadFile(final String fileName) {
        final File file = new File(fileName);
        if(!file.exists()) {
            try {
                Files.copy(Objects.requireNonNull(ProeBot.class.getResourceAsStream("/" + fileName)), file.toPath());
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        try (final FileReader reader = new FileReader(file)) {
            return new Yaml().load(reader);
        } catch (final IOException e) {
            throw new IllegalArgumentException("Could not parse config.yml file");
        }
    }
}
