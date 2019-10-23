import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText


# TODO Colocar em variável de ambiente
ENDERECO_EMAIL = 'sistema.eleitoral2019@gmail.com'
SENHA = 'SistemaEleitoral2019'

server = smtplib.SMTP(host='smtp.gmail.com', port=587)
server.starttls()
server.login(ENDERECO_EMAIL, SENHA)


def enviar_email(email, mensagem, assunto):
    msg = MIMEMultipart()

    msg['From'] = f'Vote Safe <{ENDERECO_EMAIL}>'
    msg['To'] = email
    msg['Subject'] = assunto

    msg.attach(MIMEText(mensagem, 'plain'))

    server.send_message(msg)
    
    del msg
