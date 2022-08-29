package by.rudenko.library.services;

import by.rudenko.library.models.Book;
import by.rudenko.library.models.Person;
import by.rudenko.library.repositories.BooksRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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

  public List<Book> index(){
    return booksRepository.findAll();
  }
  public Book findById(int id){
    return booksRepository.findById(id).stream().findAny().orElse(null);
  }
  @Transactional
  public void save(Book book){
    booksRepository.save(book);
  }

  @Transactional
  public void update(int id, Book updatedBook){
    updatedBook.setId(id);
    booksRepository.save(updatedBook);
  }

  @Transactional
  public void delete(int id){
    booksRepository.deleteById(id);
  }

  @Transactional
  public void assign(int id, Person person){
    Book bookToAssign = booksRepository.findById(id).get();
    bookToAssign.setOwner(person);
    booksRepository.save(bookToAssign);
  }

  @Transactional
  public void release(int id){
    Book bookToRelease = booksRepository.findById(id).get();
    bookToRelease.setOwner(null);
  }

  public Optional<Person> getOwner(int id){
    Book book = booksRepository.findById(id).get();
    return Optional.of(book.getOwner());
  }

  public Optional<Book> findByTitleAndAuthor(String title, String author){
    return booksRepository.findBookByTitleAndAuthor(title, author).stream().findAny();
  }

}
