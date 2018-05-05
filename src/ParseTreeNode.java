class ParseTreeNode {
    Token data;
    ParseTreeNode left;
    ParseTreeNode right;
    ParseTreeNode parent;

    ParseTreeNode(String data) {
        this.data = new Token(data);
    }

    ParseTreeNode(String rep, TokenType data) {
        this.data = new Token(rep, data);
    }

    public ParseTreeNode getParent()
    {
        return parent;
    }

    public Token getToken()
    {
        return data;
    }

    public ParseTreeNode getLeft() {
        return left;
    }

    public ParseTreeNode getRight() {
        return right;
    }
}