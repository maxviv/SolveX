package solver;

/**
 * This class is used for representing a token of the equation
 * For example for the equation:
 * 1 + (x * 10) = 21
 * the tokens are 1, +, x, *, 10, = , 21
 * For 1, the token type is CONSTANT and its rep is "1"
 * For +, the token type is OPERATOR_ADD and its rep is "+"
 * For x, the token type is VARIABLE and its rep is "x"
 * For *, the token type is OPERATOR_MULTIPLY and its rep is "*"
 * For 10, the token type is CONSTANT and its rep is "10"
 * For =, the token type is OPERATOR_EQUAL and its rep is "11"
 * For 21, the token type is CONSTANT and its rep is "21"
 */
public class Token {
    private TokenType type;
    private String rep;

    Token(String rep) {
        type = TokenType.fromString(rep);
        this.rep = type.display(rep);
    }

    Token(String rep, TokenType type) {
        this.rep = rep;
        this.type = type;
    }

    public String display() {
        return type.display(rep);
    }

    public boolean isOperator() {
        return type.isOperator();
    }

    public boolean isVariable() {
        return type.isVariable();
    }

    public boolean isConstant() {
        return type.isConstant();
    }

    public TokenType getType() {
        return type;
    }

    public String getRep() {
        return rep;
    }

    /**
     * Gets the value of the token if its a constant
     *
     * @return value if the constant token
     */
    public double getValue() {
        if (type == TokenType.CONSTANT) {
            return Double.parseDouble(rep);
        }
        throw new UnsupportedOperationException("Cannot define value for token which is not a constant");
    }

    /**
     * @return true if the token is valid as per the rules of the assignment
     */
    public boolean isValid() {
        return type != TokenType.UNKNOWN;
    }

    public boolean isEqualOperator() {
        return type == TokenType.OPERATOR_EQUAL;
    }
}
