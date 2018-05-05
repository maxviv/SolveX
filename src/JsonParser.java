import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonParser {


    public static void main(String[] args) throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(".\\eqn1.json"));
        ParseTree parseTree = new ParseTree(null);
        parseTree.buildParseTree((JSONObject) obj);
        parseTree.prettyPrintEquation();
        new Transformer().solveX(parseTree);
    }

}
