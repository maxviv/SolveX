public class Token {
    private TokenType type;
    private String rep;

    Token(String rep) {
        this.rep = rep;
        type = TokenType.fromString(rep);
    }

    public Token(String rep, TokenType type) {
        this.rep = rep;
        this.type = type;
    }

    public String display() {
        return type.display(rep);
    }

    public boolean isOperator()
    {
        return type.isOperator();
    }

    public boolean isVariable()
    {
        return type.isVariable();
    }

    public TokenType getType() {
        return type;
    }

    public String getRep() {
        return rep;
    }
}
