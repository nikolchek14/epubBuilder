import book.SimpleBook;
import book.SimpleHead;
import com.fasterxml.jackson.databind.ObjectMapper;
import j2html.tags.ContainerTag;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;

import java.io.*;

import static j2html.TagCreator.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleBook src = mapper.readValue(new File("src/main/resources/zaboraveno.json"), SimpleBook.class);

        Book book = new Book();
        book.getMetadata().addTitle("Zaboraveno Lekarstvo");
        book.getMetadata().addAuthor(new Author("Nikolche", "K"));

        for (SimpleHead head: src.getHeads()) {
            ContainerTag body = body(h1(head.getGlava()));
            for (String s: head.getParagrafi()) {
                body.with(p(s));
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(body.render().getBytes());
            InputStream in = new ByteArrayInputStream(baos.toByteArray());

            Resource res = new Resource(in, src.getHeads().indexOf(head) + ".html");
            book.addSection(head.getGlava(), res);
        }
        //book.getMetadata().setCoverImage(new Resource(Simple1.class.getResourceAsStream("/book1/test_cover.png"), "cover.png"));
        //book.addSection("Introduction", new Resource(Simple1.class.getResourceAsStream("/book1/chapter1.html"), "chapter1.html"));
        //book.getResources().add(new Resource(Simple1.class.getResourceAsStream("/book1/book1.css"), "book1.css"));

        /*TOCReference chapter2 = book.addSection("Second Chapter", new Resource(Simple1.class.getResourceAsStream("/book1/chapter2.html"), "chapter2.html"));
        book.getResources().add(new Resource(Simple1.class.getResourceAsStream("/book1/flowers_320x240.jpg"), "flowers.jpg"));
        book.addSection(chapter2, "Chapter 2, section 1", new Resource(Simple1.class.getResourceAsStream("/book1/chapter2_1.html"), "chapter2_1.html"));
        book.addSection("Conclusion", new Resource(Simple1.class.getResourceAsStream("/book1/chapter3.html"), "chapter3.html"));*/

        EpubWriter epubWriter = new EpubWriter();
        epubWriter.write(book, new FileOutputStream("zaboraveno_lekarstvo.epub"));
    }
}