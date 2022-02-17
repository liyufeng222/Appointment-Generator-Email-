/**
 * Author: Yufeng Li
 * Date: 2/13/2020
 * Description: the program to make appointment for student and ask question
 */

import javax.swing.JFrame;

/**
 * main class to run the game
 */
public class Main
{
    private static JFrame login = new Login();
    private static JFrame home = new HomePage();
    public static void main(String[] args)
    {
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setTitle("Login");
        login.setVisible(true);

        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home.setTitle("CS");
        home.setVisible(false);
    }

    /**
     * change frame to anthor frame
     */
    public static void changeFrame(){
        if(login.isVisible()){
            login.setVisible(false);
            home.setVisible(true);
            ((HomePage) home).setStudent(((Login) login).getStudent());
        }else{
            login.setVisible(true);
            home.setVisible(false);
        }
    }
}