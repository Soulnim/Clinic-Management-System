
/**
 * Write a description of class ClinicApp here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.Scanner;

public class ClinicApp
{
    static Scanner inText = new Scanner(System.in);
    static Scanner inChar = new Scanner(System.in);
    static Scanner inNum = new Scanner(System.in);
    
    static Appointment apptList[] = new Appointment[100];
    public static void main(String args[]) {
        // Testing (random data)
        for (int i=0;i<25;i++) {
            apptList[i] = new Appointment();
        }
        //-------------------
        while (true) {
            int key = dashboard();
            if (key == 0) {
                break;
            }
        }
    }
    
    public static int dashboard() {
        System.out.print("\f");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(" CLINIC APPOINTMENT SYSTEM");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(" A] Add New Appointment");
        System.out.println(" B] View Appointment List");
        System.out.println(" C] Edit Appointment Data");
        System.out.println(" D] Delete Appointment");
        System.out.println(" E] Statistics");
        System.out.println(" F] Exit");
        System.out.println("+-------------------------------------------------------+");
        System.out.print(" Option : ");
        char option = inChar.next().charAt(0);
        if (option == 'A' || option == 'a') {
            addAppointment();
        }
        else if (option == 'B' || option == 'b') {
            viewAppointment();
        }
        else if (option == 'C' || option == 'c') {
            editAppointment();
        }
        else if (option == 'D' || option == 'd') {
            deleteAppointment();
        }
        else if (option == 'E' || option == 'e') {
            statistics();
        }
        else if (option == 'F' || option == 'f') {
            System.out.print("\f");
            System.out.println("+-------------------------------------------------------+");
            System.out.println(" System terminated. See you again!");
            System.out.println("+-------------------------------------------------------+");
            return 0;
        }
        else {
            System.out.print("\f");
            System.out.println("+-------------------------------------------------------+");
            System.out.println(" Invalid Key!");
            System.out.println("+-------------------------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
            return 1;
        }
        return 1;
    }
    
    public static void viewAppointment() {
        int dataStart = 0;
        int page = 1;
        while (true) {
            int counter = 0;
            System.out.print("\f");
            System.out.println("+-------------------------------------------------------+");
            System.out.println(" Appointment List");
            System.out.println("+-------------------------------------------------------+");
            for (int i=dataStart; i<100; i++) {
                if (counter != 5) {
                    if (apptList[i] == null) {
                        break;
                    }
                    System.out.println(" "+(i+1)+" ] "+apptList[i].toString());
                    System.out.println("+-------------------------------------------------------+");
                    counter++;
                }
                else {
                    break;
                }
            }
            System.out.println(" [ Page : "+page+" ]");
            char option = 'H';
            if (page == 1) {
                System.out.print(" Insert [V] Next Page, [H] Home : ");
                option = inChar.next().charAt(0);
            }
            else if (page > 1) {
                System.out.print(" Insert [C] Previous Page, [V] Next Page, [H] Home : ");
                option = inChar.next().charAt(0);
            }
            if (option == 'C' || option =='c') {
                dataStart -= 5;
                page -= 1;
            }
            else if (option == 'V' || option == 'v') {
                dataStart += 5;
                page += 1;
            }
            else if (option == 'H' || option == 'h') {
                break;
            }
        }
    }
    
    public static void addAppointment() {
        System.out.print("\f");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(" Add New Appointment");
        System.out.println("+-------------------------------------------------------+");
        System.out.print(" Appointment ID : ");
        String apptID = inText.nextLine();
        System.out.println(" Choose category,");
        System.out.println(" [1] Medical Checkup");
        System.out.println(" [2] Pregnancy Test");
        System.out.println(" [3] To be determined");
        System.out.print(" Option : ");
        int category = inNum.nextInt();
        while (true) {
            System.out.print(" Date (DD/MM/YYYY) : ");
            String date = inText.nextLine();
            System.out.print(" Time (24-hours format) : ");
            String time = inText.nextLine();
            boolean isExist = false;
            for (int i=0;i<100;i++) {
                Appointment current = (Appointment) apptList[i];
                if (apptList[i] != null) {
                    if (current.getDate().equals(date) || current.getTime().equals(time)) {
                        isExist = true;
                    }
                }
                if (apptList[i] == null) {
                    if (isExist == false) {
                        apptList[i] = new Appointment(apptID,date,time,category);
                        System.out.print("\f");
                        System.out.println("+-------------------------------------------------------+");
                        System.out.println(" Data has been added!");
                        System.out.println("+-------------------------------------------------------+");
                        System.out.print(" Press [Enter] to continue");
                        String enter = inText.nextLine();
                        break;
                    }
                    else {
                        System.out.println("+-------------------------------------------------------+");
                        System.out.println(" Date or Time clashed with existing data");
                        System.out.println("+-------------------------------------------------------+");
                        System.out.print(" Press [Enter] to continue");
                        String enter = inText.nextLine();
                        System.out.print("\f");
                        System.out.println("+-------------------------------------------------------+");
                        System.out.println(" Add New Appointment");
                        System.out.println("+-------------------------------------------------------+");
                        System.out.println(" Appointment ID : " + apptID);
                        break; // testing, need to change later
                    }
                }
            }
            if (isExist == false) {
                break;
            }
        }
    }
    
    public static void editAppointment() {
        int dataStart = 0;
        int page = 1;
        boolean isEditing = true;
        while (true) {
            int counter = 0;
            System.out.print("\f");
            System.out.println("+-------------------------------------------------------+");
            System.out.println(" Choose appointment to be edited...");
            System.out.println("+-------------------------------------------------------+");
            for (int i=dataStart; i<100; i++) {
                if (counter != 5) {
                    if (apptList[i] == null) {
                        break;
                    }
                    System.out.println(" "+(i+1)+" ] "+apptList[i].toString());
                    System.out.println("+-------------------------------------------------------+");
                    counter++;
                }
                else {
                    break;
                }
            }
            System.out.println(" [ Page : "+page+" ]");
            String option = "H";
            if (page == 1) {
                System.out.print(" Insert [V] Next Page, [H] Home : ");
                option = inText.nextLine();
            }
            else if (page > 1) {
                System.out.print(" Insert [C] Previous Page, [V] Next Page, [H] Home : ");
                option = inText.nextLine();
            }
            if (option.equalsIgnoreCase("C")) {
                dataStart -= 5;
                page -= 1;
            }
            else if (option.equalsIgnoreCase("V")) {
                dataStart += 5;
                page += 1;
            }
            else if (option.equalsIgnoreCase("H")) {
                isEditing = false;
                break;
            }
            else {
                try {
                    while (true) {
                        int choice = Integer.parseInt(option);
                        System.out.print("\f");
                        System.out.println("+-------------------------------------------------------+");
                        System.out.println(" Edit Appointment");
                        System.out.println("+-------------------------------------------------------+");
                        System.out.println(" "+(choice)+" ] "+apptList[choice-1].toString());
                        System.out.println("+-------------------------------------------------------+");
                        System.out.println(" 1] Edit Date");
                        System.out.println(" 2] Edit Time");
                        System.out.println(" 3] Edit payment info");
                        System.out.println(" 4] Back");
                        System.out.println("+-------------------------------------------------------+");
                        System.out.print(" Option : ");
                        int editOpt = inNum.nextInt();
                        boolean isEdited = false;
                        if (editOpt == 1) {
                            System.out.println(" Old date : "+apptList[choice-1].getDate());
                            System.out.print(" Enter new date (DD/MM/YYYY) : ");
                            String newDate = inText.nextLine();
                            apptList[choice-1].setDate(newDate);
                            isEdited = true;
                        }
                        else if (editOpt == 2) {
                            System.out.println(" Old time : "+apptList[choice-1].getDate());
                            System.out.print(" Enter new time (24-hours format) : ");
                            String newTime = inText.nextLine();
                            apptList[choice-1].setTime(newTime);
                            isEdited = true;
                        }
                        else if (editOpt == 3) {
                            if (apptList[choice-1].getIsPaid() == false) {
                                System.out.println(" Set payment to true? (Y:Yes, N:No) : ");
                                char isPaid = inChar.next().charAt(0);
                                if (isPaid == 'Y' || isPaid == 'y') {
                                    System.out.println(" Set payment method, ");
                                    System.out.println(" [1] Cash");
                                    System.out.println(" [2] Debit");
                                    System.out.print(" Option : ");
                                    int optPay = inNum.nextInt();
                                    apptList[choice-1].setIsPaid(true);
                                    apptList[choice-1].setPayMethod(optPay);
                                    isEdited = true;
                                }
                            }
                        }
                        else if (editOpt == 4) {
                            break;
                        }
                        if (isEdited) {
                            System.out.print("\f");
                            System.out.println("+-------------------------------------------------------+");
                            System.out.println(" Data has been edited!");
                            System.out.println("+-------------------------------------------------------+");
                            System.out.print(" Press [Enter] to continue");
                            String enter = inText.nextLine();
                        }
                    }
                }
                catch (NumberFormatException e) {
                    System.out.print("\f");
                    System.out.println("+-------------------------------------------------------+");
                    System.out.println(" Invalid Key!");
                    System.out.println("+-------------------------------------------------------+");
                }
            }
        }
    }
    
    public static void deleteAppointment() {
        int dataStart = 0;
        int page = 1;
        boolean isDeleting = true;
        while (true) {
            int counter = 0;
            System.out.print("\f");
            System.out.println("+-------------------------------------------------------+");
            System.out.println(" Choose appointment to be deleted");
            System.out.println("+-------------------------------------------------------+");
            for (int i=dataStart; i<100; i++) {
                if (counter != 5) {
                    if (apptList[i] == null) {
                        break;
                    }
                    System.out.println(" "+(i+1)+" ] "+apptList[i].toString());
                    System.out.println("+-------------------------------------------------------+");
                    counter++;
                }
                else {
                    break;
                }
            }
            String option = "H";
            if (page == 1) {
                System.out.println(" [ Page : "+page+" ] [V] Next Page, [H] Home");
                System.out.print(" Choose data to be deleted (eg. 1) : ");
                option = inText.nextLine();
            }
            else if (page > 1) {
                System.out.println(" [ Page : "+page+" ] [C] Previous Page, [V] Next Page, [H] Home");
                System.out.print(" Choose data to be deleted (eg. 1) : ");
                option = inText.nextLine();
            }
            if (option.equalsIgnoreCase("C")) {
                dataStart -= 5;
                page -= 1;
            }
            else if (option.equalsIgnoreCase("V")) {
                dataStart += 5;
                page += 1;
            }
            else if (option.equalsIgnoreCase("H")) {
                isDeleting = false;
                break;
            }
            else {
                try {
                    int choice = Integer.parseInt(option);
                    System.out.print("\f");
                    System.out.println("+-------------------------------------------------------+");
                    System.out.println(" Edit Appointment");
                    System.out.println("+-------------------------------------------------------+");
                    System.out.println(" "+(choice)+" ] "+apptList[choice-1].toString());
                    System.out.println("+-------------------------------------------------------+");
                    System.out.print(" Are you sure to delete this? (Y/N) : ");
                    char deleteOpt = inChar.next().charAt(0);
                    boolean isDeleted = false;
                    if (deleteOpt == 'Y' || deleteOpt == 'y') {
                        int count = choice-1;
                        while (apptList[count] != null) {
                            apptList[count] = apptList[count+1];
                            if(apptList[count+1] == null) {
                                apptList[count] = null;
                            }
                            count++;
                        }
                        System.out.print("\f");
                        System.out.println("+-------------------------------------------------------+");
                        System.out.println(" Data has been removed!");
                        System.out.println("+-------------------------------------------------------+");
                        System.out.print(" Press [Enter] to continue");
                        String enter = inText.nextLine();
                    }
                    else {
                        // do nothing
                    }
                }
                catch (NumberFormatException e) {
                    System.out.print("\f");
                    System.out.println("+-------------------------------------------------------+");
                    System.out.println(" Invalid Key!");
                    System.out.println("+-------------------------------------------------------+");
                }
            }
        }
    }
    
    public static void statistics() {
        System.out.print("\f");
        System.out.println("+-------------------------------------------------------+");
        System.out.println(" Statistics");
        System.out.println("+-------------------------------------------------------+");
        int totalAppForEachCat[] = {0,0,0,0,0};
        for (int i=0;i<apptList.length;i++) {
            if (apptList[i] != null) {
                break;
            }
            if (apptList[i].getCategory()==1) {
                totalAppForEachCat[0]++;
            }
            else if (apptList[i].getCategory()==2) {
                totalAppForEachCat[1]++;
            }
            else if (apptList[i].getCategory()==3) {
                // to be determined
            }
        }
        System.out.println("Total appointment for each category,");
        System.out.println(" Medical Checkup     : "+totalAppForEachCat[0]);
        System.out.println(" Pregnancy Test      : "+totalAppForEachCat[1]);
        System.out.println(" Medical Checkup     : "+totalAppForEachCat[0]);
        System.out.println(" To be determined.");
        System.out.println("+-------------------------------------------------------+");
        System.out.print(" Press [Enter] to continue");
        String enter = inText.nextLine();
    }
}
