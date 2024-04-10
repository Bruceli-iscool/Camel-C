package camelc;

import java.util.ArrayList;
import camelc.Lex;

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
                if (current.matches("(")) {
                    input.remove(0);
                    current = input.get(0);
                    System.out.println("hi");
                }
            } else if (current.matches("[a-zA-Z_][a-zA-Z0-9_]*")&& current != "main") {
                // handle function definitions
            }
        }

    }
}
