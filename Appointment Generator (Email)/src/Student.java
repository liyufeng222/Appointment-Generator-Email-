import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.util.Properties;

/**
 * student class
 */
public class Student extends Person{
    /**
     * consturct the student
     */
    public Student(int ID, String email, String lastName, String firstName, Time time){
        super(ID, email, lastName, firstName, time);
    }

    /**
     * send email
     */
    public void sendEmail(String time, Person person){
        System.out.println(person.display() + " " + time);
        final String username = "leejj1233211234567@gmail.com";
        final String password = "5664281888";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("leejj1233211234567@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(getEmail())
            );
            message.setSubject("Appointment");
            message.setText("Appointment Time: " + time + "\nOfficer: " + person.display());

            Transport.send(message);

            JOptionPane.showMessageDialog(new JFrame(),"Email has been sent.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}