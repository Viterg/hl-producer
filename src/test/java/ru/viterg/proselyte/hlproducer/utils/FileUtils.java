package ru.viterg.proselyte.hlproducer.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class FileUtils {

    public static String readFile(String filePath) {
        try {
            return Files.readString(Paths.get(Objects
                    .requireNonNull(FileUtils.class.getClassLoader().getResource(filePath)).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(filePath + " does not exist", e);
        }
    }
}

