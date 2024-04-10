package camelc;

import java.util.ArrayList;

public class Lex {
    public static ArrayList<String> lex(String input) {
        // list of tokens i String
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch(c) {
                case '(':
                break;
                default:
                break;
            }
        }
        return result;
    }
}
