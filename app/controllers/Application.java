package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.w3c.dom.Document;
import play.*;
import play.api.libs.json.JacksonJson;
import play.data.format.Formats;
import play.libs.F;
import play.libs.WS;
import play.mvc.*;

import views.html.*;

import java.util.Date;
import java.util.Locale;

public class Application extends Controller {

    public static Result index() {
//        return ok(index.render(apiTester().getBody().toString()));
        return ok(index.render(addMotorMeasurementTester().getBody().toString()));
    }

    public static WS.Response apiCall(String url, JsonNode json) {
        WS.Response response =  WS.url(url)
                          .setAuth("support@motorsatwork.com","MOTORS")
                          .put(json)
                          .get(60 * 1000);
        return response;
    }

    public static WS.Response apiTester() {
        ObjectNode json = JsonNodeFactory.instance.objectNode();
        Date date = new Date();

        json.put("Time is now", (new Formats.DateFormatter("MM/dd/yyyy hh:mm:ss")).print(date, Locale.US));

        return apiCall("http://54.225.109.199/api/1.0/rest/test/put",json);
    }

    public static WS.Response addMotorMeasurementTester() {
        ObjectNode json = JsonNodeFactory.instance.objectNode();
        ArrayNode measurementArrayNode = JsonNodeFactory.instance.arrayNode();
        ObjectNode measurementNode = JsonNodeFactory.instance.objectNode();
        Date date = new Date();

        measurementNode.put("referenceNumber","1000000");
        measurementNode.put("voltageAB","220");
        measurementNode.put("voltageBC","240");
        measurementNode.put("voltageCA","260");
        measurementArrayNode.add(measurementNode);
        json.put("data",measurementArrayNode);

        return apiCall("http://54.225.109.199/api/1.0/rest/motor/external/measurement",json);
    }

}
