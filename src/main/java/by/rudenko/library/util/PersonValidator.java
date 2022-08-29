package by.rudenko.library.util;

import by.rudenko.library.dao.PersonDAO;
import by.rudenko.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

  private final PersonDAO personDao;

  @Autowired
  public PersonValidator(PersonDAO personDao) {
    this.personDao = personDao;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return Person.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    Person person = (Person) target;
    if (personDao.showByFullName(person.getFullName()).isPresent()) {
      errors.rejectValue("fullName", "", "This full name already exists.");
    }
  }
}
