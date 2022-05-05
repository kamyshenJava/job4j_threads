package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private String getContentWithFilter(Predicate<Integer> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(i)) {
            int data;
            while ((data = bis.read()) != -1) {
                if (filter.test(data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }

    public String getContent() throws IOException {
        return getContentWithFilter(x -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContentWithFilter(x -> x < 0x80);
    }
}
