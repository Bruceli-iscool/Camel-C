package camelc;

import java.util.ArrayList;

public class Lex {
    // main test method
    public static void main(String[] args) {
        System.out.println(lex("hi John{}()"));
    }
    
    public static ArrayList<String> lex(String input) {
        // list of tokens in String
        ArrayList<String> result = new ArrayList<String>();
        String z = "";
        // ifs are to end strings if it hits a token
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch(c) {
                case '(':
                    if (!z.isEmpty()) {
                        result.add(z);
                        z = "";
                    } 
                    result.add("(");
                    break;
                case ')':
                    if (!z.isEmpty()) {
                        result.add(z);
                        z = "";
                    } 
                    result.add(")");
                    break;
                case '{':
                    if (!z.isEmpty()) {
                        result.add(z);
                        z = "";
                    } 
                    result.add("{");
                    break;
                case '}':
                    if (!z.isEmpty()) {
                        result.add(z);
                        z = "";
                    } 
                    result.add("}");
                    break;
                case ' ':
                    if (!z.isEmpty()) {
                        result.add(z);
                        z = "";
                    }
                    break;
                default:
                    z += c;
                    break;
            }
        }

        if (!z.isEmpty()) {
            result.add(z);
        }
        
        return result;
    }

    }

