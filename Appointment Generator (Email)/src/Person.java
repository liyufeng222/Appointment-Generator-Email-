/**
 * abstract person class
 */
public abstract class Person{
    private int ID;
    private String email;
    private String lastName;
    private String firstName;
    private Time time;

    /**
     * construct the person
     * @param ID
     * @param email
     * @param lastName
     * @param firstName
     * @param time
     */
    public Person(int ID, String email, String lastName, String firstName, Time time){
        this.ID = ID;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.time = time;
    }

    /**
     * get the email
     * @return
     */
    public String getEmail(){
        return email;
    }

    /**
     * send email
     * @param time
     * @param person
     */
    public abstract void sendEmail(String time, Person person);

    /**
     * output line
     * @return
     */
    public String dataLine(){
        return ID + "," + email + "," + lastName + "," + firstName + time.getAvariableTime();
    }

    /**
     * display name
     * @return
     */
    public String display(){
        return lastName + ", " + firstName;
    }

    /**
     * get the time
     * @return
     */
    public Time getTime(){
        return time;
    }

    /**
     * make appointment
     * @param time
     */
    public void makeApmt(String time){
        this.time.makeApmt(time);
    }
}