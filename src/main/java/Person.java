import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Person {
  public int id;
  public String name;

  public Person(String name) {
    this.name = name;

  }
  public String getName(){
    return name;
  }

  public int getId(){
    return id;
  }

  public List<Message> allMessages() {
    String sql = "SELECT * FROM messages WHERE person_id=:id";
    try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql)
    .addParameter("id", this.id)
    .executeAndFetch(Message.class);
    }
  }

  public static List<Person> all() {
    String sql = "SELECT * FROM persons";
    try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql)
    .executeAndFetch(Person.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO persons (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
    }
  }

  public static Person find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM persons WHERE id=:id;";
      Person ranger = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Person.class);
      return ranger;
    } catch (IndexOutOfBoundsException exception) {
      return null;
    }
  }

  @Override
  public boolean equals(Object otherPerson){
    if(!(otherPerson instanceof Person)){
      return false;
    } else {
      Person newPerson = (Person) otherPerson;
      return this.getName().equals(newPerson.getName()) && this.getId() == newPerson.getId();
    }
  }
}
