package by.rudenko.library.repositories;

import by.rudenko.library.models.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

  List<Book> findBookByTitleAndAuthor(String title, String author);

  List<Book> findByTitleStartingWith(String title);
}
