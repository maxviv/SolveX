package solver;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

/**
 * This class encapsulates the parsing mechanism of the json file representing the equation
 * Right now it use the library json simple parser(defined in json-simple-1.1.1.jar). In future it may use
 * a different json parser but the client code will not change
 */
public class JsonParserHelper {

    private static JSONParser parser = new JSONParser();

    JSONObject parseJsonFile(String path) throws ParseException, IOException {
        FileReader fileReader = new FileReader(path);
        return (JSONObject) parser.parse(fileReader);
    }

    ParseTree buildParseTreeFromJsonObjectModel(JSONObject jsonOM) {
        ParseTree parseTree = new ParseTree(null);
        parseTree.buildParseTree(jsonOM);
        return parseTree;
    }
}
