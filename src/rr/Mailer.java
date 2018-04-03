package rr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Properties;    
import javax.mail.*;    
import javax.mail.internet.*;

import java.util.logging.*;

import com.sun.mail.smtp.SMTPTransport;
import com.sun.mail.util.BASE64EncoderStream;   

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Mailer {
	 void sendMail(String smtpServerHost, String smtpServerPort,  String smtpUserName, String fromUserEmail, String fromUserFullName, String toEmail, String subject, String body) {
	     String accessToken = "";
	     String refreshToken = "1/q7MVRalVXh3S8r9fLtb2ylc-qLQHmeTVEpPuJiD5MG4";
	     String oauthClientId = "739945999556-22118sccgpu7b43rt0t43759ej4gou3d.apps.googleusercontent.com";
	     String oauthSecret = "YdnGVH7VAVGlmFQRZF782hUp";
	     final String TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";
	     
	     try {
	            String request = "client_id="+URLEncoder.encode(oauthClientId, "UTF-8")
	                    +"&client_secret="+URLEncoder.encode(oauthSecret, "UTF-8")
	                    +"&refresh_token="+URLEncoder.encode(refreshToken, "UTF-8")
	                    +"&grant_type=refresh_token";
	            HttpURLConnection conn = (HttpURLConnection) new URL(TOKEN_URL).openConnection();
	            conn.setDoOutput(true);
	            conn.setRequestMethod("POST");
	            PrintWriter out = new PrintWriter(conn.getOutputStream());
	            out.print(request); // note: println causes error
	            out.flush();
	            out.close();
	            conn.connect();
	            try {
	                HashMap<String,Object> result;
	                result = new ObjectMapper().readValue(conn.getInputStream(), new TypeReference<HashMap<String,Object>>() {});
	                accessToken = (String) result.get("access_token");
	            } catch (IOException e) {
	                String line;
	                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	                while((line = in.readLine()) != null) {
	                    System.out.println(line);
	                }
	                System.out.flush();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	     
		 
		 try {
	            Properties props = System.getProperties();
	            props.put("mail.transport.protocol", "smtp");
	            props.put("mail.smtp.port", smtpServerPort);
	            props.put("mail.smtp.starttls.enable", "true");

	            Session session = Session.getDefaultInstance(props);
	            session.setDebug(true);

	            MimeMessage msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress(fromUserEmail, fromUserFullName));
	            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
	            msg.setSubject(subject);
	            msg.setContent(body, "text/html");

	            SMTPTransport transport = new SMTPTransport(session, null);
	            transport.connect(smtpServerHost, smtpUserName, null);
	            transport.issueCommand("AUTH XOAUTH2 " + new String(BASE64EncoderStream.encode(String.format("user=%s\1auth=Bearer %s\1\1", smtpUserName, accessToken).getBytes())), 235);
	            transport.sendMessage(msg, msg.getAllRecipients());
	        } catch (Exception ex) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
	        }
	    }
	
}  
