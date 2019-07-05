class VoteHolder:

    def __init__(self):
        self.votes = []

    def add(self, v):
        self.votes.append(v)

    def mult(self):
        m = 1
        for v in self.votes:
            m *= v
        return m
