/**
 * class math database to save data
 */
public class MathDataBase extends DataBase{
    private static final String INPUT_FILE = "mathquestion.txt";
    private static final String F_NAME = "mathfaculty.txt";

    /**
     * construct the math database
     */
    public MathDataBase(){
        super(INPUT_FILE, F_NAME);
    }
}