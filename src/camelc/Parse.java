package camelc;

import java.util.ArrayList;

public class Parse {
    public static void parse(ArrayList<String> input) {
        // parse and execute
        // recursive parsing
        String current = input.get(0);
        if (current == "int") {
            input.remove(0);
            current = input.get(0);
        }

    }
}
