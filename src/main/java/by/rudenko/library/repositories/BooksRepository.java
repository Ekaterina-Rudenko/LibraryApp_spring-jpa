package by.rudenko.library.repositories;

import by.rudenko.library.models.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor.OptimalPropertyAccessor;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

  List<Book> findByOwnerId(int id);

  List<Book> findBookByTitleAndAuthor(String title, String author);

  List<Book> findByTitleStartsWith(String partTitle);
}
