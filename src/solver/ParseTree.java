package solver;

import org.json.simple.JSONObject;

/**
 * This class is used for the internal representation of the equation,
 * The underlying object model is a binary tree whose root will be an operation
 * and the left and right subtree will be operation or constants or variables
 * For eg:1 + (x * 10) = 21 will be represented as
 *
 *                  =
 *              /      \
 *             +         21
 *           /   \
 *         1      *
 *              /   \
 *             x     10
 */
public class ParseTree {

    private ParseTreeNode root;

    ParseTree(ParseTreeNode root) {
        this.root = root;
    }

    public void buildParseTree(JSONObject obj) {
        root = buildParseTree(root, obj);
    }

    /**
     * @return the equation in a pretty printable format.
     * This is achieved by traversing the parse tree in an
     * inorder format and printing the braces as necessary
     */
    public String getPrettyPrintEquationFormat() {
        StringBuilder eqnRepresentation = new StringBuilder();
        printInorder(root, eqnRepresentation);
        return eqnRepresentation.toString();
    }

    /**
     * @param rootNode - the parse tree node
     * @param eqnRepresentation -  represents the equation
     */
    private void printInorder(ParseTreeNode rootNode, StringBuilder eqnRepresentation) {
        if (rootNode != null) {
            boolean shouldPrintBraces = shouldPrintBraces(rootNode);
            if (shouldPrintBraces) {
                eqnRepresentation.append("(");
            }
            printInorder(rootNode.left, eqnRepresentation);
            eqnRepresentation.append(" ").append(rootNode.data.display()).append(" ");
            printInorder(rootNode.right, eqnRepresentation);
            if (shouldPrintBraces) {
                eqnRepresentation.append(")");
            }
        }
    }

    /*
     * @param rootNode - the node
     * @return true if we are required to print braces. Braces are printed if the expression contains more
     * than 1 tokens
     */
    private boolean shouldPrintBraces(ParseTreeNode rootNode) {
        return countNodes(rootNode) > 1 && rootNode.getToken().getType() != TokenType.OPERATOR_EQUAL;
    }

    /**
     *
     * @param rootNode the node
     * @param obj - the json obj
     * @return the parse tree by parsing the json object representing the equation
     */
    private ParseTreeNode buildParseTree(ParseTreeNode rootNode, JSONObject obj) {

        Object op = obj.get("op");
        if (op != null) {
            rootNode = new ParseTreeNode((String) op);
        }

        Object lhs = obj.get("lhs");
        if (lhs instanceof JSONObject) {
            rootNode.left = buildParseTree(rootNode.left, (JSONObject) lhs);
        } else {
            rootNode.left = new ParseTreeNode(String.valueOf(lhs));
        }

        Object rhs = obj.get("rhs");
        if (rhs instanceof JSONObject) {
            rootNode.right = buildParseTree(rootNode.right, (JSONObject) rhs);
        } else {
            rootNode.right = new ParseTreeNode(String.valueOf(rhs));
        }
        return rootNode;
    }

    ParseTreeNode getNodeContainingVariable() {
        return getNodeContainingVariable(root);
    }

    private ParseTreeNode getNodeContainingVariable(ParseTreeNode rootNode) {
        if (rootNode == null) {
            return null;
        }
        if (rootNode.data.isVariable()) {
            return rootNode;
        }
        ParseTreeNode nodeContainingVariable;
        nodeContainingVariable = getNodeContainingVariable(rootNode.left);
        if (nodeContainingVariable == null) {
            nodeContainingVariable = getNodeContainingVariable(rootNode.right);
        }
        return nodeContainingVariable;
    }

    /**
     * @param node - the parse tree node
     * @return - true if the subtree rooted at node contains the variable "x"
     */
    public static boolean containsVariable(ParseTreeNode node) {
        return node != null && (node.data.isVariable() || containsVariable(node.left) || containsVariable(node.right));
    }

    public ParseTreeNode getRoot() {
        return root;
    }

    private int countNodes(ParseTreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    /**
     *
     * @return the evaluated value of variable "x"
     */
    double evaluateValX() {
        ParseTreeNode right = root.getRight();
        return evaluateValX(right);
    }

    private double evaluateValX(ParseTreeNode node) {
        if (node == null) {
            return 0;
        }
        Token token = node.getToken();
        if (token.isConstant()) {
            return token.getValue();
        }

        return token.getType().operation().apply(evaluateValX(node.left), evaluateValX(node.right));
    }

    /**
     *
      * @return true if the parse tree follows the rules of equation specified in the assignment
     *  This function will return false if
     *  1. The root is not =
     *  2. The variable is not named as "x"
     *  3. The operators does not belong to the following set : add , subtract , multiply , divide , and equal
     *  4. The RHS is not constant
     */
    boolean isValid() {
        boolean isEqualOperatorAtRoot = root.getToken().isEqualOperator();
        boolean isConstantAtRight = root.getRight().getToken().isConstant();
        return isEqualOperatorAtRoot && isConstantAtRight && isValid(root);
    }

    private boolean isValid(ParseTreeNode root) {
        return root == null || root.getToken().isValid() && isValid(root.left) && isValid(root.right);
    }
}
