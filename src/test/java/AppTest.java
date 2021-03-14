import enviandoEmail.ObjetoEnviarEmail;
import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.util.Properties;

public class AppTest {

    @Test
    public void testEmail() throws InterruptedException {
        /* olhar config do smt do Email
         *usando Gmail. --> javatesteemail2021@gmail.com senha--> zara1981
         */
        StringBuilder stringBuilderTextoEmail = new StringBuilder();
        stringBuilderTextoEmail.append("testando html,</br>");
        stringBuilderTextoEmail.append("<h1>Testando Html Email</h1> <br><br>");
        stringBuilderTextoEmail.append("<h3>testando html,</br>testando html,br>testando html,</br>testando html</h3>");
        stringBuilderTextoEmail.append("<b>click aqui ==></b> ");
        stringBuilderTextoEmail.append("<a target=\"_blank\" href=\"https://www.algaworks.com/login\" style=\" color:#2525a7;padding: 14px 25px; text-decoration: none; display:inline-block; border-radius: 20px; font-size:15px; font-family:courier; border: 3px solid green; background-color:#99DA39;\">Acesso Portal AlgaWorks</a> <br><br>");
        stringBuilderTextoEmail.append("<span style=\"font-size:10px\"> Ass: Antonio Joaquim</span> ");

        ObjetoEnviarEmail enviarEmail = new ObjetoEnviarEmail("a.joaquimsfilho@gmail.com","joaquimFilho",
        "testando emailClass",stringBuilderTextoEmail.toString());

       // enviarEmail.EnviarEmail(true);
        enviarEmail.EnviarEmailAnexo(true);

        Thread.sleep(4000);
    }

}
