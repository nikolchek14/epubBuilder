import book.SimpleBook;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleBook book = mapper.readValue(new File("src/main/resources/zaboraveno.json"), SimpleBook.class);
        System.out.println(book);
    }
}