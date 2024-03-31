import sys

# parser for Camel C

# parse
class Parse:
    def __init__(self, tokens):
        # objects
        self.tokens = tokens
        self.currentToken = None
        self.index = -1
        self.consume()
        self.funcName = None

    # helper function
    def consume(self):
        self.index += 1
        if self.index < len(self.tokens):
            # find current token
            self.currentToken = self.tokens[self.index]
        else:
            self.currentToken = None

    # parse and eval
    def parse(self):
        return self.program()

    def program(self):
        return self.function()
    def function(self):
        if self.currentToken[0] == "INT":
            self.consume()
            if self.currentToken[0] == "IDENTIFIER":
                self.funcName = self.currentToken[1]
                self.consume()
                if self.currentToken[0] == "OPEN_PAREN":
                        self.consume()
                        if self.currentToken[0] == "CLOSED_PAREN":
                            self.consume()
                            if self.currentToken[0] == "OPEN_BRACE":
                                self.consume()
                                return [(self.funcName, self.statement())]
                                if self.currentToken[0] == "CLOSED_BRACE":
                                    self.consume()
                                else:
                                    print("Camel-C: SyntaxError: Missing closing '}'")
                            else:
                                print("Camel-C: SyntaxError: Expected '{'")
                        else:
                            print("Camel-C: SyntaxError: Expected closing ')'")
                else:
                    print("Camel-C: SyntaxError: Expected '('")
                
    def statement(self):
        if self.currentToken[0] == "RETURN":
            self.consume()
            expression = self.exp()
            if self.currentToken[0] == "SEMICOLON":
                self.consume()
                return expression
            else:
                print("Camel-C: SyntaxError: Expected ';'")
        elif self.currentToken[0] == "PRINTF":
            self.consume()
            statement = self.exp()
            if self.currentToken[0] == "SEMICOLON":
                return ('PRINTF', statement)
            else:
                print("Camel-C: SyntaxError: Expected ';'")
    def exp(self):
        if self.currentToken[0] == "NUMBER":
            exp = self.currentToken[1]
            self.consume()
            return exp
        else:
            print("Camel-C: Expected integer.")

        
parse = Parse([('INT', 'int'), ('IDENTIFIER', 'main'), ('OPEN_PAREN', '('), ('CLOSED_PAREN', ')'), ('OPEN_BRACE', '{'), ('IDENTIFIER', 'printf'), ('NUMBER', '5'), ('SEMICOLON', ';'), ('CLOSED_BRACE', '}')])
result = parse.parse()
print(result)
