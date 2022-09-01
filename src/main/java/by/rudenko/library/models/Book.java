package by.rudenko.library.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotEmpty(message = "Title shouldn't be empty")
  @Size(min = 2, max = 100, message = "Title should have size from 2 to 100 characters")
  @Column(name = "title")
  private String title;

  @NotEmpty(message = "Author shouldn't be empty")
  @Size(min = 2, max = 100, message = "Author should have size from 2 to 100 characters")
  @Column(name = "author")
  private String author;

  @Column(name = "year")
  private int year;

  @ManyToOne
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Person owner;

  @Column(name = "date")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDate date;

  @Transient
  private boolean isExpired;

  public Book(int id, String title, String author, int year) {
    this.title = title;
    this.author = author;
    this.year = year;
  }

  public Book() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public Person getOwner() {
    return owner;
  }

  public void setOwner(Person owner) {
    this.owner = owner;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public boolean isExpired() {
    /*LocalDate deadline = this.date.plusDays(10);
    return (LocalDate.now().compareTo(deadline) <= 0);*/
    long difference = ChronoUnit.DAYS.between(LocalDate.now(), this.date);
    return difference <= 10;
  }

  public void setExpired(boolean expired) {
    isExpired = expired;
  }
}
