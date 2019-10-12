from random import randint
from hashlib import md5

tokens = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
          'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', '', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
          'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9']


def generate_password():
    password = ''
    for _ in range(10):
        password += tokens[randint(0, len(tokens)-1)]
    return password


def encrypt_md5(texto):
    return md5(texto.encode()).hexdigest()


def generate_pin():
    pin = ''
    for _ in range(5):
        pin += tokens[randint(0, len(tokens)-1)]
    return pin
