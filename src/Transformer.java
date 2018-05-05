public class Transformer {

    public ParseTreeNode solveX(ParseTree orig)
    {
        ParseTreeNode equalNode = orig.getRoot();
        ParseTreeNode constantNode = equalNode.getRight();
        ParseTreeNode node = equalNode.getLeft();

        ParseTreeNode newOperatorNode;
        while(true){
            ParseTreeNode left = node.left;
            boolean leftContainsX = false;
            if(left != null){
                leftContainsX = ParseTree.containsVariable(left);
            }
            ParseTreeNode nodeNotContainingX;
            ParseTreeNode nodeContainingX;
            if(leftContainsX){
                nodeNotContainingX = node.right;
                nodeContainingX = node.left;
            }else{
                nodeNotContainingX = node.left;
                nodeContainingX = node.right;
            }
            Token token = node.getToken();
            TokenType complementary = token.getType().getComplementary();
            newOperatorNode = new ParseTreeNode(token.getRep(), complementary);
            newOperatorNode.left = constantNode;
            newOperatorNode.right = nodeNotContainingX;
            node = nodeContainingX;
            constantNode = newOperatorNode;
            if(nodeContainingX.getToken().isVariable()){
                break;
            }
        }
        int i = 0;
        ParseTreeNode transformedRoot = new ParseTreeNode("=", TokenType.OPERATOR_EQUAL);
        transformedRoot.right  = newOperatorNode;
        transformedRoot.left = new ParseTreeNode("x", TokenType.VARIABLE);
        ParseTree transformedParseTree = new ParseTree(transformedRoot);
        transformedParseTree.prettyPrintEquation();
        return newOperatorNode;
    }
    public ParseTreeNode transform(ParseTree orig) {
        ParseTreeNode nodeContainingVariable = orig.getNodeContainingVariable();
        ParseTreeNode parent = nodeContainingVariable.getParent();
        ParseTreeNode addOrSubOperatorNode = locateAddOrSubOperatorNode(parent.getParent());
        ParseTreeNode expressionToMove;
        ParseTreeNode left = addOrSubOperatorNode.getLeft();
        if (ParseTree.containsVariable(left)) {
            expressionToMove = addOrSubOperatorNode.getRight();
        } else {
            expressionToMove = left;
        }

        ParseTreeNode constantNodeRHS = orig.getRoot().getRight();
        TokenType type = addOrSubOperatorNode.getToken().getType();
        ParseTreeNode tempNode;
        if (type == TokenType.OPERATOR_ADD) {
            tempNode = new ParseTreeNode("subtract");
        } else {
            tempNode = new ParseTreeNode("add");
        }
        tempNode.left = constantNodeRHS;
        tempNode.right = expressionToMove;


        return null;
    }

    public ParseTreeNode seggregateNodeAttachedToX(ParseTreeNode parentNodeX, ParseTreeNode tempNode) {
        ParseTreeNode parent = parentNodeX.getParent();
        ParseTreeNode newRoot = null;
        TokenType type = parent.getToken().getType();
        while (parent.getToken().getType() == TokenType.OPERATOR_DIV || parent.getToken().getType() == TokenType.OPERATOR_MULTIPLY) {
            if (type == TokenType.OPERATOR_DIV) {
                newRoot = new ParseTreeNode("multiply");
            } else {
                newRoot = new ParseTreeNode("divide");
            }
            if (ParseTree.containsVariable(parent.getRight())) {
                newRoot.right = parent.getLeft();
                newRoot.left = tempNode;
            } else {
                newRoot.left = parent.getRight();
                newRoot.right = tempNode;
            }
        }
        return newRoot;
    }

    public ParseTreeNode locateAddOrSubOperatorNode(ParseTreeNode parentOfVariable) {
        Token token = parentOfVariable.getToken();
        if (token.getType().equals(TokenType.OPERATOR_ADD) ||
                token.getType().equals(TokenType.OPERATOR_SUB)) {
            return parentOfVariable;
        }
        return locateAddOrSubOperatorNode(parentOfVariable.getParent());
    }


}
