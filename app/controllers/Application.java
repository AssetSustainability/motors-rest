package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.w3c.dom.Document;
import play.*;
import play.api.libs.json.JacksonJson;
import play.libs.F;
import play.libs.WS;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render(apiCall().getBody().toString()));
    }

    public static WS.Response apiCall() {
        ObjectNode json = JsonNodeFactory.instance.objectNode();
        json.put("This will eventually","DO SOMETHING");

        WS.Response response =  WS.url("http://54.225.109.199/api/1.0/rest/motor/external/measurement")
                          .setAuth("support@motorsatwork.com","MOTORS")
                          .put(json)
                          .get(60 * 1000);
        return response;
    }

}
