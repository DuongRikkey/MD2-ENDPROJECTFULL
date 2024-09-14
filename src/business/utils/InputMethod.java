package business.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class InputMethod {
    /**
     * getString()  Return a String value from the user.
     */
    public static String getString(){
        do {
            String result=getInput();
            if (result.isBlank()){
                System.out.println(ShopTextMessege.ERROR_ALERT);
                continue;
            }
            return result;

        }while (true);
    }

    public static String getInput() {
        Scanner input=new Scanner(System.in);
        return input.nextLine();
    }
    /**
     * getBoolean()  Return a Boolean value from the user.
     */
    public static boolean getBoolean(){
        do {
            String result=getString();
            if (result.equalsIgnoreCase("true")||result.equalsIgnoreCase("false")){
                return Boolean.parseBoolean(result);
            }
            else {
                System.err.println(ShopTextMessege.ERROR_ALERT);
            }

        }while (true);
    }
    public static byte getByte(){
        do {
            String result=getString();
            if (result.equalsIgnoreCase("1")||result.equalsIgnoreCase("0")){}

        }while (true);
    }

    public static int getInteger(){
        do {
            try {
                return Integer.parseInt(getString());
            }
            catch (NumberFormatException e){
                System.err.println(ShopTextMessege.ERROR_ALERT);
            }

        }while (true);
    }

    public static long getLong(){
        do {
            try {
                return Long.parseLong(getString());
            }
            catch (NumberFormatException e){
                System.err.println(ShopTextMessege.ERROR_ALERT);
            }


        }while (true);
    }
    public static double getDouble(){
        do {
            try {
                return Double.parseDouble(getString());
            }catch (NumberFormatException e){
                System.err.println(ShopTextMessege.ERROR_ALERT);
            }
        }while (true);

    }

    public static Date getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        do {
            try {
                return sdf.parse(getInput());
            }
            catch (ParseException e){
                System.out.println(ShopTextMessege.ERROR_DATE);
            }

        }while (true);
    }
}
