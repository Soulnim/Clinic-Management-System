/**
 * TITLE : CLINIC MANAGEMENT SYSTEM
 * CONTRIBUTOR :
 * 1. LUQMAN
 * 2. WENG
 * 3. BAWAN
 * 4. ALEP
 * 5. HAZIQ
 */
import java.util.*;
import java.io.*;

public class ClinicLL
{
    // Declare necessary variable
    static Scanner inText = new Scanner(System.in);
    static Scanner inChar = new Scanner(System.in);
    static Scanner inNum = new Scanner(System.in);
    
    static LinkedList appointment = new LinkedList();
    static LinkedList patient = new LinkedList();
    static LinkedList doctor = new LinkedList();
    static LinkedList invoice = new LinkedList();
    static LinkedList medicine = new LinkedList();
    
    static String security_key = "CMS12345";
    static String password = "cms@12345";
    static int access_granted = 2;
    
    public static void main(String args[]) {
        // Fetch data from clinic.txt
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("clinicdata.txt")));
            String data = br.readLine();
            while (data != null) {
                StringTokenizer st = new StringTokenizer(data, ";");
                String dataType = st.nextToken();
                if (dataType.equals("Appointment")) {
                    String NRIC = st.nextToken();
                    String date = st.nextToken();
                    String time = st.nextToken();
                    String type = st.nextToken();
                    appointment.addLast(new Appointment(NRIC,date,time,type));
                }
                else if (dataType.equals("Patient")) {
                    String NRIC = st.nextToken();
                    String name = st.nextToken();
                    int age = Integer.parseInt(st.nextToken());
                    patient.addLast(new Patient(NRIC,name,age));
                }
                else if (dataType.equals("Doctor")) {
                    String NRIC = st.nextToken();
                    String docName = st.nextToken();
                    String specialty = st.nextToken();
                    String phoneNum = st.nextToken();
                    doctor.addLast(new Doctor(NRIC,docName,specialty,phoneNum));
                }
                else if (dataType.equals("Invoice")) {
                    String invID = st.nextToken();
                    double invValue = Double.parseDouble(st.nextToken());
                    String payType = st.nextToken();
                    invoice.addLast(new Invoice(invID,invValue,payType));
                }
                else if (dataType.equals("Medicine")) {
                    String medName = st.nextToken();
                    double medPrice = Double.parseDouble(st.nextToken());
                    String medType = st.nextToken();
                }
                else {
                    System.out.println("Data fetching error!");
                    return;
                }
                data = br.readLine();
            }
        }
        catch(Exception e) { System.err.println(e.getMessage()); }
        // Main processes
        while (true) {
            access_granted = login();
            while (access_granted == 1) {
                dashboard();
            }
            if (access_granted == 0) {
                break;
            }
        }
        // Store data to clinicdata.txt
        try {
            FileWriter fw = new FileWriter("clinicdata.txt");
            
            Appointment appData = (Appointment) appointment.getFirst();
            while (appData != null) {
                fw.write(appData.rawData()+"\n");
                appData = (Appointment) appointment.getNext();
            }
            Patient patData = (Patient) patient.getFirst();
            while (patData != null) {
                fw.write(patData.rawData()+"\n");
                patData = (Patient) patient.getNext();
            }
            Doctor docData = (Doctor) doctor.getFirst();
            while (docData != null) {
                fw.write(docData.rawData()+"\n");
                docData = (Doctor) doctor.getNext();
            }
            Invoice invData = (Invoice) invoice.getFirst();
            while (invData != null) {
                fw.write(invData.rawData()+"\n");
                invData = (Invoice) invoice.getNext();
            }
            Medicine medData = (Medicine) medicine.getFirst();
            while (medData != null) {
                fw.write(medData.rawData()+"\n");
                medData = (Medicine) medicine.getNext();
            }
            
            fw.close();
        }
        catch (Exception e) { System.err.println(e.getMessage()); }
    }
    
    // Login system, return either true/false
    public static int login() {
        System.out.print("\f");
        System.out.println("+-------------------------------------+");
        System.out.println("| CMS LOGIN");
        System.out.println("+--------------------+----------------+");
        System.out.print(" Enter security key ('0' to exit) : ");
        String secKey = inText.nextLine();
        if (secKey.equals("0")) {
            System.out.print("\f");
            System.out.println("+-------------------------------------+");
            System.out.println("| Session terminated. See you again!");
            System.out.println("+--------------------+----------------+");
            return 0;
        }
        System.out.print(" Enter password : ");
        String pass = inText.nextLine();
        if (secKey.equals(security_key) && pass.equals(password)) {
            System.out.print("\f");
            System.out.println("+-------------------------------------+");
            System.out.println("| Logged in successfully!");
            System.out.println("+--------------------+----------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
            return 1;
        }
        else {
            System.out.print("\f");
            System.out.println("+-------------------------------------+");
            System.out.println("| Invalid security key or password!");
            System.out.println("+--------------------+----------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
            return 2;
        }
    }
    
    // Dashboard
    public static void dashboard() {
        // Dashboard
        System.out.print("\f");
        System.out.println("+-------------------------------------+");
        System.out.println("| CMS DASHBOARD");
        System.out.println("+--------------------+----------------+");
        System.out.println("| [A] Appointment    |");
        System.out.println("| [B] Patient        |");
        System.out.println("| [C] Doctor         |");
        System.out.println("| [D] Invoice        |");
        System.out.println("| [E] Medicine       |");
        System.out.println("| [F] Log Out        |");
        System.out.println("+--------------------+----------------+");
        System.out.print(" Option : ");
        char option_dash = inChar.next().charAt(0);
        if (option_dash == 'A' || option_dash == 'a') {
            // Appointment
            System.out.print("\f");
            System.out.println("+-------------------------------------+");
            System.out.println("| CMS DASHBOARD");
            System.out.println("+--------------------+----------------+");
            System.out.println("| [A] Appointment >> | [1] View       |");
            System.out.println("| [ ] Patient        | [2] Add        |");
            System.out.println("| [ ] Doctor         | [3] Edit       |");
            System.out.println("| [ ] Invoice        | [4] Delete     |");
            System.out.println("| [ ] Medicine       | [5] Back       |");
            System.out.println("| [ ] Log Out        |                |");
            System.out.println("+--------------------+----------------+");
            System.out.print(" Option : ");
            int option_app = inNum.nextInt();
            if (option_app == 1) {
                int value = viewList("appointment");
            }
            else if (option_app == 2) {
                addToList("appointment");
            }
            else if (option_app == 3) {
                editList("appointment");
            }
            else if (option_app == 4) {
                deleteFromList("appointment");
            }
            else if (option_app == 5) {
                dashboard();
            }
        }
        else if (option_dash == 'B' || option_dash == 'b') {
            // Patient
            System.out.print("\f");
            System.out.println("+-------------------------------------+");
            System.out.println("| CMS DASHBOARD");
            System.out.println("+--------------------+----------------+");
            System.out.println("| [ ] Appointment    | [1] View       |");
            System.out.println("| [B] Patient     >> | [2] Edit       |");
            System.out.println("| [ ] Doctor         | [3] Back       |");
            System.out.println("| [ ] Invoice        |                |");
            System.out.println("| [ ] Medicine       |                |");
            System.out.println("| [ ] Log Out        |                |");
            System.out.println("+--------------------+----------------+");
            System.out.print(" Option : ");
            int option_app = inNum.nextInt();
            if (option_app == 1) {
                viewList("patient");
            }
            else if (option_app == 2) {
                editList("patient");
            }
            else if (option_app == 3) {
                dashboard();
            }
        }
        else if (option_dash == 'C' || option_dash == 'c') {
            // Doctor
            System.out.print("\f");
            System.out.println("+-------------------------------------+");
            System.out.println("| CMS DASHBOARD");
            System.out.println("+--------------------+----------------+");
            System.out.println("| [ ] Appointment    | [1] View       |");
            System.out.println("| [ ] Patient        | [2] Add        |");
            System.out.println("| [C] Doctor      >> | [3] Back       |");
            System.out.println("| [ ] Invoice        |                |");
            System.out.println("| [ ] Medicine       |                |");
            System.out.println("| [ ] Log Out        |                |");
            System.out.println("+--------------------+----------------+");
            System.out.print(" Option : ");
            int option_app = inNum.nextInt();
            if (option_app == 1) {
                viewList("doctor");
            }
            else if (option_app == 2) {
                editList("doctor");
            }
            else if (option_app == 3) {
                dashboard();
            }
        }
        else if (option_dash == 'D' || option_dash == 'd') {
            // Invoice
            System.out.print("\f");
            System.out.println("+-------------------------------------+");
            System.out.println("| CMS DASHBOARD");
            System.out.println("+--------------------+----------------+");
            System.out.println("| [ ] Appointment    | [1] View       |");
            System.out.println("| [ ] Patient        | [2] Add        |");
            System.out.println("| [ ] Doctor         | [3] Back       |");
            System.out.println("| [D] Invoice     >> |                |");
            System.out.println("| [ ] Medicine       |                |");
            System.out.println("| [ ] Log Out        |                |");
            System.out.println("+--------------------+----------------+");
            System.out.print(" Option : ");
            int option_app = inNum.nextInt();
            if (option_app == 1) {
                viewList("invoice");
            }
            else if (option_app == 2) {
                editList("invoice");
            }
            else if (option_app == 3) {
                dashboard();
            }
        }
        else if (option_dash == 'E' || option_dash == 'e') {
            // Log Out
            System.out.print("\f");
            System.out.println("+-------------------------------------+");
            System.out.println("| CMS DASHBOARD");
            System.out.println("+--------------------+----------------+");
            System.out.println("| [ ] Appointment    | [1] View       |");
            System.out.println("| [ ] Patient        | [2] Add        |");
            System.out.println("| [ ] Doctor         | [3] Edit       |");
            System.out.println("| [ ] Invoice        |                |");
            System.out.println("| [E] Medicine    >> |                |");
            System.out.println("| [ ] Log Out        |                |");
            System.out.println("+--------------------+----------------+");
            System.out.print(" Option : ");
            int option_app = inNum.nextInt();
            if (option_app == 1) {
                viewList("medicine");
            }
            else if (option_app == 2) {
                addToList("medicine");
            }
            else if (option_app == 3) {
                editList("medicine");
            }
            else if (option_app == 4) {
                deleteFromList("medicine");
            }
            else if (option_app == 5) {
                dashboard();
            }
        }
        else if (option_dash == 'F' || option_dash == 'f') {
            // Log Out
            System.out.print("\f");
            System.out.println("+-------------------------------------+");
            System.out.println("| CMS DASHBOARD");
            System.out.println("+--------------------+----------------+");
            System.out.println("| [ ] Appointment    |                |");
            System.out.println("| [ ] Patient        |  Are you sure? |");
            System.out.println("| [ ] Doctor         |  [Y] Yes       |");
            System.out.println("| [ ] Invoice        |  [N] No        |");
            System.out.println("| [ ] Medicine       |                |");
            System.out.println("| [E] Log Out     >> |                |");
            System.out.println("+--------------------+----------------+");
            System.out.print(" Option : ");
            char option_logout = inChar.next().charAt(0);
            if (option_logout == 'Y' || option_logout ==  'y') {
                access_granted = 2;
                return;
            } else {
                dashboard();
            }
        }
        else {
            System.out.print("\f");
            System.out.println("+-------------------------------------+");
            System.out.println("| CMS DASHBOARD");
            System.out.println("+--------------------+----------------+");
            System.out.println("| [ ] Appointment    |                |");
            System.out.println("| [ ] Patient        |                |");
            System.out.println("| [ ] Doctor         | Invalid input! |");
            System.out.println("| [ ] Invoice        |                |");
            System.out.println("| [ ] Log Out        |                |");
            System.out.println("+--------------------+----------------+");
            System.out.println(" Press [Enter] to continue");
            String temp = inText.nextLine();
            dashboard();
        }
    }
    
    // VIEW LIST
    public static int viewList(String listName) {
        int totalData = 0;
        int currentPage = 1;
        while (true) {
            System.out.println("\f");
            System.out.println("+-------------------------------------+");
            int counter = 0;
            if (listName.equals("appointment")) {
                System.out.println("| APPOINTMENTS");
                System.out.println("+--------------------+----------------+");
                Appointment current = (Appointment) appointment.getFirst();
                while (counter != totalData) {
                        current = (Appointment) appointment.getNext();
                        counter++;
                }
                while (current != null && counter < (10 * currentPage)) {
                    System.out.println(" "+(counter+1)+" | "+current.toString());
                    System.out.println("+-------------------------------------+");
                    current = (Appointment) appointment.getNext();
                    counter++;
                }
            }
            else if (listName.equals("patient")) {
                System.out.println("| PATIENTS");
                System.out.println("+--------------------+----------------+");
                Patient current = (Patient) patient.getFirst();
                while (counter != totalData) {
                        current = (Patient) patient.getNext();
                        counter++;
                }
                while (current != null && counter < (10 * currentPage)) {
                    System.out.println(" "+(counter+1)+" | "+current.toString());
                    System.out.println("+-------------------------------------+");
                    current = (Patient) patient.getNext();
                    counter++;
                }
            }
            else if (listName.equals("doctor")) {
                System.out.println("| DOCTORS");
                System.out.println("+--------------------+----------------+");
                Doctor current = (Doctor) doctor.getFirst();
                while (counter != totalData) {
                        current = (Doctor) doctor.getNext();
                        counter++;
                }
                while (current != null && counter < (10 * currentPage)) {
                    System.out.println(" "+(counter+1)+" | "+current.toString());
                    System.out.println("+-------------------------------------+");
                    current = (Doctor) doctor.getNext();
                    counter++;
                }
            }
            else if (listName.equals("invoice")) {
                System.out.println("| INVOICE");
                System.out.println("+--------------------+----------------+");
                Invoice current = (Invoice) invoice.getFirst();
                while (counter != totalData) {
                        current = (Invoice) invoice.getNext();
                        counter++;
                }
                while (current != null && counter < (10 * currentPage)) {
                    System.out.println(" "+(counter+1)+" | "+current.toString());
                    System.out.println("+-------------------------------------+");
                    current = (Invoice) invoice.getNext();
                    counter++;
                }
            }
            else if (listName.equals("medicine")) {
                System.out.println("| MEDICINE");
                System.out.println("+--------------------+----------------+");
                Medicine current = (Medicine) medicine.getFirst();
                while (counter != totalData) {
                        current = (Medicine) medicine.getNext();
                        counter++;
                }
                while (current != null && counter < (10 * currentPage)) {
                    System.out.println(" "+(counter+1)+" | "+current.toString());
                    System.out.println("+-------------------------------------+");
                    current = (Medicine) medicine.getNext();
                    counter++;
                }
            }
            System.out.println("[ Page : "+currentPage+" ]");
            if (currentPage > 1) {
                System.out.println(" Search or [C] Left, [V] Right, [H] Home");
                System.out.println("+-------------------------------------+");
                System.out.print(" Option : ");
                String option = inText.nextLine();
                if (option.equalsIgnoreCase("C")) {
                    totalData -= 10;
                    currentPage--;
                }
                else if (option.equalsIgnoreCase("V")) {
                    totalData += 10;
                    currentPage++;
                }
                else if (option.equalsIgnoreCase("H")) {
                    return 0;
                }
                else {
                    try {
                        return Integer.parseInt(option);
                    }
                    catch (NumberFormatException e) {
                        return 0;
                    }
                }
            } else {
                System.out.println(" Search or [V] Right, [H] Home : ");
                System.out.println("+-------------------------------------+");
                System.out.print(" Option : ");
                String option = inText.nextLine();
                if (option.equalsIgnoreCase("V")) {
                    totalData += 10;
                    currentPage++;
                }
                else if (option.equalsIgnoreCase("H")) {
                    break;
                }
                else {
                    try {
                        return Integer.parseInt(option);
                    }
                    catch (NumberFormatException e) {
                        return 0;
                    }
                }
            }
        }
        dashboard();
        return 0; // testing
    }
    
    public static void addToList(String listName) {
        System.out.println("\f");
        System.out.println("+-------------------------------------+");
        if (listName.equals("appointment")) {
            System.out.println("| ADD APPOINTMENTS");
            System.out.println("+--------------------+----------------+");
            System.out.print(" Enter NRIC : ");
            String NRIC = inText.nextLine();
            System.out.print(" Date (DD/MM/YYYY) : ");
            String date = inText.nextLine();
            System.out.print(" Time (24-hours format) : ");
            String time = inText.nextLine();
            System.out.print(" Type : ");
            String type = inText.nextLine();
            appointment.addLast(new Appointment(NRIC,date,time,type));
        }
        else if (listName.equals("medicine")) {
            System.out.println("| ADD MEDICINE");
            System.out.println("+--------------------+----------------+");
            System.out.print("Medicine Name : ");
            String medName = inText.nextLine();
            System.out.print("Price : ");
            double price = inNum.nextDouble();
            System.out.print("Type : ");
            String type = inText.nextLine();
            medicine.addLast(new Medicine(medName,price,type));
        }
        System.out.println("+-------------------------------------+");
        System.out.println("| Data has been added!");
        System.out.println("+--------------------+----------------+");
        System.out.print(" Press [Enter] to continue");
        String enter = inText.nextLine();
        dashboard();
    }
    
    public static void editList(String listName) {
        boolean isEdit = false;
        if (listName.equals("appointment")) {
            int option = viewList("appointment");
            if (option == 0) {
                return;
            }
            int counter = 1;
            Appointment current = (Appointment) appointment.getFirst();
            while (counter != option) {
                current = (Appointment) appointment.getNext();
                counter++;
            }
            System.out.print("\f");
            System.out.println("+-------------------------------------+");
            System.out.println("| EDIT APPOINTMENT");
            System.out.println("+--------------------+----------------+");
            System.out.println(" "+option+" ] "+current.toString());
            System.out.println("+-------------------------------------+");
            System.out.println(" 1] Edit Date");
            System.out.println(" 2] Edit Time");
            System.out.println(" 3] Edit Type");
            System.out.println(" 4] Back");
            System.out.println("+-------------------------------------+");
            System.out.print(" Option : ");
            int editType = inNum.nextInt();
            System.out.println("+-------------------------------------+");
            if (editType == 1) {
                System.out.println(" Current date : "+current.getDate());
                System.out.print(" Enter new date (DD/MM/YYYY) : ");
                String newDate = inText.nextLine();
                current.setDate(newDate);
                isEdit = true;
            }
            else if (editType == 2) {
                System.out.println(" Current time : "+current.getTime());
                System.out.print(" Enter new time (24-hours format) : ");
                String newTime = inText.nextLine();
                current.setTime(newTime);
                isEdit = true;
            }
            else if (editType == 3) {
                System.out.println(" Current type : "+current.getType());
                System.out.print(" Enter new type : ");
                String newType = inText.nextLine();
                current.setType(newType);
                isEdit = true;
            }
            else {
                editList("appointment");
            }
        }
        else if (listName.equals("patient")) {
            // continue...
        }
        else if (listName.equals("doctor")) {
            // continue...
        }
        else if (listName.equals("invoice")) {
            // continue...
        }
        else if (listName.equals("medicine")) {
            // continue...
        }
        if (isEdit == true) {
            System.out.print("\f");
            System.out.println("+-------------------------------------+");
            System.out.println("| Data has been edited!");
            System.out.println("+--------------------+----------------+");
            System.out.println(" Press [Enter] to continue");
            String enter = inText.nextLine();
        }
        editList(listName);
    }
    
    public static void deleteFromList(String listName) {
        if (listName.equals("appointment")) {
            int option = viewList("appointment");
            if (option == 0) {
                return;
            }
            int counter = 1;
            Appointment current = (Appointment) appointment.getFirst();
            while (counter != option) {
                current = (Appointment) appointment.getNext();
                counter++;
            }
            System.out.print("\f");
            System.out.println("+-------------------------------------+");
            System.out.println("| Are you sure you want to delete this?");
            System.out.println("+--------------------+----------------+");
            System.out.println(" "+option+" ] "+current.toString());
            System.out.print(" Option ([Y] Yes, [N] No) : ");
            char deleteOrNot = inChar.next().charAt(0);
            if (deleteOrNot == 'Y' || deleteOrNot == 'y') {
                // delete
                LinkedList tempAppt = new LinkedList();
                counter = 1;
                Appointment currentData = (Appointment) appointment.getFirst();
                while (currentData != null) {
                    if (counter == option) {
                        currentData = (Appointment) appointment.getNext();
                        counter++;
                    }
                    else {
                        tempAppt.addLast(currentData);
                        currentData = (Appointment) appointment.getNext();
                        counter++;
                    }
                }
                appointment.clear();
                currentData = (Appointment) tempAppt.getFirst();
                while (currentData != null) {
                    appointment.addLast(currentData);
                    currentData = (Appointment) tempAppt.getNext();
                }
                System.out.print("\f");
                System.out.println("+-------------------------------------+");
                System.out.println("| Data has been deleted!");
                System.out.println("+--------------------+----------------+");
                System.out.print(" Press [Enter] to continue");
                String enter = inText.nextLine();
            }
            else {
                deleteFromList("appointment");
            }
        }
        else if (listName.equals("patient")) {
            // to be determined
        }
        else if (listName.equals("doctor")) {
            // to be determined
        }
        else if (listName.equals("invoice")) {
            // to be determined
        }
        else if (listName.equals("medicine")) {
            // to be determined
        }
        return;
    }
}

