package api.library.book;

import api.library.exception.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.criteria.Predicate;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found.")));
    }

    @Override
    public Book add(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        Optional<Book> findBook = bookRepository.findById(id);
        if(findBook.isPresent())
            bookRepository.deleteById(id);
    }

    public List<Book> addBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    public List<Book> getSortedBooks(String sortingCriteria, String sortingOrder) {
        if(sortingCriteria.equals("title") && sortingOrder.equals("asc")) {
            return bookRepository.findAllByOrderByTitleAsc();
        } else if(sortingCriteria.equals("title") && sortingOrder.equals("desc")) {
            return bookRepository.findAllByOrderByTitleDesc();
        } else if(sortingCriteria.equals("author") && sortingOrder.equals("asc")) {
            return bookRepository.findAllByOrderByAuthorAsc();
        } else if(sortingCriteria.equals("author") && sortingOrder.equals("desc")) {
            return bookRepository.findAllByOrderByAuthorDesc();
        } else if(sortingCriteria.equals("genre") && sortingOrder.equals("asc")) {
            return bookRepository.findAllByOrderByGenreAsc();
        } else if(sortingCriteria.equals("genre") && sortingOrder.equals("desc")) {
            return bookRepository.findAllByOrderByGenreDesc();
        } else {
            // throw new IllegalArgumentException("Invalid sorting criteria or sorting order.");
            return null;
        }
    }

    public List<Book> getFilteredBooks(String titleFilter, String authorFilter, String genreFilter) {
            Specification<Book> spec = (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                // Predikati su uslovi koji se primenjuju na upit kako bi se filtrirali rezultati.
                // Koristi se za kombinovano filtriranje

                if (titleFilter != null && !titleFilter.isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("title")), "%" + titleFilter.toLowerCase() + "%"));
                }

                if (authorFilter != null && !authorFilter.isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("author")), "%" + authorFilter.toLowerCase() + "%"));
                }

                if (genreFilter != null && !genreFilter.isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("genre")), "%" + genreFilter.toLowerCase() + "%"));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };

            return bookRepository.findAll(spec);
    }
}
