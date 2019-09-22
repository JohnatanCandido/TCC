from phe import paillier
from phe import PaillierPublicKey, PaillierPrivateKey


class Crypto:
    def __init__(self, n, p, q):
        self.pub = PaillierPublicKey(n)
        self.priv = PaillierPrivateKey(self.pub, p, q)

    def dec(self, number):
        number = paillier.EncryptedNumber(self.pub, number)
        return self.priv.decrypt(number)

    def enc(self, number):
        return self.pub.encrypt(number).ciphertext()

    def get_public_key(self):
        return str(self.pub.n)
