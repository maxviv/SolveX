package solver;

import java.util.function.BiFunction;

/**
 * This class is used for classifying the tokens of the equation
 * The types are
 * OPERATOR_ADD - representing add operation
 * OPERATOR_SUB - representing subtract operation
 * OPERATOR_MULTIPLY - representing multiply operation
 * OPERATOR_DIV - representing divide operation
 * OPERATOR_EQUAL - representing equal operation
 * VARIABLE - representing single variable reference (x) that occurs somewhere in the LHS of the equation
 * CONSTANT - representing RHS of the equation which will always be a fixed number
 * UNKNOWN  -  unknown token
 */
enum TokenType {
    UNKNOWN {
        @Override
        public String display(String token) {
            return "Not Defined";
        }

        @Override
        public TokenType getComplementary() {
            throw new UnsupportedOperationException();
        }

        @Override
        public BiFunction<Double, Double, Double> operation() {
            throw new UnsupportedOperationException();
        }


    },
    OPERATOR_ADD {
        @Override
        public String display(String token) {
            return "+";
        }

        @Override
        public TokenType getComplementary() {
            return OPERATOR_SUB;
        }

        @Override
        public BiFunction<Double, Double, Double> operation() {
            return (op1, op2) -> op1 + op2;
        }


    },
    OPERATOR_SUB {
        @Override
        public String display(String token) {
            return "-";
        }

        @Override
        public TokenType getComplementary() {
            return OPERATOR_ADD;
        }

        @Override
        public BiFunction<Double, Double, Double> operation() {
            return (op1, op2) -> op1 - op2;
        }
    },
    OPERATOR_EQUAL {
        @Override
        public String display(String token) {
            return "=";
        }

        @Override
        public TokenType getComplementary() {
            return OPERATOR_EQUAL;
        }

        @Override
        public BiFunction<Double, Double, Double> operation() {
            throw new UnsupportedOperationException();
        }
    },
    OPERATOR_MULTIPLY {
        @Override
        public String display(String token) {
            return "*";
        }

        @Override
        public TokenType getComplementary() {
            return OPERATOR_DIV;
        }

        @Override
        public BiFunction<Double, Double, Double> operation() {
            return (op1, op2) -> op1 * op2;
        }
    },
    OPERATOR_DIV {
        @Override
        public String display(String token) {
            return "/";
        }

        @Override
        public TokenType getComplementary() {
            return OPERATOR_MULTIPLY;
        }

        @Override
        public BiFunction<Double, Double, Double> operation() {
            return (op1, op2) -> op1 / op2;
        }
    },
    VARIABLE {
        @Override
        public String display(String token) {
            return "x";
        }

        @Override
        public boolean isOperator() {
            return false;
        }

        @Override
        public boolean isVariable() {
            return true;
        }

        @Override
        public TokenType getComplementary() {
            throw new UnsupportedOperationException();
        }

        @Override
        public BiFunction<Double, Double, Double> operation() {
            throw new UnsupportedOperationException();
        }
    },
    CONSTANT {
        @Override
        public String display(String token) {
            return token;
        }

        @Override
        public boolean isOperator() {
            return false;
        }

        @Override
        public TokenType getComplementary() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isConstant() {
            return true;
        }

        @Override
        public BiFunction<Double, Double, Double> operation() {
            throw new UnsupportedOperationException();
        }
    };

    /**
     *
     * @param token - the tokens of the equation
     * @return the display string of the tokens. For example operator add will be represented as +
     */
    public abstract String display(String token);

    /**
     * @return - the complementary operation of a token type
     * For example
     * for token OPERATOR_ADD, the complementary is OPERATOR_SUB and vice versa
     * for token OPERATOR_DIV, the complementary is OPERATOR_MULTIPLY and vice versa
     */
    public abstract TokenType getComplementary();

    public boolean isOperator() {
        return true;
    }

    public boolean isVariable() {
        return false;
    }

    public boolean isConstant() {
        return false;
    }

    /**
     *
     * @return the mathematical operation representing the token type.
     * For example for OPERATOR_ADD, this should represent addition
     */
    public abstract BiFunction<Double, Double, Double> operation();

    public static TokenType fromString(String token) {
        switch (token) {
            case "add":
                return OPERATOR_ADD;
            case "subtract":
                return OPERATOR_SUB;
            case "multiply":
                return OPERATOR_MULTIPLY;
            case "divide":
                return OPERATOR_DIV;
            case "equal":
                return OPERATOR_EQUAL;
            case "x":
                return VARIABLE;
        }

        try {
            double contant = Double.parseDouble(token);
        } catch (NumberFormatException e) {
            System.out.println("The token " + token + " is not supported");
            return UNKNOWN;
        }
        return CONSTANT;
    }
}
