package api.library.book;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    List<Book> getAllBooks();

    Optional<Book> findById(Long id);

    Book add(Book book);

    Book update(Book book);

    void delete(Long id);
}
