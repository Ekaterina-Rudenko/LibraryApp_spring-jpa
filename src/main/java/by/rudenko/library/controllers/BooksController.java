package by.rudenko.library.controllers;

import by.rudenko.library.dao.BookDAO;
import by.rudenko.library.dao.PersonDAO;
import by.rudenko.library.models.Book;
import by.rudenko.library.models.Person;
import by.rudenko.library.util.BookValidator;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BooksController {

  private final BookDAO bookDAO;
  private final PersonDAO personDAO;
  private final BookValidator bookValidator;

  @Autowired
  public BooksController(BookDAO bookDAO, PersonDAO personDAO,
      BookValidator bookValidator) {
    this.bookDAO = bookDAO;
    this.personDAO = personDAO;
    this.bookValidator = bookValidator;
  }

  @GetMapping
  public String index(Model model){
    model.addAttribute("books", bookDAO.index());
    return "/books/index";
  }
  @GetMapping("/{id}")
  public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person ){
    model.addAttribute("book", bookDAO.show(id));
    Optional<Person> owner = bookDAO.getOwner(id);
    if(owner.isPresent()){
      model.addAttribute("owner", owner.get());
    } else {
      model.addAttribute("people", personDAO.index());
    }
    return "/books/show";
  }
  @GetMapping("/new")
  public String newBook(@ModelAttribute("book")Book book){
    return "/books/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
    bookValidator.validate(book, bindingResult);
    if(bindingResult.hasErrors()){
      return "books/new";
    }
    bookDAO.save(book);
    return "redirect:/books";
  }
  @GetMapping("/{id}/edit")
  public String edit(Model model, @PathVariable("id") int id) {
    model.addAttribute("book",bookDAO.show(id));
    return "books/edit";
  }
  @PatchMapping("/{id}")
  public String update(@ModelAttribute("book") @Valid Book book,BindingResult bindingResult,
      @PathVariable("id") int id){
    bookValidator.validate(book, bindingResult);
    if(bindingResult.hasErrors()){
      return "/books/edit";
    }
    bookDAO.update(id, book);
    return "redirect:/books";
  }

  @PatchMapping("/{id}/assign")
  public String assign(@PathVariable("id")int id, @ModelAttribute("person") Person person){
    bookDAO.assign(id, person);
    return "redirect:/books/" + id;
  }
  @PatchMapping("/{id}/release")
  public String release(@PathVariable("id")int id){
    bookDAO.release(id);
    return "redirect:/books/" + id;
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    bookDAO.delete(id);
    return "redirect:/books";
  }
}
