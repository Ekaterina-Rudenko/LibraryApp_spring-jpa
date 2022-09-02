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
public class PersonDAO {

/*  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public PersonDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Person> index() {
    String SQL = "SELECT * FROM person";
    return jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Person.class));
  }

  public Optional<Person> showByFullName(String fullName) {
    return jdbcTemplate.query("Select * FROM person WHERE full_name=?", new Object[]{fullName},
        new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
  }

  public Person show(int id) {
    String SQL = "SELECT * FROM person WHERE id=?";
    return jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
        .stream().findAny().orElse(null);
  }

  public void save(Person person) {
    String SQL = "INSERT INTO person (full_name, year_of_birth) VALUES ( ?, ?)";
    jdbcTemplate.update(SQL, person.getFullName(), person.getYearOfBirth());
  }

  public void update(int id, Person updatedPerson) {
    String SQL = "UPDATE person SET full_name =?, year_of_birth=? WHERE id=?";
    jdbcTemplate.update(SQL, updatedPerson.getFullName(), updatedPerson.getYearOfBirth(), id);
  }

  public void delete(int id) {
    String SQL = "DELETE FROM person WHERE id=?";
    jdbcTemplate.update(SQL, id);
  }
  public List<Book> getBooksByPersonId(int id){
    String SQL = "SELECT * FROM Book WHERE person_id = ?";
   return jdbcTemplate.query(SQL,  new Object[]{id},
        new BeanPropertyRowMapper<>(Book.class));
  }*/
}
