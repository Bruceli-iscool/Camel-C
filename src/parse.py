import sys

# parser and evaluator for Camel C

# parse
class Parse:
    def __init__(self, tokens):
        # objects
        self.tokens = tokens
        self.currentToken = None
        self.index = -1
        self.consume()
        self.funcName = None
        # functions
        self.functions = {}

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
                # if function name is main then execute the statements in it
                self.funcName = self.currentToken[1]
                self.consume()
                if self.currentToken[1] == "main":
                    if self.currentToken[0] == "OPEN_PAREN":
                        self.consume()
                        if self.currentToken[0] == "CLOSED_PAREN":
                            self.consume()
                            if self.currentToken[0] == "OPEN_BRACE":
                                self.consume()
                                return self.statement()
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
                else:
                    # else store the function
                    if self.currentToken[0] == "OPEN_PAREN":
                        self.consume()
                        if self.currentToken[0] == "CLOSED_PAREN":
                            self.consume()
                            if self.currentToken[0] == "OPEN_BRACE":
                                self.consume()
                                self.store()
                                if sel.currentToken[0] == "CLOSED_BRACE":
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
                self.advance()
                return expression
            else:
                print("Camel-C: SyntaxError: Expected ';'")
    def store(self):
        statement = self.statement()
        self.functions[self.funcName] = statement

    def exp(self):
        if self.currentToken[0] == "NUMBER":
            return self.currentToken[1]
            self.consume()
        else:
            print("Camel-C: Expected integer.")

        
                
