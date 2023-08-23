package api.library.book;

import api.library.exception.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

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

    public List<Book> getSortedBooks(String sortingCriteria, String sortingOrder, int pageNumber, int pageSize) {
        // List<Book> sortedBooks = null;

        Pageable pageable;

        if (sortingCriteria.equals("title") && sortingOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "title"));
        } else if (sortingCriteria.equals("title") && sortingOrder.equals("desc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "title"));
        } else if (sortingCriteria.equals("author") && sortingOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "author"));
        } else if (sortingCriteria.equals("author") && sortingOrder.equals("desc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "author"));
        } else if (sortingCriteria.equals("genre") && sortingOrder.equals("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "genre"));
        } else if (sortingCriteria.equals("genre") && sortingOrder.equals("desc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "genre"));
        } else {
            throw new IllegalArgumentException("Invalid sorting criteria or sorting order.");
        }

        Page<Book> bookPage = bookRepository.findAll(pageable);

        // sortedBooks = bookPage.getContent();

        return bookPage.getContent();

        // return sortedBooks;
    }


    public List<Book> getFilteredBooks(String titleFilter, String authorFilter, String genreFilter) {
            Specification<Book> spec = (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                // Predicates are condition which are applied to the query in order to filter the results.
                // It is used for combined filtering.

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
