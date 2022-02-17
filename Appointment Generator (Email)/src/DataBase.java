import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * database to store questions, faculty, and dean
 */
public class DataBase{
    private static final String DEAN_FILE = "dean.txt";
    private static final String CHAIR_FILE = "chairDept.txt";

    private ArrayList<Question> questions = new ArrayList<Question>();
    private ArrayList<Person> faculties = new ArrayList<Person>();
    private ArrayList<Person> deans = new ArrayList<Person>();
    private Student student;
    private Person chairDept;
    private String questionFile;
    private String facultyFile;
    
    /**
     * construct the dataBase with question file and person list file
     * @param questionFile
     * @param facultyFile
     * @param DeanFile
     */
    public DataBase(String questionFile, String facultyFile){
        this.questionFile = questionFile;
        this.facultyFile = facultyFile;
    }

    /**
     * set student
     * @param student
     */
    public void setStudent(Student student){
        this.student = student;
    }

    /**
     * get the student
     * @return
     */
    public Student getStudent(){
        return student;
    }

    /**
     * get the questions
     * @return
     */
    public ArrayList<Question> getQuestion(){
        return questions;
    }

    /**
     * faculty list
     * @return
     */
    public ArrayList<Person> getFaculties(){
        return faculties;
    }

    /**
     * dean list
     * @return
     */
    public ArrayList<Person> getDeans(){
        return deans;
    }

    /**
     * chair department
     * @return
     */
    public Person getChairDept(){
        return chairDept;
    }

    /**
     * get list from file
     */
    public void searchFaculties(){
        faculties.clear();
        File file = new File(facultyFile); 
        try(Scanner in = new Scanner(file)){
            while(in.hasNextLine()){
                String info = in.nextLine();
                String infoArr[] = info.split(",", -1);
                int ID = Integer.parseInt(infoArr[0]);
                String email = infoArr[1];
                String lastName = infoArr[2];
                String firstName = infoArr[3];
                Time time = new Time();
                time.setTime(infoArr);
                FacultyOrDean person = new FacultyOrDean(ID, email, lastName, firstName, time);    //may change to faculty
                faculties.add(person);
            }
        } catch (FileNotFoundException e){
            System.out.println("Question file not found.");
        }
    }

     /**
     * get list from file
     */
    public void searchDeans(){
        deans.clear();
        File file = new File(DEAN_FILE); 
        try(Scanner in = new Scanner(file)){
            while(in.hasNextLine()){
                String info = in.nextLine();
                String infoArr[] = info.split(",", -1);
                int ID = Integer.parseInt(infoArr[0]);
                String email = infoArr[1];
                String lastName = infoArr[2];
                String firstName = infoArr[3];
                Time time = new Time();
                time.setTime(infoArr);
                FacultyOrDean person = new FacultyOrDean(ID, email, lastName, firstName, time);    //may change to faculty
                deans.add(person);
            }
        } catch (FileNotFoundException e){
            System.out.println("Question file not found.");
        }
    }

     /**
     * get list from file
     */
    public void searchChairDept(){
        File file = new File(CHAIR_FILE); 
        try(Scanner in = new Scanner(file)){
            if(in.hasNextLine()){
                String info = in.nextLine();
                String infoArr[] = info.split(",", -1);
                int ID = Integer.parseInt(infoArr[0]);
                String email = infoArr[1];
                String lastName = infoArr[2];
                String firstName = infoArr[3];
                Time time = new Time();
                time.setTime(infoArr);
                chairDept = new ChairDepartment(ID, email, lastName, firstName, time);    //may change to faculty
            }else{
                chairDept = new ChairDepartment(0, "NULL", "NULL", "NULL", new Time());
            }
        } catch (FileNotFoundException e){
            System.out.println("Question file not found.");
        }
    }

    /**
     * search the question from the file
     */
    public void seachQuestion(){
        questions.clear();
        File file = new File(questionFile); 
        try(Scanner in = new Scanner(file)){
            while(in.hasNextLine()){
                Question q = new Question();
                q.setQuestion(in.nextLine());
                q.setAnswer(in.nextLine());
                questions.add(q);
            }
        } catch (FileNotFoundException e){
            System.out.println("Question file not found.");
        }
    }

    /**
     * find the faculty
     * @param name
     * @return
     */
    public Person nameGetFaculty(String name){
        for(Person p : faculties){
            if(p.display().equals(name)){
                return p;
            }
        }
        return new FacultyOrDean(0,"NULL", "NULL", "NUll",new Time());
    }

    /**
     * find the dean
     * @param name
     * @return
     */
    public Person nameGetDean(String name){
        for(Person p : deans){
            if(p.display().equals(name)){
                return p;
            }
        }
        return new FacultyOrDean(0,"NULL", "NULL", "NUll",new Time());
    }

    /**
     * find the question
     * @param question
     * @return
     */
    public String checkExact(String question){
        for(Question q : questions){
            if(q.displayQues().equals(question)){
                return q.display();
            }
        }
        return "Answer Not found!";
    }

    /**
     * check if has that question
     * @param question
     * @return
     */
    public boolean hasExact(String question){
        for(Question q : questions){
            if(q.displayQues().equals(question)){
                return true;
            }
        }
        return false;
    }

    /**
     * get the answer
     * @param question
     * @return
     */
    public String getAnswer(String question){
        for(Question q : questions){
            if(q.displayQues().equals(question)){
                return q.getAnswer();
            }
        }
        return "Answer Not found!";
    }

    /**
     * get the top 4 questions
     * @param question
     * @return
     */
    public ArrayList<Question> checkPossible(String question){
        ArrayList<Question> topFour = new ArrayList<Question>();
        ArrayList<String> keywords = getKeywords(question);
        ArrayList<Integer> matchNum = new ArrayList<Integer>();
        for(Question q : questions){
            matchNum.add((Integer)q.getMatchNum(keywords));
        }
        for(int i = 0; i < 4; i++){
            int position = foundBiggest(matchNum, i);
            Integer temp = matchNum.get(position);
            Question temp2 = questions.get(position);
            matchNum.remove(position);
            questions.remove(position);
            matchNum.set(0, temp);
            questions.set(0, temp2);
            topFour.add(temp2);
        }
        seachQuestion();   //get everything back to order
        return topFour;
    }

    /**
     * find the beat possible
     * @param matchNum
     * @param from
     * @return
     */
    private int foundBiggest(ArrayList<Integer> matchNum, int from){
        int biggest = matchNum.get(from);
        int position = from;
        for(int i = from + 1; i < matchNum.size(); i++){
            if(matchNum.get(i) > biggest){
                biggest = matchNum.get(i);
                position = i;
            }
        }
        return position;
    }

    /**
     * get the keyword
     * @param question
     * @return
     */
    private ArrayList<String> getKeywords(String question){
        ArrayList<String> keywords = new ArrayList<String>();
        String[] words = question.split(" ", -1);
        for(String s : words){
            if(s.length() >= 5){
                keywords.add(s);
            }
        }
        return keywords;
    }

    /**
     * update the questions
     * @param q
     */
    public void writeToFile(Question q){
        try(FileWriter fileWriter = new FileWriter(questionFile, true); PrintWriter printWriter = new PrintWriter(fileWriter)){
            printWriter.println(q.displayQues());  //New line
            printWriter.println(q.getAnswer());
            printWriter.close();
        }catch (FileNotFoundException e){
            System.out.println("Out put not found");
        }catch (IOException e){
            e.getStackTrace();
        }
    }

    /**
     * update the faculty
     */
    public void updateFaculty(){
        try(FileWriter fileWriter = new FileWriter(facultyFile, false); PrintWriter printWriter = new PrintWriter(fileWriter)){
            for(Person p : faculties){
                printWriter.println(p.dataLine());
            }
            printWriter.close();
        }catch (FileNotFoundException e){
            System.out.println("Out put not found");
        }catch (IOException e){
            e.getStackTrace();
        }
    }

    /**
     * update the dean
     */
    public void updateDean(){
        try(FileWriter fileWriter = new FileWriter(DEAN_FILE, false); PrintWriter printWriter = new PrintWriter(fileWriter)){
            for(Person p : deans){
                printWriter.println(p.dataLine());
            }
            printWriter.close();
        }catch (FileNotFoundException e){
            System.out.println("Out put not found");
        }catch (IOException e){
            e.getStackTrace();
        }
    }

    /**
     * update the chair department
     */
    public void updateChairDept(){
        try(FileWriter fileWriter = new FileWriter(CHAIR_FILE, false); PrintWriter printWriter = new PrintWriter(fileWriter)){
            printWriter.println(chairDept.dataLine());
            printWriter.close();
        }catch (FileNotFoundException e){
            System.out.println("Out put not found");
        }catch (IOException e){
            e.getStackTrace();
        }
    }

    /**
     * make apointment
     * @param type
     * @param name
     * @param time
     */
    public void makeApmt(int type, String name, String time){
        if(type == 1){
            for(int i = 0; i < faculties.size(); i++){
                if(faculties.get(i).display().equals(name)){
                    faculties.get(i).makeApmt(time);
                    faculties.get(i).sendEmail(time, student);
                    student.sendEmail(time, faculties.get(i));
                    updateFaculty();
                }
            }
        }else if(type == 2){
            for(int i = 0; i < deans.size(); i++){
                if(deans.get(i).display().equals(name)){
                    deans.get(i).makeApmt(time);
                    deans.get(i).sendEmail(time, student);
                    student.sendEmail(time, deans.get(i));
                    updateDean();
                }
            }
        }else{
            chairDept.makeApmt(time);
            chairDept.sendEmail(time, student);
            student.sendEmail(time, chairDept);
            updateChairDept();
        }
    }
}