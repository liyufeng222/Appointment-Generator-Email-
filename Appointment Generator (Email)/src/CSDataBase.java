/**
 * class cs database
 */
public class CSDataBase extends DataBase{
    private static final String INPUT_FILE = "csquestion.txt";
    private static final String F_NAME = "csfaculty.txt";

    /**
     * construct the cs database
     */
    public CSDataBase(){
        super(INPUT_FILE, F_NAME);
    }
}