
/**
 * Write a description of class ClinicApp here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.Scanner;
import java.util.Random;

public class ClinicApp
{
    static Scanner inText = new Scanner(System.in);
    static Scanner inChar = new Scanner(System.in);
    static Scanner inNum = new Scanner(System.in);
    
    static Appointment apptList[] = new Appointment[100];
    public static void main(String args[]) {
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
            //System.out.print("\f");
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
                    System.out.println(" "+(i+1)+"]"+apptList[i].toString());
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
        System.out.print(" Enter NRIC : ");
        String NRIC = inText.nextLine();
        System.out.println("+-------------------------------------------------------+");
        System.out.println(" Choose category,");
        System.out.println(" [1] Medical Checkup");
        System.out.println(" [2] Pregnancy Test");
        System.out.println(" [3] Vaccination");
        System.out.println(" [4] Blood Test");
        System.out.println(" [5] Eye Test");
        System.out.println("+-------------------------------------------------------+");
        System.out.print(" Option : ");
        int category = inNum.nextInt();
        System.out.println("+-------------------------------------------------------+");
        while (true) {
            System.out.print(" Date (DD/MM/YYYY) : ");
            String date = inText.nextLine();
            System.out.println("+-------------------------------------------------------+");
            System.out.print(" Time (24-hours format) : ");
            String time = inText.nextLine();
            boolean isExist = false;
            for (int i=0;i<100;i++) {
                Appointment current = (Appointment) apptList[i];
                if (apptList[i] != null) {
                    if (current.getDate().equals(date) && current.getTime().equals(time)) {
                        isExist = true;
                    }
                }
                if (apptList[i] == null) {
                    if (isExist == false) {
                        apptList[i] = new Appointment(generateID(),NRIC,date,time,category);
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
                        System.out.println(" NRIC : " + NRIC);
                        break; // testing, need to change later
                    }
                }
            }
            if (isExist == false) {
                break;
            }
        }
    }
    
    public static String generateID() {
        String ID = "";
        while (true) {
            boolean isExist = false;
            Random rand = new Random();
            int randInt = rand.nextInt(1000);
            for (int i=0;i<apptList.length;i++) {
                if (apptList[i] == null) {
                    break;
                }
                if (apptList[i].getApptID().equals(randInt)) {
                    isExist = true;
                }
            }
            if (isExist == false) {
                ID = Integer.toString(randInt);
                break;
            }
        }
        return ID;
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
                        System.out.println(" 1] Edit date");
                        System.out.println(" 2] Edit time");
                        System.out.println(" 3] Edit category");
                        System.out.println(" 4] Edit payment info");
                        System.out.println(" 5] Back");
                        System.out.println("+-------------------------------------------------------+");
                        System.out.print(" Option : ");
                        int editOpt = inNum.nextInt();
                        System.out.println("+-------------------------------------------------------+");
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
                            System.out.println(" Current category : "+apptList[choice-1].categoryDesc());
                            System.out.println(" Choose new category,");
                            System.out.println(" [1] Medical Checkup");
                            System.out.println(" [2] Pregnancy Test");
                            System.out.println(" [3] Vaccination");
                            System.out.println(" [4] Blood Test");
                            System.out.println(" [5] Eye Test");
                            System.out.println("+-------------------------------------------------------+");
                            System.out.print(" Option : ");
                            int category = inNum.nextInt();
                            System.out.println("+-------------------------------------------------------+");
                            apptList[choice-1].setCategory(category);
                            isEdited = true;
                        }
                        else if (editOpt == 4) {
                            if (apptList[choice-1].getPayStatus() == false) {
                                System.out.print(" Set payment to true? (Y-Yes, N-No) : ");
                                char isPaid = inChar.next().charAt(0);
                                if (isPaid == 'Y' || isPaid == 'y') {
                                    System.out.println(" Set payment method, ");
                                    System.out.println(" [1] Cash");
                                    System.out.println(" [2] Debit");
                                    System.out.print(" Option : ");
                                    int optPay = inNum.nextInt();
                                    apptList[choice-1].setPayStatus(true);
                                    apptList[choice-1].setPayMethod(optPay);
                                    isEdited = true;
                                }
                            }
                        }
                        else if (editOpt == 5) {
                            break;
                        }
                        if (isEdited) {
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
            System.out.println(" Choose an appointment to be deleted");
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
                    System.out.println(" Choose an appointment to be deleted...");
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
        int totalApp = 0;
        int totalAppForEachCat[] = {0,0,0,0,0};
        double totalPay = 0;
        double totalPayForEachCat[] = {0,0,0,0,0};
        for (int i=0;i<apptList.length;i++) {
            if (apptList[i] == null) {
                break;
            }
            if (apptList[i].getCategory()==1) {
                totalAppForEachCat[0]++;
                totalPayForEachCat[0]+=apptList[i].getPayAmount();
            }
            else if (apptList[i].getCategory()==2) {
                totalAppForEachCat[1]++;
                totalPayForEachCat[1]+=apptList[i].getPayAmount();
            }
            else if (apptList[i].getCategory()==3) {
                totalAppForEachCat[2]++;
                totalPayForEachCat[2]+=apptList[i].getPayAmount();
            }
            else if (apptList[i].getCategory()==4) {
                totalAppForEachCat[3]++;
                totalPayForEachCat[3]+=apptList[i].getPayAmount();
            }
            else if (apptList[i].getCategory()==5) {
                totalAppForEachCat[4]++;
                totalPayForEachCat[4]+=apptList[i].getPayAmount();
            }
            totalApp++;
            totalPay+=apptList[i].getPayAmount();
        }
        double average = totalPay/totalApp;
        int max = 0; // to be determined
        int min = 0; // to be determined
        System.out.println(" Total appointment for each category,");
        System.out.println(" Medical Checkup     : "+totalAppForEachCat[0]);
        System.out.println(" Pregnancy Test      : "+totalAppForEachCat[1]);
        System.out.println(" Vaccination         : "+totalAppForEachCat[2]);
        System.out.println(" Blood Test          : "+totalAppForEachCat[3]);
        System.out.println(" Eye Test            : "+totalAppForEachCat[4]);
        System.out.println(" Total               : "+totalApp);
        System.out.println("+-------------------------------------------------------+");
        System.out.println(" Sum of payment amount for each category,");
        System.out.println(" Medical Checkup     : RM "+totalPayForEachCat[0]);
        System.out.println(" Pregnancy Test      : RM "+totalPayForEachCat[1]);
        System.out.println(" Vaccination         : RM "+totalPayForEachCat[2]);
        System.out.println(" Blood Test          : RM "+totalPayForEachCat[3]);
        System.out.println(" Eye Test            : RM "+totalPayForEachCat[4]);
        System.out.println(" Total               : RM "+totalPay);
        System.out.println("+-------------------------------------------------------+");
        System.out.println(" Average payment amount for all category : RM "+average);
        System.out.println(" Maximum number of appointment assigned in a month : "+getHighest());
        System.out.println(" Minimum number of appointment assigned in a month : "+getLowest());
        System.out.println("+-------------------------------------------------------+");
        System.out.print(" Press [Enter] to continue");
        String enter = inText.nextLine();
    }
    
    // NEW
    public static String getMonthName(int month) {
        String monthName = "";
        if (month==1) { monthName="January"; }
        else if (month==2) { monthName="February"; }
        else if (month==3) { monthName="March"; }
        else if (month==4) { monthName="April"; }
        else if (month==5) { monthName="May"; }
        else if (month==6) { monthName="June"; }
        else if (month==7) { monthName="July"; }
        else if (month==8) { monthName="August"; }
        else if (month==9) { monthName="September"; }
        else if (month==10) { monthName="October"; }
        else if (month==11) { monthName="November"; }
        else if (month==12) { monthName="December"; }
        
        return monthName;
    }
    
    // Get the month with highest appointment
    public static String getHighest() {
        int appPerMonth[] = {0,0,0,0,0,0,0,0,0,0,0,0};
        for (int i=0;i<apptList.length;i++) {
            if (apptList[i] == null) {
                break;
            }
            int getMonth = Integer.parseInt(apptList[i].getDate().substring(3,5));
            if (getMonth==1) { appPerMonth[0]++; }
            else if (getMonth==2) { appPerMonth[1]++; }
            else if (getMonth==3) { appPerMonth[2]++; }
            else if (getMonth==4) { appPerMonth[3]++; }
            else if (getMonth==5) { appPerMonth[4]++; }
            else if (getMonth==6) { appPerMonth[5]++; }
            else if (getMonth==7) { appPerMonth[6]++; }
            else if (getMonth==8) { appPerMonth[7]++; }
            else if (getMonth==9) { appPerMonth[8]++; }
            else if (getMonth==10) { appPerMonth[9]++; }
            else if (getMonth==11) { appPerMonth[10]++; }
            else if (getMonth==12) { appPerMonth[11]++; }
        }
        int highest = 0;
        int highestMonth = 0;
        for (int i=0;i<appPerMonth.length;i++) {
            if (appPerMonth[i] > highest) {
                highest = appPerMonth[i];
                highestMonth = i+1;
            }
        }
        if (highest==0) {
            return "N/A";
        }
        else {
            return "\n [ "+getMonthName(highestMonth)+", "+highest+" appointment(s) ]";
        }
    }
    
    // Get the month with lowest appointment
    public static String getLowest() {
        int appPerMonth[] = {0,0,0,0,0,0,0,0,0,0,0,0};
        for (int i=0;i<apptList.length;i++) {
            if (apptList[i] == null) {
                break;
            }
            int getMonth = Integer.parseInt(apptList[i].getDate().substring(3,5));
            if (getMonth==1) { appPerMonth[0]++; }
            else if (getMonth==2) { appPerMonth[1]++; }
            else if (getMonth==3) { appPerMonth[2]++; }
            else if (getMonth==4) { appPerMonth[3]++; }
            else if (getMonth==5) { appPerMonth[4]++; }
            else if (getMonth==6) { appPerMonth[5]++; }
            else if (getMonth==7) { appPerMonth[6]++; }
            else if (getMonth==8) { appPerMonth[7]++; }
            else if (getMonth==9) { appPerMonth[8]++; }
            else if (getMonth==10) { appPerMonth[9]++; }
            else if (getMonth==11) { appPerMonth[10]++; }
            else if (getMonth==12) { appPerMonth[11]++; }
        }
        int lowest = 999;
        int lowestMonth = 0;
        for (int i=0;i<appPerMonth.length;i++) {
            if (appPerMonth[i] < lowest) {
                lowest = appPerMonth[i];
                lowestMonth = i+1;
            }
        }
        if (lowest==999) {
            return "N/A";
        }
        else {
            return "\n [ "+getMonthName(lowestMonth)+", "+lowest+" appointment(s) ]";
        }
    }
}
