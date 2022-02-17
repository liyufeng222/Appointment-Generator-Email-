import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Font;
import java.awt.Dimension;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;

/**
 * class of the login page
 */
public class Login extends JFrame{
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 200;

    private JTextField ID_Text;
    private JTextField email_Text;
    private JTextField lastName_Text;
    private JTextField firstName_Text;

    private LoginInfo user = new LoginInfo();

    /**
     * construt the login page with elements
     */
    public Login(){
        JLabel label1 = new JLabel(" Please enter your information:");
        Font f = new Font(label1.getText(), Font.BOLD, 15);
        label1.setFont(f);
        add(label1, BorderLayout.NORTH);

        JButton login = new JButton("Log In");
        //add action listener
        ButtonListener listener = new ButtonListener();
        login.addActionListener(listener);
        JPanel panel = new JPanel();
        panel.add(login, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        add(createInfoPanel(), BorderLayout.CENTER);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(createFileMenu());

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
    }

    public Student getStudent(){
        return new Student(user.getID(), user.getEmail(), user.getLastName(), user.getFirstName(), new Time());
    }
    /**
     * class ButtonListener to action when click
     */
    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill all the information!", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                //user.setID(Integer.parseInt(ID_Text.getText()));
                if(!user.checkData()){
                    //System.out.println("---------------------------------");
                    user.setAll(email_Text.getText(), lastName_Text.getText(), firstName_Text.getText());
                    user.setDetail();
                }
                //save data and change frame
                if(user.checkData()){
                        Main.changeFrame();
                }
            }
        }
    }

    /**
     * check is the textfield if empty
     * @return
     */
    public boolean isEmpty(){
        return ID_Text.getText().isEmpty() || email_Text.getText().isEmpty() || lastName_Text.getText().isEmpty() || firstName_Text.getText().isEmpty();
    }

    /**
     * exit listener when exit click
     */
    class ExitItemListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            System.exit(0);
        }
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
        menu.add(exitItem);
        return menu;
    }

    /**
     * create the info panel of the login page
     * @return
     */
    public JPanel createInfoPanel(){
        JPanel info = new JPanel();
        //info.setSize(300, 100);
        info.setLayout(new GridLayout(4,2));

        JLabel ID = new JLabel("      ID: ");
        ID_Text = new JTextField();
        ID_Text.setPreferredSize(new Dimension(300,20));
        info.add(ID);
        info.add(ID_Text);

        JLabel email = new JLabel("      Email: ");
        email_Text = new JTextField();
        email_Text.setPreferredSize(new Dimension(300,20));
        email_Text.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                user.setID(Integer.parseInt(ID_Text.getText()));
                if(user.checkData()){
                    email_Text.setText(user.getEmail());
                    firstName_Text.setText(user.getFirstName());
                    lastName_Text.setText(user.getLastName());
                    info.repaint();
                }
            }
        });
        info.add(email);
        info.add(email_Text);
        

        JLabel lastName = new JLabel("      Last Name: ");
        lastName_Text = new JTextField();
        lastName_Text.setPreferredSize(new Dimension(300,20));
        info.add(lastName);
        info.add(lastName_Text);

        JLabel firstName = new JLabel("      First Name: ");
        firstName_Text = new JTextField();   
        firstName_Text.setPreferredSize(new Dimension(300,20)); 
        info.add(firstName);
        info.add(firstName_Text);  
        
        return info;
    }
}
