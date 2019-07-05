from flask import Flask, request
from entities.crypt import Crypto
from business.vote_holder import VoteHolder

app = Flask(__name__)

c = Crypto()
vh = VoteHolder()


@app.route("/get_key", methods=["GET"])
def get_key():
    return c.get_public_key()


@app.route("/decrypt", methods=["POST"])
def decrypt():
    cypher_text = request.json
    return str(c.dec(cypher_text))


@app.route("/vote", methods=["POST"])
def vote():
    voto = int(request.form['voto'])
    vh.add(voto)
    return '200'


@app.route("/get_res", methods=["GET"])
def get_res():
    m = vh.mult()
    enc_res = m % c.pub.nsquare
    vh.votes = []
    return str(c.dec(enc_res))


if __name__ == '__main__':
    app.run(debug=True)
