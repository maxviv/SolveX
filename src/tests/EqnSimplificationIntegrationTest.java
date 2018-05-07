package tests;

import junit.framework.Assert;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import solver.EquationSimplifier;

import java.io.IOException;

/**
 * This class does integration testing of the assignment application EquationSimplifier.
 * For testing JUnit 4 is used.
 * All the tests parses the json file, builds the equation represented by the json file,
 * tests the pretty print format of the equation
 * tests the expression for x
 * tests the value of x
 * There are positive as well as negative test cases
 */
public class EqnSimplificationIntegrationTest {

    @org.junit.Before
    public void setUp() {
    }

    @org.junit.After
    public void tearDown() {
    }


    @Test
    public void testEqn1() {
        EquationSimplifier equationSimplifier = new EquationSimplifier(".\\src\\tests\\testdata\\eqn1.json");

        try {
            Assert.assertEquals("( 3  + (( x  -  20 ) *  3 )) =  21", equationSimplifier.getEquationInPrettyPrintFormat());
            Assert.assertEquals("x  = ((( 21  -  3 ) /  3 ) +  20 )", equationSimplifier.getExpressionForX());
            Assert.assertEquals(26.0, equationSimplifier.solveForX());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testEqn2() {
        EquationSimplifier equationSimplifier = new EquationSimplifier(".\\src\\tests\\testdata\\eqn2.json");

        try {
            Assert.assertEquals("( 2  - ( x  *  3 )) =  5", equationSimplifier.getEquationInPrettyPrintFormat());
            Assert.assertEquals("x  = ((( 5  -  2 ) /  -1 ) /  3 )", equationSimplifier.getExpressionForX());
            Assert.assertEquals(-1.0, equationSimplifier.solveForX());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            Assert.fail();
        }

    }

    @Test
    public void testEqn3() {
        EquationSimplifier equationSimplifier = new EquationSimplifier(".\\src\\tests\\testdata\\eqn3.json");

        try {
            Assert.assertEquals("( -3  *  x ) =  15", equationSimplifier.getEquationInPrettyPrintFormat());
            Assert.assertEquals("x  = ( 15  /  -3 )", equationSimplifier.getExpressionForX());
            Assert.assertEquals(-5.0, equationSimplifier.solveForX());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            Assert.fail();
        }

    }

    @Test
    public void testEqn4() {
        EquationSimplifier equationSimplifier = new EquationSimplifier(".\\src\\tests\\testdata\\eqn4.json");

        try {
            Assert.assertEquals("( 1  + ( x  *  10 )) =  21", equationSimplifier.getEquationInPrettyPrintFormat());
            Assert.assertEquals("x  = (( 21  -  1 ) /  10 )", equationSimplifier.getExpressionForX());
            Assert.assertEquals(2.0, equationSimplifier.solveForX());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testEqn5() {
        EquationSimplifier equationSimplifier = new EquationSimplifier(".\\src\\tests\\testdata\\eqn5.json");

        try {
            Assert.assertEquals("( x  /  5 ) =  15", equationSimplifier.getEquationInPrettyPrintFormat());
            Assert.assertEquals("x  = ( 15  *  5 )", equationSimplifier.getExpressionForX());
            Assert.assertEquals(75.0, equationSimplifier.solveForX());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testEqn6() {
        EquationSimplifier equationSimplifier = new EquationSimplifier(".\\src\\tests\\testdata\\eqn6.json");

        try {
            Assert.assertEquals("( 2  * ( 3  - ( x  /  5 ))) =  4", equationSimplifier.getEquationInPrettyPrintFormat());
            Assert.assertEquals("x  = (((( 4  /  2 ) -  3 ) /  -1 ) *  5 )", equationSimplifier.getExpressionForX());
            Assert.assertEquals(5.0, equationSimplifier.solveForX());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testComplexEqn() {
        EquationSimplifier equationSimplifier = new EquationSimplifier(".\\src\\tests\\testdata\\eqnComplexX.json");
        try {
            Assert.assertEquals("( 5  + (( 4  * (( 3  * ( x  -  4 )) +  4 )) +  7 )) =  50", equationSimplifier.getEquationInPrettyPrintFormat());
            Assert.assertEquals("x  = (((((( 50  -  5 ) -  7 ) /  4 ) -  4 ) /  3 ) +  4 )", equationSimplifier.getExpressionForX());
            Assert.assertEquals(5.833333333333333, equationSimplifier.solveForX());
        } catch (IOException | ParseException e) {
            Assert.fail();
        }

    }

    @Test
    public void testSimpleEqn() {
        EquationSimplifier equationSimplifier = new EquationSimplifier(".\\src\\tests\\testdata\\eqnSimpleX.json");
        try {
            Assert.assertEquals("x  =  21", equationSimplifier.getEquationInPrettyPrintFormat());
            Assert.assertEquals("x  =  21", equationSimplifier.getExpressionForX());
            Assert.assertEquals(21.0, equationSimplifier.solveForX());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testEqnInvalidHavingUnsupportedOperator() {
        boolean failed = false;
        EquationSimplifier equationSimplifier = new EquationSimplifier(".\\src\\tests\\testdata\\invalidOperatorEqn.json");
        try {
            Assert.assertEquals("1", equationSimplifier.getEquationInPrettyPrintFormat());

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            failed = true;
        }
        Assert.assertEquals("Parsing should Fail", true, failed);
    }

    @Test
    public void testEqnInvalidHavingUnsupportedVariable() {
        boolean failed = false;
        EquationSimplifier equationSimplifier = new EquationSimplifier(".\\src\\tests\\testdata\\invalidVariableEqn.json");
        try {
            Assert.assertEquals("1", equationSimplifier.getEquationInPrettyPrintFormat());

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            failed = true;
        }
        Assert.assertEquals("Parsing should Fail", true, failed);
    }

    @Test
    public void testEqnInvalidHavingNonConstantRHS() {
        boolean failed = false;
        EquationSimplifier equationSimplifier = new EquationSimplifier(".\\src\\tests\\testdata\\invalidRHSEqn.json");
        try {
            Assert.assertEquals("1", equationSimplifier.getEquationInPrettyPrintFormat());

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            failed = true;
        }
        Assert.assertEquals("Parsing should Fail", true, failed);
    }
}