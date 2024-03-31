# Interpreter for Camel C

class Evaluate:
    def __init__(self, ast):
        # objects
        self.ast = ast
        self.functions = {}
        
    def evaluate(self):
        # eval asts
        # it should only be able to handle functions
        for funcName, body in self.ast:
            if funcName == 'main':
                return body
            elif funcName == 'printf':
                print(body)
            else:
                self.functions[funcName] = body

inter = Evaluate([('main', 23)])
result = inter.evaluate()
print(result)
