package com.epam.fileparser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {

        final HashSet<Book> s = new HashSet();
        final String f = args[0];
        final String ext = f.split("\\.")[1];
//        if (ext == "json")
//        {
//            final String s = IOUtils.toString(new FileInputStream(f)));
//            final ObjectMapper m = new ObjectMapper();
//        }
        if (ext == "json") {
            s.addAll(readJson(new FileInputStream(f)));
        } else if (ext == "csv") {
            s.addAll(Arrays.asList(readCSV(new FileInputStream(f))));
        } else {
            System.out.println("Unknown file extension " + ext);
        }

        for (final Book book : s)
        {
            print(book.title, book.author, book.year, book.pages);
        }
    }

    /**
     * Reads incoming CSV to the Books data structure.
     *
     * @param stream
     * @return
     */
    public static Book[] readCSV(final InputStream stream) {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            final HashMap<String, Book> map = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                Book book = new Book();
                book.setTitle(row[0]);
                book.setAuthor(row[1]);
                book.setYear(Integer.parseInt(row[2]));
                book.setPages(Integer.parseInt(row[3]));
                map.put(book.getTitle(), book);
            }
            return map.values().toArray(Book[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Book> readJson(final InputStream stream) throws IOException {
        final String s = IOUtils.toString(stream);
        final ObjectMapper m = new ObjectMapper();
        return m.readValue(s, new TypeReference<List<Book>>(){});
    }

    private static void print(String title, String author, int year, int pages) {
        System.out.println(String.format("Book { title: %s, author: %s, year: %d, pages: %d }", title, author, year, pages));
    }

}