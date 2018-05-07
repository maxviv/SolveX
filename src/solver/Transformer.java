package solver;

/**
 * This class is responsible for Transforming the equation represented by a ParseTree, so that
 * we have ‘x’ on one side , and all the operations on the
 * other side. For example for the parse tree:
 * <p>
 * * For eg for equation : 1 + (x * 10) = 21
 * <p>
 * the  transformed expression will  be: x = (21 − 1) / 10
 */
public class Transformer {

    /**
     * @param orig - the Parse Tree representing the original equation
     * @return the parse tree representing the transformed expression so
     * that x is on one side
     */
    public ParseTree solveX(ParseTree orig) {
        //Get the root of the original parse tree representing the equation
        ParseTreeNode equalNode = orig.getRoot();
        assert equalNode.getToken().isEqualOperator();
        //get the RHS part of the equation which will be a constant
        ParseTreeNode constantNodeOnRHS = equalNode.getRight();
        assert constantNodeOnRHS.getToken().isConstant();

        //The left part of the equation where the variable x is located
        ParseTreeNode subtreeContainingVariableX = equalNode.getLeft();
        assert subtreeContainingVariableX != null;
        //This is the root operator node for the transformed expression.
        //For example if the 1 + (x * 10) = 21, the operatorNodeForTransformedExpression
        // will be  a parse tree node as
        //                      (divide /)
        //                    /       \
        //                  -(sub)      10
        //                /   \
        //              21      1
        ParseTreeNode operatorNodeForTransformedExpression;

        //Descend down the original parse tree and restructuring it
        //util we reach the node containing the variable x
        while (true) {
            if (subtreeContainingVariableX.getToken().isVariable()) {
                //If the variableXSubTree contains the variable x, we are done
                operatorNodeForTransformedExpression = constantNodeOnRHS;
                break;
            }
            //Locate the immediate below subtree containing the variable x
            ParseTreeNode left = subtreeContainingVariableX.left;
            boolean leftContainsX = false;
            if (left != null) {
                leftContainsX = ParseTree.containsVariable(left);
            }
            boolean isLeft = true;
            ParseTreeNode nodeNotContainingXOnLHS;
            ParseTreeNode nodeContainingXOnLHS;
            if (leftContainsX) {
                nodeNotContainingXOnLHS = subtreeContainingVariableX.right;
                nodeContainingXOnLHS = subtreeContainingVariableX.left;
                isLeft = false;
            } else {
                nodeNotContainingXOnLHS = subtreeContainingVariableX.left;
                nodeContainingXOnLHS = subtreeContainingVariableX.right;
            }

            Token token = subtreeContainingVariableX.getToken();
            TokenType type = token.getType();
            operatorNodeForTransformedExpression = determineTheOperatorNodeWhenTransposingTheNonVariableLHSExpressionToRHS(subtreeContainingVariableX, isLeft);
            operatorNodeForTransformedExpression.left = constantNodeOnRHS;
            operatorNodeForTransformedExpression.right = nodeNotContainingXOnLHS;

            //If the expression is of type A - Bx, when we move A to the
            //RHS part of the expression, the LHS of the expression is adjusted to
            // (-1)*(B*x).
            subtreeContainingVariableX = adjustSubtreeContainingVariableX(isLeft, nodeContainingXOnLHS, type);

            //The new constant node on RHS of the equation
            constantNodeOnRHS = operatorNodeForTransformedExpression;
            if (nodeContainingXOnLHS.getToken().isVariable()) {
                break;
            }
        }

        return createTreeForTransformedExpression(operatorNodeForTransformedExpression);
    }

    /**
     * @param isLeft          - true if the constant exp or the non variable subtree is on left of the parse tree node
     * @param nodeContainingX - the node containing the variable x
     * @param type            - the type of the token in subtreeContainingVariableX
     * @return - the parse tree node
     */
    private ParseTreeNode adjustSubtreeContainingVariableX(boolean isLeft, ParseTreeNode nodeContainingX, TokenType type) {
        ParseTreeNode adjustedSubtreeContainingVariableX;
        if (type == TokenType.OPERATOR_SUB && isLeft) {
            ParseTreeNode parseTreeNode = new ParseTreeNode("multiply");
            parseTreeNode.left = new ParseTreeNode("-1");
            parseTreeNode.right = nodeContainingX;
            adjustedSubtreeContainingVariableX = parseTreeNode;
        } else {
            adjustedSubtreeContainingVariableX = nodeContainingX;
        }
        return adjustedSubtreeContainingVariableX;
    }

    /**
     * @param variableXSubTree - the parse subtree containing the variable x
     * @param isLeft           - true if the constant part of expression is on the left of the parse tree node
     * @return ParseTreeNode which will be created if we transpose the non variable side expression to the right
     */
    private ParseTreeNode determineTheOperatorNodeWhenTransposingTheNonVariableLHSExpressionToRHS(ParseTreeNode variableXSubTree, boolean isLeft) {
        Token token = variableXSubTree.getToken();
        TokenType type = token.getType();
        return new ParseTreeNode(token.getRep(), getComplementaryOperation(isLeft, type));
    }

    //get the complementary operation when an expression is shifted to the left
    private TokenType getComplementaryOperation(boolean isLeft, TokenType type) {
        TokenType complementary;
        if (isLeft && (type == TokenType.OPERATOR_SUB || type == TokenType.OPERATOR_ADD)) {
            complementary = TokenType.OPERATOR_SUB;
        } else {
            complementary = type.getComplementary();
        }
        return complementary;
    }

    private ParseTree createTreeForTransformedExpression(ParseTreeNode operatorNodeForTransformedExpression) {
        ParseTreeNode transformedRoot = new ParseTreeNode("=", TokenType.OPERATOR_EQUAL);
        transformedRoot.right = operatorNodeForTransformedExpression;
        transformedRoot.left = new ParseTreeNode("x", TokenType.VARIABLE);
        return new ParseTree(transformedRoot);
    }
}
