from phe import paillier


class Crypto:
    def __init__(self):
        self.pub, self.priv = paillier.generate_paillier_keypair()

    def dec(self, number):
        number = paillier.EncryptedNumber(self.pub, number)
        return self.priv.decrypt(number)

    def get_public_key(self):
        return str(self.pub.n)
