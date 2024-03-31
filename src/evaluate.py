# Interpreter for Camel C

class Evaluate:
    def __init__(self, ast):
        # objects
        self.ast = ast
        self.functions = {}
        
    def evaluate(self):
        # eval asts
        # it should only be able to handle functions
        for self.funcName, body in self.ast:
            if self.funcName == 'main':
                body = self.statements(body)
                return body
            elif self.funcName == 'printf':
                body = self.statements(body)
                print(body)
                return None
            elif self.funcName in self.functions:
                body = self.functions[self.funcName]
                self.statements(body)
            else:
                self.functions[self.funcName] = body
    def statements(self, body):
        # handle statements
        if '"' and "'" not in body:
            return eval(body)
        elif self.funcName in self.functions:
            body = self.functions[self.funcName]
            self.statements(body)
        else:
            print("Camel-C: SyntaxError: Strings are not allowed in 'int' type functions!")

inter = Evaluate([('hi', 23)])
result = inter.evaluate()
