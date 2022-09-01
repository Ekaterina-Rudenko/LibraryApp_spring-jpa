package by.rudenko.library.services;

import by.rudenko.library.models.Book;
import by.rudenko.library.models.Person;
import by.rudenko.library.repositories.BooksRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BookService {

  private final BooksRepository booksRepository;

  @Autowired
  public BookService(BooksRepository booksRepository) {
    this.booksRepository = booksRepository;
  }

  public List<Book> index(boolean sortByYear) {
    if(sortByYear) {
      return booksRepository.findAll(Sort.by("year"));
    } else {
      return booksRepository.findAll();
    }
  }

  public List<Book> indexWithPagination(int pageNumber, int itemsPerPage, boolean sortByYear) {
    if(sortByYear){
      return booksRepository.findAll(PageRequest.of(pageNumber, itemsPerPage, Sort.by("year"))).getContent();
    } else {
      return booksRepository.findAll(PageRequest.of(pageNumber, itemsPerPage)).getContent();
    }
  }

  public Optional<Book> searchByPartTitle(String partTitle) {
    return booksRepository.findByTitleStartingWith(partTitle).stream().findFirst();
  }


  public Book findById(int id) {
    return booksRepository.findById(id).stream().findAny().orElse(null);
  }

  @Transactional
  public void save(Book book) {
    booksRepository.save(book);
  }

  @Transactional
  public void update(int id, Book updatedBook) {
    Book bookToBeUpdated = booksRepository.findById(id).get();
    updatedBook.setId(id);
    updatedBook.setOwner(bookToBeUpdated.getOwner());
    booksRepository.save(updatedBook);
  }

  @Transactional
  public void delete(int id) {
    booksRepository.deleteById(id);
  }

  @Transactional
  public void assign(int id, Person person) {
    booksRepository.findById(id).ifPresent(
        book -> {
          book.setOwner(person);
          book.setDate(LocalDate.now());
        }
    );
  }

  @Transactional
  public void release(int id) {
    booksRepository.findById(id).ifPresent(
        book -> {
          book.setOwner(null);
          book.setDate(null);
        }
    );
  }

  public Person getOwner(int id) {
    return booksRepository.findById(id).map(Book::getOwner).orElse(null);
  }

  public Optional<Book> findByTitleAndAuthor(String title, String author) {
    return booksRepository.findBookByTitleAndAuthor(title, author).stream().findAny();
  }

}
