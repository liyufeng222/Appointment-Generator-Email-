import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * class login info for the login input
 */
public class LoginInfo {
    private static final File INPUT_FILE = new File("NameList.txt");
    private int ID;
    private String email;
    private String lastName;
    private String firstName;

    /**
     * defaule construtor
     */
    public LoginInfo() {
    }

    /**
     * set the id
     * @param ID
     */
    public void setID(int ID){
        this.ID = ID;
    }

    public int getID(){
        return ID;
    }

    /**
     * set all the information
     * @param email
     * @param lastName
     * @param firstName
     */
    public void setAll(String email, String lastName, String firstName){
        this.email = email.replaceAll("\\s", ""); 
        this.lastName = lastName.replaceAll("\\s", ""); 
        this.firstName = firstName.replaceAll("\\s", ""); 
    }

    /**
     * get the email
     * @return
     */
    public String getEmail(){return email;}
    /**
     * get the last name
     */
    public String getLastName(){return lastName;}
    /**
     * get the first name
     * @return
     */
    public String getFirstName(){return firstName;}

    /**
     * check the data in the file to see if the id have been recorded
     * @return
     */
    public boolean checkData() {
        try (Scanner in = new Scanner(INPUT_FILE)) {
            while (in.hasNext()) {
                int temp_ID = in.nextInt();
                if (temp_ID == ID) {
                    email = in.next();
                    lastName = in.next();
                    firstName = in.next();
                    return true;
                }else{
                    in.next();
                    in.next();
                    in.next();
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Name list file not found!");
        }
        return false;
    }

    /**
     * set the information to the file
     */
    public void setDetail() {
        try(FileWriter fileWriter = new FileWriter(INPUT_FILE, true); PrintWriter printWriter = new PrintWriter(fileWriter)){
            printWriter.println(ID + " " + email + " " + lastName + " " + firstName);  //New line
            printWriter.close();
        }catch (FileNotFoundException e){
            System.out.println("Out put not found");
        }catch (IOException e){
            e.getStackTrace();
        }
    }
}