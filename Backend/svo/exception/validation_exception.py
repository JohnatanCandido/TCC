class ValidationException(Exception):
    def __init__(self, message, errors):
        self.message = message
        self.errors = errors
