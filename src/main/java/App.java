import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    // Use this setting only for development
    externalStaticFileLocation(String.format("%s/src/main/resources/public", System.getProperty("user.dir")));

    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/persons/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name1");
      Person person1 = new Person(name);
      person1.save();
      String name2 = request.queryParams("name2");
      Person person2 = new Person(name2);
      person2.save();
      model.put("persons", Person.all());
      String url = String.format("/persons");
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/persons", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("persons", Person.all());
      model.put("template", "templates/persons.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/persons/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int person_id = Integer.parseInt(request.params(":id"));
      model.put("person", Person.find(person_id));
      model.put("allPersons", Person.all());
      model.put("template", "templates/person.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/message", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String messageText= request.queryParams("messageText");
      int personId= Integer.parseInt(request.queryParams("person_id"));
      Message message = new Message(messageText, personId);
      message.save();
      String url = String.format("/persons/" + personId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
