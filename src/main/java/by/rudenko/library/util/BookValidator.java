package by.rudenko.library.util;

import by.rudenko.library.dao.BookDAO;
import by.rudenko.library.models.Book;
import by.rudenko.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

  private final BookService bookService;

  @Autowired
  public BookValidator(BookService bookService) {
    this.bookService = bookService;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return Book.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    Book book = (Book) target;
    if(bookService.findByTitleAndAuthor(book.getTitle(), book.getAuthor()).isPresent()){
      errors.rejectValue("title", "", "Book with such name and author already exists");
    }
  }
}
