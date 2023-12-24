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
                    String docID = st.nextToken();
                    String docName = st.nextToken();
                    String specialty = st.nextToken();
                    String phoneNum = st.nextToken();
                    doctor.addLast(new Doctor(docID,docName,specialty,phoneNum));
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
            boolean access_granted = login();
            if (access_granted) {
                // Dashboard
                dashboard();
                break; // temp, delete this after testing
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
    public static boolean login() { return true; }
    
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
                viewList("appointment");
            }
            else if (option_app == 2) {
                addAppointment();
            }
            else if (option_app == 3) {
                editAppointment();
            }
            else if (option_app == 4) {
                deleteAppointment();
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
                editPatient();
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
                editDoctor();
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
                editInvoice();
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
            System.out.println("| [ ] Invoice        | [4] Delete     |");
            System.out.println("| [E] Medicine    >> | [5] Edit       |");
            System.out.println("| [ ] Log Out        |                |");
            System.out.println("+--------------------+----------------+");
            System.out.print(" Option : ");
            int option_app = inNum.nextInt();
            if (option_app == 1) {
                viewList("medicine");
            }
            else if (option_app == 2) {
                addMedicine();
            }
            else if (option_app == 3) {
                editMedicine();
            }
            else if (option_app == 4) {
                deleteMedicine();
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
    public static void viewList(String listName) {
        int totalData = 0;
        int currentPage = 1;
        while (true) {
            System.out.println("\f");
            System.out.println("+-------------------------------------+");
            System.out.println("| VIEW APPOINTMENTS");
            System.out.println("+--------------------+----------------+");
            int counter = 0;
            if (listName.equals("appointment")) {
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
                char option = inChar.next().charAt(0);
                if (option == 'C' || option == 'c') {
                    totalData -= 10;
                    currentPage--;
                }
                else if (option == 'V' || option == 'v') {
                    totalData += 10;
                    currentPage++;
                } else {
                    break;
                }
            } else {
                System.out.println(" Search or [V] Right, [H] Home : ");
                System.out.println("+-------------------------------------+");
                System.out.print(" Option : ");
                char option = inChar.next().charAt(0);
                if (option == 'V' || option == 'v') {
                    totalData += 10;
                    currentPage++;
                } else {
                    break;
                }
            }
        }
        dashboard();
    }
    
    public static void addAppointment() {
        System.out.println("\f");
        System.out.println("+-------------------------------------+");
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
        System.out.println("+-------------------------------------+");
        System.out.println("| Data has been added!");
        System.out.println("+--------------------+----------------+");
        System.out.print(" Press [Enter] to continue");
        String enter = inText.nextLine();
        dashboard();
    }
    public static void editAppointment() {}
    public static void deleteAppointment() {}
    
    public static void editPatient() {}
    
    // Doctor's Processes
    public static void editDoctor() {}
    
    // Invoice's Processes
    public static void editInvoice() {}
    
    // Medicine's Processes
    public static void addMedicine() {}
    public static void editMedicine() {}
    public static void deleteMedicine() {}
}

