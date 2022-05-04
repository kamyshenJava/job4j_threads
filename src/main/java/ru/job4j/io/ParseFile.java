package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private String getContentWithFilter(Predicate<Integer> filter) throws IOException {
        String output = "";
        try (InputStream i = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(i)) {
            int data;
            while ((data = bis.read()) > 0) {
                if (filter.test(data)) {
                    output += (char) data;
                }
            }
        }
        return output;
    }

    public String getContent() throws IOException {
        return getContentWithFilter(x -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContentWithFilter(x -> x < 0x80);
    }
}
