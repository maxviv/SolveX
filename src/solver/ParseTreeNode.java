package solver;

/**
 * This class is a  binary tree data structure
 * used for representing expression of the form
 * exp1 op exp2
 * where op can be add , subtract , multiply , divide , and equal
 * and exp1 and exp2 can be
 * another operation,
 * Or a fixed number,
 * Or a variable reference
 */
class ParseTreeNode {
    Token data;
    ParseTreeNode left;
    ParseTreeNode right;

    ParseTreeNode(String data) {
        this.data = new Token(data);
    }

    ParseTreeNode(String rep, TokenType data) {
        this.data = new Token(rep, data);
    }

    public Token getToken() {
        return data;
    }

    public ParseTreeNode getLeft() {
        return left;
    }

    public ParseTreeNode getRight() {
        return right;
    }
}