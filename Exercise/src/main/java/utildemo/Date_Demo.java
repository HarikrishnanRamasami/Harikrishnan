package utildemo;

import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;


public class Date_Demo {
    private String startDate;
    private String endDate;

    public void calculateDate(){
        @SuppressWarnings("resource")
        Scanner in=new Scanner(new InputStreamReader(System.in));

        System.out.println("Enter the starting date (DD/MM/YY) :");
        startDate=in.next();

        System.out.println("Enter the End date (DD/MM/YY) :");
        endDate=in.next();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try
        {
            Calendar start = Calendar.getInstance();
            start.setTime(sdf.parse(startDate));
            Calendar end = Calendar.getInstance();
            end.setTime(sdf.parse(endDate));
            int workingDays = 0;
            while(!start.after(end))
            {
                int day = start.get(Calendar.DAY_OF_WEEK);
                if ((day != Calendar.SATURDAY) && (day != Calendar.SUNDAY))
                    workingDays++;
                start.add(Calendar.DATE, 1);
            }
            System.out.println(workingDays);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
    	Date_Demo daysCounter=new Date_Demo();
        daysCounter.calculateDate();
    }
}