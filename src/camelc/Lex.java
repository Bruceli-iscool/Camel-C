package camelc;

import java.util.*;

public class Lex {
    public static void main(String args[]) {
        System.out.println(lex("(){}"));
    }
    public static Map<String, String> lex(String input) {
        // tokens map
        Map<String, String> Tokens = new HashMap<String, String>();
        Map<String, String> TokensInInput = new HashMap<String, String>();
        Tokens.put("RPAREN", ")");
        Tokens.put("LPAREN", "(");
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            String z = "";
            switch(c) {
                case ')':
                TokensInInput.put("RPAREN", ")");
                break;
                case '(':
                TokensInInput.put("LPAREN", "(");
                break;
                case '{':
                TokensInInput.put("LBRACE", "{");
                break;
                case '}':
                TokensInInput.put("RBRACE", "}");
                break;
                default:
                switch(z) {
                    case "":
                    z = z + c;
                }
            }
        }
        return TokensInInput;
        // Tokens.containsKey(string)
    }
}