package solver;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * This is the entry point of the application.
 * This class exposes high level API for reading the equation from json file,
 * Reading the equation is a parse tree,
 * Getting printable format for the equation,
 * Transforming the parse tree to have X on one side,
 * Finding the value of x
 */
public class EquationSimplifier {
    //The json file path
    private String jsonPath;
    //Parser for json files
    private JsonParserHelper parser;
    //Object model representation of the equation
    private ParseTree parseTreeOfEquation = null;
    //Object Model representation of the expression for x
    private ParseTree parseTreeForX = null;

    public EquationSimplifier(String jsonPath) {
        this.jsonPath = jsonPath;
        parser = new JsonParserHelper();
    }

    /**
     * Parses the Json file into a binary tree object model
     *
     * @throws IOException    -  if the file is not parseable or is not valid
     * @throws ParseException - if the file is not parseable
     */
    private void buildEquation() throws IOException, ParseException {
        JSONObject jsonObject = parser.parseJsonFile(jsonPath);
        parseTreeOfEquation = parser.buildParseTreeFromJsonObjectModel(jsonObject);
        if (!parseTreeOfEquation.isValid()) {
            throw new IOException("Equation is not well formed.");
        }
    }

    /**
     * function to pretty-print the parsed
     * equation , in a single line with brackets
     *
     * @return - pretty print string format of the eqn
     * @throws IOException    - thrown when eqn is not well formed
     * @throws ParseException - thrown when eqn is not well formed
     */
    public String getEquationInPrettyPrintFormat() throws IOException, ParseException {
        initParseTreeForEquation();
        return parseTreeOfEquation.getPrettyPrintEquationFormat().trim();
    }

    private void initParseTreeForEquation() throws IOException, ParseException {
        if (parseTreeOfEquation == null) {
            buildEquation();
        }
    }

    /**
     * @return expression so that you have ‘x’ on one side , and all the operations on the
     * other side
     * @throws IOException    - thrown when eqn is not well formed
     * @throws ParseException - thrown when eqn is not well formed
     */
    public String getExpressionForX() throws IOException, ParseException {
        initParseTreeForX();
        assert parseTreeForX != null;
        return parseTreeForX.getPrettyPrintEquationFormat().trim();
    }

    private void initParseTreeForX() throws IOException, ParseException {
        if (parseTreeForX == null) {
            initParseTreeForEquation();
            parseTreeForX = new Transformer().solveX(parseTreeOfEquation);
        }
    }

    /**
     * @return Evaluate the expression on the other side and find the value of ‘x’.
     * @throws IOException    - thrown when eqn is not well formed
     * @throws ParseException - - thrown when eqn is not well formed
     */
    public double solveForX() throws IOException, ParseException {
        initParseTreeForX();
        return parseTreeForX.evaluateValX();
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Incorrect Usage.Please give the path of the json file");
        }
        String jsonFilePath = args[0];
        EquationSimplifier equationSimplifier = new EquationSimplifier(jsonFilePath);
        String equationInPrettyPrintFormat;
        try {
            equationInPrettyPrintFormat = equationSimplifier.getEquationInPrettyPrintFormat();
            System.out.println("Equation is : " + equationInPrettyPrintFormat);
            System.out.println("Expression for x is :" + equationSimplifier.getExpressionForX());
            System.out.println("x = " + equationSimplifier.solveForX());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

}
