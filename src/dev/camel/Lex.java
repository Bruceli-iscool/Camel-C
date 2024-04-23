//package dev.camel;

import java.util.ArrayList;

public class Lex {
    // main test method
    public static void main(String[] args) {
        System.out.println(lex("\"int   \" main() {int y=5}"));
    }
    
    public static ArrayList<String> lex(String input) {
        // list of tokens in String
        ArrayList<String> result = new ArrayList<String>();
        String z = "";
        // detect if a String is created or not
        boolean ifString = false;
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
                case ';':
                    if (!z.isEmpty()) {
                        result.add(z);
                        z = "";
                    }
                    result.add(";");
                    break;
                case '=':
                    if (!z.isEmpty()) {
                        result.add(z);
                        z = "";
                    }
                    result.add("=");
                    break;
                case ',':
                    if (!z.isEmpty()) {
                        result.add(z);
                        z = "";
                    }
                    result.add(",");
                    break;
                case '"':
                    if (!z.isEmpty() && ifString) {
                        ifString = false;
                        result.add(z);
                        z = "";
                    } else {
                        ifString = true;
                    }
                    result.add("\"");
                    break;
                case ' ':
                    if (!z.isEmpty() && ifString == false) {
                        result.add(z);
                        z = "";
                    } else if (ifString) {
                        z += c;
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

