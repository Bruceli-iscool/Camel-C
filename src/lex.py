import re
import sys

# lexer for camel C
# use re to find all tokens
# tokens
TOKENS = [('INT', r'int\b'),
    ('VOID', r'void\b'),
    ('RETURN', r'return\b'),
    ('IDENTIFIER', r'[a-zA-Z_][a-zA-Z0-9_]*'),
    ('OPEN_PAREN', r'\('),
    ('CLOSED_PAREN', r'\)'),
    ('OPEN_BRACE', r'\{'),
    ('CLOSED_BRACE', r'\}'),
    ('SEMICOLON', r';'),
    ('NUMBER', r'\d+')
]
# lexer
class Lex:
    def __init__(self, source):
        # objects
        self.source = source
        self.position = 0

    def lex(self):
        # find tokens
        while self.position < len(self.source):
            match = None
            for token_type in TOKENS:
                token_name, pattern = token_type
                regex = re.compile(pattern)
                match = regex.match(self.source, self.position)
                if match:
                    value = match.group(0)
                    yield (token_name, value)
                    self.position = match.end()
                    break
            if not match:
                # Skip whitespace characters
                if self.source[self.position].isspace():
                    self.position += 1
                    continue
                else:
                    print(f"Camel-C: SyntaxError: Unknown Identifier: {self.source[self.position]}")
                    sys.exit()
lexer = Lex("int main() {printf 5;}")
result = lexer.lex()
tokens = list(result)
print(tokens)
