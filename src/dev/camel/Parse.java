//package dev.camel;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Parse {
    public static Map<String, Long> ints = new HashMap<String, Long>();
    public static Map<String, String> strings = new HashMap<String, String>();
    public static HashMap<String, HashMap<ArrayList<String>, ArrayList<String>>> voidFunc = new HashMap<String, HashMap<ArrayList<String>, ArrayList<String>>>();
    public static void main(String[] args){
        ArrayList<String> tokens = Lex.lex("void main() { printf(\"Hello\n\"); printf(5); int hi = 5+5*23; printf(hi);}");
        parse(tokens);
    }
    public static void parse(ArrayList<String> input){
        // parse and execute
        // recursive parsing
        while (!input.isEmpty()) {
                String current = input.get(0);
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
                                        continue;
                                    }
                                } else {
                                    System.err.println("\nCamel-C: Expected '{'");
                                    break;
                                }
                            } else {
                                System.err.println("\nCamel-C: Expected closing ')'.");
                                break;
                            }
                        } else {
                            System.err.println("\nCamel-C: Expected '('");
                            break;
                        }
                    } else if (current.matches("[a-zA-Z_][a-zA-Z0-9_]*")&& !current.matches("main")) {
                        // handle function definitions

                        String name = current;
                        if (current.matches("\\(")) {
                            input.remove(0); 
                            current = input.get(0);
                            ArrayList<String> add_to = new ArrayList<String>();
                            while (!current.matches(")")) {
                                add_to.add(current);
                                input.remove(0);
                                current = input.get(0);
                            }
                            if (current.matches("\\)")) {
                                input.remove(0);
                                current = input.get(0);
                                if (current.matches("\\{")) {
                                    input.remove(0);
                                    current = input.get(0);
                                    int stack = 1;
                                    String to_add = "";
                                    ArrayList<String> body = new ArrayList<String>();
                                    while (stack > 0) {
                                        if (current.matches("{")) {
                                            stack += 1;
                                            to_add += current;
                                            input.remove(0);
                                            current = input.get(0);
                                        } else if (current.matches("}")&& stack > 1) {
                                            stack -= 1;
                                            to_add += current;
                                            input.remove(0);
                                            current = input.get(0);
                                        } else if (current.matches("}")&& stack < 2) {
                                            HashMap<ArrayList<String>, ArrayList<String>> total = new HashMap<ArrayList<String>, ArrayList<String>>();
                                            total.put(add_to, body);
                                            voidFunc.put(name, total);
                                            stack -= 1;
                                            break;
                                        } else {
                                            to_add = current;
                                            input.remove(0);
                                            current = input.get(0);
                                        }
                                        body.add(to_add);
                                    }
                                    continue;
                                }
                            }
                        }
                    } else {
                    System.err.println("Camel-C: Unknown token " + input.get(0));
                    break;
                }
        }
    }

    } public static Long eval(String exp) {
        // evaluate an expression 
        Expression expression = new ExpressionBuilder(exp).build();
        long result = (long) expression.evaluate();
        return result;
    }
    public static String replaceVarsInt(String input, Map<String, Long> replacements) {
        // replace variables
        StringBuilder output = new StringBuilder(input);
        for (Map.Entry<String, Long> entry : replacements.entrySet()) {
            String key = entry.getKey();
            Long value = entry.getValue();
            String keyStr = key + ""; // Convert key to string
            int index = output.indexOf(keyStr);
            while (index != -1) {
                output.replace(index, index + keyStr.length(), value.toString());
                index = output.indexOf(keyStr, index + value.toString().length());
            }
        }
        return output.toString();
    }
    public static ArrayList<String> args(ArrayList<String> args, ArrayList<String> tokens, ArrayList<String> originalArgs) {
        // handle arguments
        ArrayList<String> updatedTokens = new ArrayList<>(tokens);
        for (int i = 0; i < originalArgs.size(); i++) {
            String originalArg = originalArgs.get(i);
            String replacement = args.size() > i ? args.get(i) : "";
            for (int j = 0; j < updatedTokens.size(); j++) {
                String updatedToken = updatedTokens.get(j);
                updatedToken = updatedToken.replace(originalArg, replacement);
                updatedTokens.set(j, updatedToken);
            }
        }

        return updatedTokens;
    }
    private static ArrayList<String> statements(ArrayList<String> input, String x){
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
                                    System.err.println("\nCamel-C: Expected ';'");
                                    break;
                                }
                            } else {
                                System.err.println("\nCamel-C: Expected ')'");
                                break;
                            }
                        } else {
                            System.err.println("\nCamel-C: Expected '\"'");
                            break;
                        }
                    } else if (x.matches("^\\d+$")) {
                        System.out.println(eval(x));
                        input.remove(0);
                        x = input.get(0);
                        if (x.matches("\\)")) {
                            input.remove(0);
                            x = input.get(0);
                            if (x.matches("\\;")) {
                                input.remove(0);
                                x = input.get(0);
                                continue;
                            } else {
                                System.err.println("\nCamel-C: Expected ';'");
                                break;
                            }
                        } else {
                            System.err.println("\nCamel-C: Expected ')'");
                            break;
                        }
                    } else if (x.matches("[a-zA-Z_][a-zA-Z0-9_]*")){
                        String output = replaceVarsInt(x, ints);
                        System.out.println(output);
                        input.remove(0);
                        x = input.get(0);
                        if (x.matches("\\)")) {
                            input.remove(0);
                            x = input.get(0);
                            if (x.matches("\\;")) {

                                input.remove(0);
                                x = input.get(0);
                                continue;
                            } else {
                                System.err.println("\nCamel-C: Expected ';'");
                                break;
                            }
                        } else {
                            System.err.println("\nCamel-C: Expected ')'");
                            break;
                        }
                    }
                    
                } else {
                    System.err.println("\nCamel-C: Expected '('");
                    break;
                }
            } else if (x.matches("int")){
                // handle integers
                input.remove(0);
                x = input.get(0);
                if (x.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                    String name = x;
                    input.remove(0);
                    x = input.get(0);
                    if (x.matches("=")) {
                        input.remove(0);
                        x = input.get(0);    
                        long num = eval(x);
                        ints.put(name, num);
                        input.remove(0);
                        x = input.get(0);
                        if (x.matches(";")) {
                            input.remove(0);
                            x = input.get(0);
                            continue;
                        } else {
                            System.err.println("\nCamel-C: Expected a ';'");
                            break;
                        }
                    } else {
                        System.err.println("\nCamel-C: Expected '='");
                        break;
                    }
                } else {
                    System.err.println("\nCamel-C: Expected Identifier");
                    break;
                }
            } else {
                break;
            } 
        }
        return input;
    }


}
