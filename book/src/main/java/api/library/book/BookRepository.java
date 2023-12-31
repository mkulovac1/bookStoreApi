package api.library.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> { // beacuse of filtering its second interface
    List<Book> findAllByOrderByTitleAsc();

    List<Book> findAllByOrderByTitleDesc();

    List<Book> findAllByOrderByAuthorAsc();

    List<Book> findAllByOrderByAuthorDesc();

    List<Book> findAllByOrderByGenreAsc();

    List<Book> findAllByOrderByGenreDesc();
}
