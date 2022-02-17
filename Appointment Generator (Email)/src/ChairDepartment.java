import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.util.Properties;

/**class ChairDepartment for chair department */
public class ChairDepartment extends Person{
    /**
     * construct the person
     * @param ID
     * @param email
     * @param lastName
     * @param firstName
     * @param time
     */
    public ChairDepartment(int ID, String email, String lastName, String firstName, Time time){
       super(ID, email, lastName, firstName, time);
    }

    /**
     * send email to this person
     */
    public void sendEmail(String sentence, Person person){
        System.out.println(person.display() + " " + sentence);
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
            if(sentence.charAt(0) == '*'){
                message.setText("Question: " + sentence + "\nStudent: " + person.display());
            }else{
                message.setText("Appointment Time: " + sentence + "\nStudent: " + person.display());
            }

            Transport.send(message);

            JOptionPane.showMessageDialog(new JFrame(),"Email has been sent.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}