package by.rudenko.library.repositories;

import by.rudenko.library.models.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

  List<Person> findByFullName(String fullName);
}
