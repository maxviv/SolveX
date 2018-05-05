enum TokenType {
    OPERATOR_ADD {
        @Override
        public String display(String token) {
            return "+";
        }

        @Override
        public TokenType getComplementary() {
            return OPERATOR_SUB;
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
            return VARIABLE;
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
            return CONSTANT;
        }
    };

    public abstract String display(String token);
    public abstract TokenType getComplementary();
    public  boolean isOperator(){
        return true;
    }

    public  boolean isVariable(){
        return false;
    }

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
        return CONSTANT;
    }
}
