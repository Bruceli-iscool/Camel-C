package camelc;

import java.util.ArrayList;

public class Parse {
    public static void main(String[] args) {
        ArrayList<String> tokens = Lex.lex("int main()");
        parse(tokens);
    }
    public static void parse(ArrayList<String> input) {
        // parse and execute
        // recursive parsing
        String current = input.get(0);
        if (current.matches("int")) {
            input.remove(0);
            current = input.get(0);
            // if main function execute code
            if (current.matches("main")) {
                input.remove(0);
                current = input.get(0);
                if (current.matches("\\(")) {
                    input.remove(0);
                    current = input.get(0);
                    if (current.matches("\\)")) {
                        input.remove(0);
                        current = input.get(0);
                        if (current.matches("\\{")) {
                            input.remove(0);
                            current = input.get(0);
                            statements(input);
                        }
                    } else {
                        System.err.println("Camel-C: Expected closing ')'.");
                    }
                } else {
                    System.err.println("Camel-C: Expected '('");
                }
            } else if (current.matches("[a-zA-Z_][a-zA-Z0-9_]*")&& !current.matches("main")) {
                // handle function definitions
            }
        }

    }
    private static void statements(ArrayList<String> input) {
        // handle statements
    }
}
