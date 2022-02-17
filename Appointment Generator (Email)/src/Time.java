import java.util.ArrayList;
/**
 * time class store time slot
 */
public class Time{
    private int[][] schedule = new int[7][17]; //0 means untaken, 1 avariable, 2 taken by some one
    private static final String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private static final String[] timeslot = new String[]{"7:30~8:00", "8:00~8:30", "8:30~9:00", "9:00~9:30", "9:30~10:00", "10:00~10:30", "10:30~11:00", "11:00~11:30", "11:30~12:00", "12:00~12:30", "12:30~1:00", "1:00~1:30", "1:30~2:00", "2:00~2:30", "2:30~3:00", "3:00~3:30", "3:30~4:00"};

    /**construct the time */
    public Time(){
        for(int i = 0; i < schedule.length; i++){
            for(int j = 0; j < schedule[0].length; j++){
                schedule[i][j] = 0;
            }
        }
    }

    /**
     * set the schedule
     * @param info
     */
    public void setTime(String[] info){
        for(int i = 4; i < info.length; i++){
            String pos = info[i];
            schedule[Integer.parseInt(pos.substring(0,1))][Integer.parseInt( pos.substring(1,2))] = 1;
        }
    }

    /**
     * get the avariable time
     * @return
     */
    public String getAvariableTime(){ // for out put
        String m = "";
        for(int i = 0; i < schedule.length; i++){
            for(int j = 0; j < schedule[0].length; j++){
                if(schedule[i][j] == 1){
                    m += ",";
                    m += i;
                    m += j;
                }
            }
        }
        return m;
    }

    /**
     * get the string of time
     * @return
     */
    public ArrayList<String> getTimeList(){
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0; i < schedule.length; i++){
            for(int j = 0; j < schedule[0].length; j++){
                if(schedule[i][j] == 1){
                    list.add(days[i] + " " + timeslot[j]);
                }
            }
        }
        return list;
    }

    /**
     * make appointment on that time
     * @param time
     */
    public void makeApmt(String time){
        String info[] = time.split(" ", -1);
        int row = 0;
        int col = 0;
        for(int i = 0; i < 7; i++){
            if(days[i].equals(info[0])){
                row = i;
                break;
            }
        }
        for(int i = 0; i < 17; i++){
            if(timeslot[i].equals(info[1])){
                col = i;
                break;
            }
        }
        schedule[row][col] = 2;
    }
}