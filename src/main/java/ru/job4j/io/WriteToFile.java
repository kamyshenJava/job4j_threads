package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class WriteToFile {
    private final File file;

    public WriteToFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        Files.writeString(file.toPath(), content);
    }
}
