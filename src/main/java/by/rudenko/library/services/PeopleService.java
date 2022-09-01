package by.rudenko.library.services;

import by.rudenko.library.models.Book;
import by.rudenko.library.models.Person;
import by.rudenko.library.repositories.BooksRepository;
import by.rudenko.library.repositories.PeopleRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

  private final PeopleRepository peopleRepository;
  private final BooksRepository booksRepository;

  @Autowired
  public PeopleService(PeopleRepository peopleRepository,
      BooksRepository booksRepository) {
    this.peopleRepository = peopleRepository;
    this.booksRepository = booksRepository;
  }

  public List<Person> index() {
    return peopleRepository.findAll();
  }

  public Person findById(int id) {
    Optional<Person> foundedPerson = peopleRepository.findById(id);
    return foundedPerson.orElse(null);
  }

  public Optional<Person> findByFullName(String fullName) {
    List<Person> foundedPeople = peopleRepository.findByFullName(fullName);
    return foundedPeople.stream().findAny();
  }

  @Transactional
  public void save(Person person) {
    peopleRepository.save(person);
  }

  @Transactional
  public void update(int id, Person updatedPerson) {
    updatedPerson.setId(id);
    peopleRepository.save(updatedPerson);
  }

  @Transactional
  public void delete(int id) {
    peopleRepository.deleteById(id);
  }

  public List<Book> getBooksByPersonId(int id) {
    Optional<Person> person = peopleRepository.findById(id);
    if (person.isPresent()) {
      Hibernate.initialize(person.get().getBooks());

      person.get().getBooks().forEach(book -> {
        long difference = ChronoUnit.DAYS.between(LocalDate.now(), book.getDate());
        if (difference > 10) {
          book.setExpired(true);
        }
      });
      return person.get().getBooks();
    } else {
      return Collections.emptyList();
    }
  }

}
