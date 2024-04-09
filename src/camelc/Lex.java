package camelc;

import java.util.*;
import java.util.ArrayList;
import java.util.Arrays.*;
public class Lex {
    public static void main(String args[]) {
        System.out.println(lex("(){}"));
    }
    public static Map<String, String> lex(String input) {
        // tokens map
        Map<String, String> Tokens = new HashMap<String, String>();
        ArrayList<String> TokensInInadd = new ArrayList<String>();
        Tokens.put("RPAREN", ")");
        Tokens.put("LPAREN", "(");
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            String z = "";
            switch(c) {
                case ')':
                TokensInInadd.add(")");
                break;
                case '(':
                TokensInInadd.add("LPAREN", "(");
                break;
                case '{':
                TokensInInadd.add("LBRACE", "{");
                break;
                case '}':
                TokensInInadd.add("RBRACE", "}");
                break;
                default:
                switch(z) {
                    case "":
                    z = z + c;
                }
            }
        }
        return TokensInInadd;
        // Tokens.containsKey(string)
    }
}