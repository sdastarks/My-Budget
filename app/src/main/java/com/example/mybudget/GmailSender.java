package com.example.mybudget;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import android.net.Uri;
import android.os.Environment;

import android.text.Html;
import android.util.Log;

public class GmailSender {

    final String emailPort = "587";// gmail's smtp port
    final String smtpAuth = "true";
    final String starttls = "true";
    final String emailHost = "smtp.gmail.com";

    private String fromEmail = "pocketmonster.sda5@gmail.com";
    private String fromPassword = "SDA5gpPM";
    String toEmail;
    String emailSubject = "Mocket monster needs your aproval";
    String emailBody;
    @SuppressWarnings("rawtypes")
    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;

    public GmailSender() {
    }

    @SuppressWarnings("rawtypes")
    public GmailSender (String toEmail, String emailBody) {
        this.toEmail = toEmail;
        this.emailBody = emailBody;

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", smtpAuth);
        emailProperties.put("mail.smtp.starttls.enable", starttls);
        Log.i("GmailSender", "Mail server properties set.");
    }

    public MimeMessage createEmailMessage() throws AddressException,
            MessagingException, UnsupportedEncodingException, IOException {

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        emailMessage.setFrom(new InternetAddress(fromEmail));
        emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));


        emailMessage.setSubject(emailSubject);

        BodyPart messageBodyPart = new MimeBodyPart();

        // Now set the actual message
        //messageBodyPart.setText(emailBody);
       // messageBodyPart.setContent(Html.fromHtml("<h1>rdsf</h1>"),"xsd");

        Multipart multipart = new MimeMultipart();

        // Set text message part
        messageBodyPart.setContent(Html.toHtml(Html.fromHtml("<h1>"+emailBody+"</h1>"))+"<br><br>" , "text/html"); //5
        multipart.addBodyPart(messageBodyPart);

        String filename = "/storage/emulated/0/Pocket Monster/logo_pm.jpg";
        File file = new File(filename);
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(file);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        messageBodyPart.setDisposition(MimeBodyPart.INLINE);
        multipart.addBodyPart(messageBodyPart);

        emailMessage.setContent(multipart);

        Log.i("GmailSender", "Email Message created.");
        return emailMessage;
    }

    public void sendEmail() throws AddressException, MessagingException {

        Transport transport = mailSession.getTransport("smtp");
        transport.connect(emailHost, fromEmail, fromPassword);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        Log.i("GmailSender", "Email sent successfully.");
    }

}