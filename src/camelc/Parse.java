package camelc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parse {
    public static Map<String, Integer> ints = new HashMap<String, Integer>();
    public static void main(String[] args) {
        ArrayList<String> tokens = Lex.lex("void main() { printf(\"Hello\"); printf(\"Hello\"); }");
        parse(tokens);
    }
    public static void parse(ArrayList<String> input) {
        // parse and execute
        // recursive parsing
        String current = input.get(0);
        while (true) {
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
                                System.err.println("Camel-C: Expected '{'");
                                break;
                            }
                        } else {
                            System.err.println("Camel-C: Expected closing ')'.");
                            break;
                        }
                    } else {
                        System.err.println("Camel-C: Expected '('");
                        break;
                    }
                } else if (current.matches("[a-zA-Z_][a-zA-Z0-9_]*")&& !current.matches("main")) {
                    // handle function definitions
                } else {
                    break;
                }
            }
        }
    }
    private static ArrayList<String> statements(ArrayList<String> input, String x) {
        // handle statements
        // standard output
        while (true) {
            if (x.matches("printf")) {
                input.remove(0);
                x = input.get(0);
                if (x.matches("\\(")) {
                    input.remove(0);
                    x = input.get(0);
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
                                    continue;
                                } else {
                                    System.err.println("Camel-C: Expected ';'");
                                    break;
                                }
                            } else {
                                System.err.println("Camel-C: Expected ')'");
                                break;
                            }
                        } else {
                            System.err.println("Camel-C: Expected '\"'");
                            break;
                        }
                    }
                } else {
                    System.err.println("Camel-C: Expected '('");
                    break;
                }
            } else if (x.matches("int")) {
                // handle variables
                input.remove(0);
                x = input.get(0);
                if (x.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                    String name = x;
                    input.remove(0);
                    x = input.get(0);
                    if (x.matches("=")) {
                        input.remove(0);
                        x = input.get(0);
                        if (x.matches("[0-9_]*")) {
                            int y = Integer.parseInt(x);
                            ints.put(name,  y);
                            input.remove(0);
                            x = input.get(0);
                            if (x.matches(";")) {
                                input.remove(0);
                                x = input.get(0);
                                continue;
                            } else {
                                System.err.println("Camel-C: Expected ';'");
                                break;
                            }
                        }
                    }
                }
            } else {
                break;
            } 
        }
        return input;
    }
}