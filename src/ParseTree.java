import org.json.simple.JSONObject;

public class ParseTree {

    private ParseTreeNode root;

    public ParseTree(ParseTreeNode root) {
        this.root = root;
    }

    public void buildParseTree(JSONObject obj) {
        root = buildParseTree(root, obj);
    }

    public void prettyPrintEquation() {
        System.out.println("Equation : ");
        printInorder(root);
        System.out.println("");
    }

    private void printInorder(ParseTreeNode rootNode) {
        if (rootNode != null) {
            boolean shouldPrintBraces = shouldPrintBraces2(rootNode);
            if (shouldPrintBraces) {
                System.out.print("(");
            }
            printInorder(rootNode.left);
            System.out.print(" " + rootNode.data.display() + " ");
            printInorder(rootNode.right);
            if (shouldPrintBraces) {
                System.out.print(")");
            }
        }
    }

    private boolean shouldPrintBraces2(ParseTreeNode rootNode) {
       return countNodes(rootNode) > 1 && rootNode.getToken().getType() != TokenType.OPERATOR_EQUAL;
    }

    private boolean shouldPrintBraces(ParseTreeNode rootNode) {
        if (rootNode == null) {
            return false;
        }
        if (rootNode.data.isOperator()) {
            return rootNode.left != null && rootNode.left.data.isVariable() || rootNode.right != null && rootNode.right.data.isVariable();

        }
        return false;
    }

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

        if (rootNode.left != null) {
            rootNode.left.parent = rootNode;
        }
        if (rootNode.right != null) {
            rootNode.right.parent = rootNode;
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

    public static boolean containsVariable(ParseTreeNode node) {
        return node != null && (node.data.isVariable() || containsVariable(node.left) || containsVariable(node.right));
    }

    public ParseTreeNode getRoot() {
        return root;
    }

    int countNodes(ParseTreeNode node)
    {
        if(node == null){
            return 0;
        }
        return 1+ countNodes(node.left) + countNodes(node.right);
    }
}
