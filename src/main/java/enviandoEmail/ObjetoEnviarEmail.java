package enviandoEmail;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ObjetoEnviarEmail {
    private String userName = "abcjavatesteemail2021@gmail.com";
    private String senha = "********";
    private String listaDestinatarios = "";
    private String nomeRemetente = "";
    private String assuntEmail = "";
    private String textoEmail = "";

    public ObjetoEnviarEmail(String listaDestinatarios, String nomeRemetente, String assuntEmail, String textoEmail) {
        this.listaDestinatarios = listaDestinatarios;
        this.nomeRemetente = nomeRemetente;
        this.assuntEmail = assuntEmail;
        this.textoEmail = textoEmail;
    }

    public void EnviarEmail(boolean envioHtml) {
        /* olhar config do smt do Email
         *usando Gmail. --> javatesteemail2021@gmail.com senha--> zara1981
         */

        try {
            Properties properties = new Properties();

            properties.put("mail.smtp.ssl.trust", "*");
            properties.put("mail.smtp.auth", "true"); // autorizar
            properties.put("mail.smtp.starttls", "true"); //autenticar
            properties.put("mail.smtp.host", "smtp.gmail.com"); // servidor do Gmail google
            properties.put("mail.smtp.port", "465"); //porta do servidor
            properties.put("mail.smtp.socketFactory.port", "465"); // expecifica a porta a ser conectadda pelo socket
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//class socket de conexa //smtp

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, senha);
                }
            });

            Address[] toUser = InternetAddress.parse(listaDestinatarios);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName, nomeRemetente)); /* quem esta enviando e apelido*/
            message.setRecipients(Message.RecipientType.TO, toUser); /*Email de destino*/
            message.setSubject(assuntEmail);/*Assunto do email enviando*/
            if(envioHtml){//verificar se o texto é html
                message.setContent(textoEmail,"text/html; charset=utf-8");
            }else {
                message.setText(textoEmail); /*texto do email*/
            }
            Transport.send(message);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void EnviarEmailAnexo(boolean envioHtml) {

        try {
            Properties properties = new Properties();

            properties.put("mail.smtp.ssl.trust", "*");
            properties.put("mail.smtp.auth", "true"); // autorizar
            properties.put("mail.smtp.starttls", "true"); //autenticar
            properties.put("mail.smtp.host", "smtp.gmail.com"); // servidor do Gmail google
            properties.put("mail.smtp.port", "465"); //porta do servidor
            properties.put("mail.smtp.socketFactory.port", "465"); // expecifica a porta a ser conectadda pelo socket
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//class socket de conexa //smtp

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, senha);
                }
            });

            Address[] toUser = InternetAddress.parse(listaDestinatarios);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName, nomeRemetente)); /* quem esta enviando e apelido*/
            message.setRecipients(Message.RecipientType.TO, toUser); /*Email de destino*/
            message.setSubject(assuntEmail);/*Assunto do email enviando*/

            /*Parte 1 do Email: q é o texto e descrição do e-mail.*/

           MimeBodyPart corpoEmail = new MimeBodyPart();
            if(envioHtml){//verificar se o texto é html
                corpoEmail.setContent(textoEmail,"text/html; charset=utf-8");
            }else {
               corpoEmail.setText(textoEmail); /*texto do email*/
            }
            /*Parte 2 do Email arquivos pdf e outros*/

            MimeBodyPart anexoEmail = new MimeBodyPart();
            /*onde é passado o simulador d PDF você passa o arquivo do banco de dados*/
            anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(simuladorPDF(),"application/pdf")));
            anexoEmail.setFileName("anexoemail.pdf");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(corpoEmail); //juntar as partes dddo email
            multipart.addBodyPart(anexoEmail);
            message.setContent(multipart);


            Transport.send(message);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    /*
     * metodo para simular pdf ou qualqyer arquivo q possa ser enviado por email.
     * podendo pegar do banco de dados base64,byte[],stream de arquivos.
     * */

    private FileInputStream simuladorPDF()throws Exception{
        Document document = new Document();
        File file = new File("fileanexo.pdf");
        file.createNewFile();
        PdfWriter.getInstance(document,new FileOutputStream(file));
        document.open();
        document.add(new Paragraph("Conteudo do PDF anexo com java Mail. esse texto é do pdf"));
        document.close();

        return new FileInputStream(file); // retorna um pdf em branco ocm o texto do paragrafo de exemplo
    }
}
