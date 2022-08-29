package by.rudenko.library.dao;

import by.rudenko.library.models.Book;
import by.rudenko.library.models.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookDAO {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public BookDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Book> index() {
    String SQL = "SELECT * FROM book";
    return jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Book.class));
  }

  public Book show(int id){
    String SQL = "SELECT * FROM book WHERE id = ?";
    return jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
        .stream().findAny().orElse(null);
  }

  public void save(Book book){
    String SQL = "INSERT INTO book(title, author, year) VALUES(?, ?, ?)";
    jdbcTemplate.update(SQL, book.getTitle(), book.getAuthor(), book.getYear());
  }
  public void update(int id, Book book){
    String SQL = "UPDATE book SET title=?, author=?, year=? WHERE id=?";
    jdbcTemplate.update(SQL, book.getTitle(), book.getAuthor(), book.getYear(), id);
  }
  public void delete(int id){
    String SQL = "DELETE FROM book WHERE id = ?";
    jdbcTemplate.update(SQL, id);
  }
  public void assign(int id, Person person){
    String SQL = "UPDATE Book SET person_id=? WHERE id=?";
    jdbcTemplate.update(SQL, person.getId(), id);
  }
  public void release(int id){
    String SQL = "UPDATE Book SET person_id=NULL WHERE id=?";
    jdbcTemplate.update(SQL, id);
  }
  public Optional<Person> getOwner(int id){
    String SQL = "SELECT person.* FROM book JOIN person ON book.person_id = person.id WHERE book.id=?";
    return jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
        .stream().findAny();
  }
  public Optional<Book> showByTitleAndAuthor(String title, String author) {
    return jdbcTemplate.query("Select * FROM book WHERE title=? AND author=?", new Object[]{title, author},
        new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
  }

}
