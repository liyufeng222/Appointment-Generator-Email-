import java.util.ArrayList;

/**
 * class question have all the things in a question
 */
public class Question{
    private String question;
    private String answer;
    private ArrayList<String> keywords = new ArrayList<String>();

    /**
     * construct the question with default
     */
    public Question(){
        question = "NULL";
        answer = "NUll";
    }

    /**
     * set the question
     * @param question
     */
    public void setQuestion(String question){
        this.question = question;
    }

    /**
     * set answer for question
     * @param answer
     */
    public void setAnswer(String answer){
        this.answer = answer;
    }

    public String getAnswer(){
        return answer;
    }

    public int getMatchNum(ArrayList<String> keys){
        int count = 0;
        for(String s : keys){
            for(String s2 : keywords){
                if(s.equals(s2)){
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * get the list of the keyword
     */
    public void getKeywords(){
        String[] words = question.split(" ", -1);
        for(String s : words){
            if(s.length() >= 5){
                keywords.add(s);
            }
        }
    }

    /**
     * display the question
     */
    public String displayQues(){
        return question;
    }

    /**
     * display the whole question with answer
     * @return
     */
    public String display(){
        String m = "";
        m += question;
        m += "\n";
        m += "Answer: \n";
        m += answer;
        return m;
    }
}