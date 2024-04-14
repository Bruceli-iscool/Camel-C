package camelc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parse {
    public static Map<String, Integer> ints = new HashMap<String, Integer>();
    public static void main(String[] args) {
        ArrayList<String> tokens = Lex.lex("void main() { printf(\"Hello\"); printf(\"Hello\"); }");
        System.out.println(tokens);
        parse(tokens);
    }
    public static void parse(ArrayList<String> input) {
        // parse and execute
        // recursive parsing
        String current = input.get(0);
        //while (!input.isEmpty()) {
            if (current.matches("void")) {
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
                                input = statements(input, current);
                                current = input.get(0);
                                if (current.matches("}")) {
                                    input.remove(0);
                                }
                            } else {
                                System.out.println("Camel-C: Expected '{'");
                                //break;
                            }
                        } else {
                            System.out.println("Camel-C: Expected closing ')'.");
                            //break;
                        }
                    } else {
                        System.out.println("Camel-C: Expected '('");
                        //break;
                    }
                } else if (current.matches("[a-zA-Z_][a-zA-Z0-9_]*")&& !current.matches("main")) {
                    // handle function definitions
                } else {
                    //break;
                }
            //}
        }
    }
    private static ArrayList<String> statements(ArrayList<String> input, String x) {
        // handle statements
        // standard output
        while (true) {
            if (x.matches("printf")) {
                System.err.print(x);
                input.remove(0);
                x = input.get(0);
                if (x.matches("\\(")) {
                    input.remove(0);
                    x = input.get(0);
                    while (true) {
                    // handle strings
                    if (x.matches("\"")) {
                        input.remove(0);
                        x = input.get(0);
                        String out = x;
                        input.remove(0);
                        x = input.get(0);
                        if (x.matches("\"")) {
                            input.remove(0);
                            x = input.get(0);
                            if (x.matches("\\)")) {
                                input.remove(0);
                                x = input.get(0);
                                if (x.matches("\\;")) {
                                    System.out.print(out);
                                    input.remove(0);
                                    x = input.get(0);
                                    System.out.println(x);
                                    continue;
                                } else {
                                    System.out.println("Camel-C: Expected ';'");
                                    //break;
                                }
                            } else {
                                System.out.println("Camel-C: Expected ')'");
                                //break;
                            }
                        } else {
                            System.out.println("Camel-C: Expected '\"'");
                            //break;
                        }
                    }
                    }
                } else {
                    System.out.println("Camel-C: Expected '('");
                    //break;
                }
            } else {
                break;
            }/* else if (x.matches("int")) {
                // handle variables
                System.out.print("x");
                input.remove(0);
                x = input.get(0);
                if (x.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                    String name = x;
                    input.remove(0);
                    x = input.get(0);
                    if (x.matches("=")) {
                        input.remove(0);
                        x = input.get(0);
                        if (x.matches("^-?\\d+$")) {
                            int y = Integer.parseInt(x);
                            ints.put(name,  y);
                            input.remove(0);
                            x = input.get(0);
                            if (x.matches(";")) {
                                input.remove(0);
                                x = input.get(0);
                                continue;
                            } else {
                                System.out.println("Camel-C: Expected ';'");
                                break;
                            }
                        } else {
                            System.out.println("Camel-C: Expected an Integer");
                            break;
                        }
                    } else {
                        System.out.println("Camel-C: Expected '='");
                        break;
                    }
                } else {
                    System.out.println("Camel-C: Expected Identifier");
                    break;
                } */
        }  
        
        return input;
    }
}
