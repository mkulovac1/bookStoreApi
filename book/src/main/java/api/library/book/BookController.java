package api.library.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("book/{id}")
    public Optional<Book> getBookById(@PathVariable("id") Long id) {
        return bookService.findById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> add(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.add(book), HttpStatus.CREATED);
    }

    @PostMapping("/addBooks")
    public ResponseEntity<List<Book>> addBooks(@RequestBody List<Book> books) {
        return new ResponseEntity<>(bookService.addBooks(books), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> update(@PathVariable("id") Long id, @RequestBody Book book) {
        return new ResponseEntity<>(bookService.update(id, book), HttpStatus.OK);
    }

    @DeleteMapping("/book/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        bookService.delete(id);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Book>> getSortedBooks(@RequestParam(name = "criteria") String criteria,
                                                     @RequestParam(name = "order") String order,
                                                     @RequestParam(name = "page", defaultValue = "0") int pageNumber,
                                                     @RequestParam(name = "size", defaultValue = "10") int pageSize) {
        /* List<Book> sortedBooks = bookService.getSortedBooks(criteria, order);
        if(sortedBooks == null) {
            return ResponseEntity.badRequest().build();
        }*/
        List<Book> b = bookService.getSortedBooks(criteria, order, pageNumber, pageSize);

        return ResponseEntity.ok(bookService.getSortedBooks(criteria, order, pageNumber, pageSize));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Book>> getFilteredBooks(@RequestParam(name = "title", required = false) String title,
                                                       @RequestParam(name = "author", required = false) String author,
                                                       @RequestParam(name = "genre", required = false) String genre) {

        return ResponseEntity.ok(bookService.getFilteredBooks(title, author, genre));
    }
}
