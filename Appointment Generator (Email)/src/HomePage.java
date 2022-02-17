import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.Font;
import java.awt.Dimension;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.BorderLayout;

/**
 * class of home page draw the home page frame of quesion and appointment
 */
public class HomePage extends JFrame {
    private DataBase[] database;
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;
    private JComboBox questionList;
    private JComboBox facultyList;
    private JComboBox deanList;

    private JRadioButton faculty;
    private JRadioButton dean;
    private JRadioButton chairD;

    private JRadioButton commonQ;
    private JRadioButton enterQ; // two type question

    private JTextField enterField = new JTextField(); // enter question

    private JPanel resultP = new JPanel(); // for queston result
    private JTextArea result = new JTextArea();

    private JPanel timeP = new JPanel();
    private JComboBox timeSelect = new JComboBox<>();

    private int current = 0;
    // default datatbase is CS

    /**
     * construct the home page with basic elements
     */
    public HomePage() {
        database = new DataBase[] { new CSDataBase(), new MathDataBase()};

        for (int i = 0; i < database.length; i++) {
            database[i].seachQuestion();
            database[i].searchFaculties();
            database[i].searchDeans();
            database[i].searchChairDept();
        }

        add(createQuestionPanel(), BorderLayout.CENTER);

        add(createPointmentPanel(), BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(createFileMenu());
        menuBar.add(createSubMenu());

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
    }

    /**
     * set the student
     */
    public void setStudent(Student student){
        for (int i = 0; i < database.length; i++) {
            database[i].setStudent(student);
        }
    }

    /**
     * class buttonListener to do thing when button click
     */
    class CheckButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (commonQ.isSelected()) {
                String question = (String) questionList.getSelectedItem();
                result.setText(database[current].checkExact(question));
                // System.out.println(database[current].checkExact(question));
                resultP.repaint();
            } else if (enterQ.isSelected()) {
                // System.out.println("------------------------------");
                String question = (String) enterField.getText();
                if(database[current].hasExact(question)){
                    result.setText(database[current].checkExact(question));
                    // System.out.println(database[current].checkExact(question));
                    resultP.repaint();
                }else{
                    ArrayList<Question> topFour = database[current].checkPossible(question);
                    /*for (Question q : topFour) {
                        System.out.println(q.display());
                    }*/
                    resultP.remove(result);
                    resultP.add(createOptionPanel(topFour, question));
                    setVisible(true);
                    //System.out.println("------------------------------");
                }
            }
        }
    }

    /**
     * the question option panel
     * @param topFour
     * @param question
     * @return
     */
    public JPanel createOptionPanel(ArrayList<Question> topFour, String question) {
        JPanel option = new JPanel();
        option.setLayout(new GridLayout(5, 1));

        class chooseButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                Question newQ = new Question();
                String q = ((JRadioButton) event.getSource()).getText();
                //System.out.println(q);
                newQ.setQuestion(question);
                newQ.setAnswer(database[current].getAnswer(q));
                database[current].writeToFile(newQ);
                database[current].seachQuestion();    // update the new question
                resultP.removeAll();
                result.setText(newQ.display());
                resultP.add(result);
                resultP.repaint();
                setVisible(true);
            }
        }
        class noneButtonListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                resultP.removeAll();
                result.setText("Have send eamil to chair department");
                String question = "*" + (String) enterField.getText();
                database[current].getChairDept().sendEmail(question, database[current].getStudent());
                resultP.add(result);
                resultP.repaint();
                setVisible(true);
            }
        }

        ButtonGroup group = new ButtonGroup();
        for(Question q : topFour){
            JRadioButton temp = new JRadioButton(q.displayQues());
            group.add(temp);
            option.add(temp);
            temp.addActionListener(new chooseButtonListener());
        }
        JRadioButton none = new JRadioButton("None of above");
        group.add(none);
        option.add(none);
        none.addActionListener(new noneButtonListener());
        return option;
    }

    /**
     * ExitItemListener class to exit the program
     */
    class ExitItemListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            System.exit(0);
        }
    }

    /**
     * help listener
     */
    class HelpListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            JOptionPane.showMessageDialog(new JFrame(),"Please end email to liyufeng1130@gmail.com.");
        }
    }

    /**
     * ChangeDeptListener action to change the department
     */
    class ChangeDeptListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(getTitle().equals("CS")){
                setTitle("Math");
                current = 1;
                //change database
                //create method to change the combo boxes
            }else{
                setTitle("CS");
                current = 0;
                //change database
            }
            changeAllComboInfo();
        }
    }

    /**
     * create the sub menu of the home page
     */
    public JMenu createSubMenu()
    {
        JMenu menu = new JMenu("Help");
        JMenuItem contectItem = new JMenuItem("Contect");
        contectItem.addActionListener(new HelpListener());
        menu.add(contectItem);
        return menu;
    }

    /**
     Creates the File menu.
     @return the menu
     */
    public JMenu createFileMenu()
    {
        JMenu menu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        ActionListener listener = new ExitItemListener();
        exitItem.addActionListener(listener);

        JMenuItem change = new JMenuItem("Swith Dept.");
        ActionListener listener2 = new ChangeDeptListener();
        change.addActionListener(listener2);

        menu.add(change);
        menu.add(exitItem);
        
        return menu;
    }

    /**
     * create the question panel in the home page
     */
    public JPanel createQuestionPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(createInputFeild());
        panel.add(createResultFeild());
        panel.setBorder(new TitledBorder(new EtchedBorder(), "General Question"));
        return panel;
    }

    /**
     * create the input field in the home page
     * @return
     */
    public JPanel createInputFeild(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        commonQ = new JRadioButton("Common Question List:");
        commonQ.setSelected(true);

        enterQ = new JRadioButton("Enter Question:");

        ButtonGroup group = new ButtonGroup();
        group.add(commonQ);
        group.add(enterQ);

        JPanel combo = new JPanel();
        combo.setLayout(null);
        questionList = new JComboBox();
        //Database question list displayQues
        for(Question s : database[current].getQuestion()){
            questionList.addItem(s.displayQues());
        }

        questionList.setBounds(0, 20, 225, 30);
        combo.add(questionList);

        JPanel textP = new JPanel();
        textP.setLayout(null);
        enterField.setBounds(0, 20, 225, 30);
        textP.add(enterField);

        panel.add(commonQ);
        panel.add(combo);
        panel.add(enterQ);
        panel.add(textP);

        return panel;
    }

    /**
     * create the result field in the home page
     * @return
     */
    public JPanel createResultFeild(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        result.setEditable(false);
        resultP.setLayout(new GridLayout(1,1));
        resultP.add(result);

        JPanel buttonPanel = new JPanel();

        JPanel buttonP = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        JButton button = new JButton("Check Question");
        button.addActionListener(new CheckButtonListener());
        buttonP.add(button);

        buttonPanel.add(buttonP);

        panel.add(buttonPanel);
        panel.add(resultP);

        return panel;
    }



    /**
     * create the appointment field in the home page
     * @return
     */
    public JPanel createPointmentPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        faculty = new JRadioButton("Faculty:");
        faculty.addActionListener(new PersonButtonListener());
        //commonQ.addActionListener(listener);
        faculty.setSelected(true);

        dean = new JRadioButton("Dean:");
        //commonQ.addActionListener(listener);
        dean.addActionListener(new PersonButtonListener());

        chairD = new JRadioButton("Chair Department:");
        chairD.addActionListener(new PersonButtonListener());
        //chairD.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(faculty);
        group.add(dean);
        group.add(chairD);

        facultyList = new JComboBox();
        //later add from file
        for(Person p : database[current].getFaculties()){
            facultyList.addItem(p.display());
        }
        facultyList.addActionListener(new PersonButtonListener());

        deanList = new JComboBox();
        deanList.addActionListener(new PersonButtonListener());
        for(Person p : database[current].getDeans()){
            deanList.addItem(p.display());
        }
        
        Time temp = new Time();
        if(faculty.isSelected()){
            temp = database[current].nameGetFaculty((String) facultyList.getSelectedItem()).getTime();
        }else if(dean.isSelected()){
            temp = database[current].nameGetDean((String) facultyList.getSelectedItem()).getTime();
        }else if(chairD.isSelected()){
            temp = database[current].getChairDept().getTime();
        }
        ArrayList<String> timeList = temp.getTimeList();
        for(String s : timeList){
            timeSelect.addItem(s);
        }
        timeP.add(timeSelect);

        JPanel buttonP = new JPanel();
        JButton button = new JButton("Send");
        button.addActionListener(new SendButtonListener());
        buttonP.add(button);

        panel.add(faculty);
        panel.add(facultyList);
        panel.add(dean);
        panel.add(deanList);
        panel.add(chairD);

        JPanel empty = new JPanel();
        panel.add(empty);

        panel.add(buttonP);
        panel.add(timeP);


        //panel.add(createContectPanel());
        panel.setBorder(new TitledBorder(new EtchedBorder(), "Appointment"));
        return panel;
    }

    /**
     * change all the combo box info by the department 
     */
    public void changeAllComboInfo(){
        if(getTitle().equals("CS")){
            current = 0;
        }
        questionList.removeAllItems();
        for(Question s : database[current].getQuestion()){
            questionList.addItem(s.displayQues());
        }
    }

    /**
     * appointment listener
     */
    class PersonButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            timeSelect.removeAllItems();
            Time temp = new Time();
            if(faculty.isSelected()){
                temp = database[current].nameGetFaculty((String) facultyList.getSelectedItem()).getTime();
            }else if(dean.isSelected()){
                temp = database[current].nameGetDean((String) deanList.getSelectedItem()).getTime();
            }else if(chairD.isSelected()){
                temp = database[current].getChairDept().getTime();
            }
            ArrayList<String> timeList = temp.getTimeList();
            for(String s : timeList){
                timeSelect.addItem(s);
            }
        }
    }

    /**
     * send button action
     */
    class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if(faculty.isSelected()){
                database[current].makeApmt(1, (String) facultyList.getSelectedItem(), (String) timeSelect.getSelectedItem());
            }else if(dean.isSelected()){
                database[current].makeApmt(2, (String) deanList.getSelectedItem(), (String) timeSelect.getSelectedItem());
            }else if(chairD.isSelected()){
                database[current].makeApmt(3, null, (String) timeSelect.getSelectedItem());
            }
        }
    }
}