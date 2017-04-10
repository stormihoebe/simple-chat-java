import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Message {
  public int id;
  public String messageText;
  public int person_id;

  public Message(String messageText, int person_id) {
    this.messageText = messageText;
    this.person_id = person_id;

  }

  public int getPersonId(){
    return person_id;
  }

  public String getMessageText() {
    return messageText;
  }

  public int getId(){
    return id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO messages (messageText, person_id) VALUES (:messageText, :person_id)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("messageText", this.messageText)
      .addParameter("person_id", this.person_id)
      .executeUpdate()
      .getKey();
    }
  }

  @Override
  public boolean equals(Object otherMessage){
    if(!(otherMessage instanceof Message)){
      return false;
    } else {
      Message newMessage = (Message) otherMessage;
      return this.getMessageText().equals(newMessage.getMessageText()) && this.getId() == newMessage.getId();
    }
  }

}
