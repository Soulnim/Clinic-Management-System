/**
 * Write a description of class ClinicLL here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
// import necessary package
import java.util.*;
import java.io.*;

public class ClinicLLApp
{
    // define necessary attribute
    static Scanner inText = new Scanner(System.in);
    static Scanner inChar = new Scanner(System.in);
    static Scanner inNum = new Scanner(System.in);
    
    static LinkedList appointment = new LinkedList();
    static LinkedList doctor = new LinkedList();
    static LinkedList patient = new LinkedList();
    static LinkedList invoice = new LinkedList();
    static LinkedList medicine = new LinkedList();
    
    static int sessionCode = 0;
    // 0 - not logged in
    // 1 - logged in
    // 2 - terminate session
    static String SECURITY_KEY = "1"; // to be determined
    static String PASSWORD = "1"; // to be determined
    
    public static void main(String args[]) {
        // Fetch data from clinicdata.txt
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("clinicdata.txt")));
            String currentLine = br.readLine();
            while (currentLine != null) {
                StringTokenizer data = new StringTokenizer(currentLine, ";");
                String dataType = data.nextToken();
                if (dataType.equals("Appointment")) {
                    String appID = data.nextToken();
                    String patID = data.nextToken();
                    String docID = data.nextToken();
                    String invID = data.nextToken();
                    String date = data.nextToken();
                    String time = data.nextToken();
                    String type = data.nextToken();
                    appointment.addLast(new Appointment(appID,patID,docID,invID,date,time,type));
                }
                else if (dataType.equals("Doctor")) {
                   String docID = data.nextToken();
                   String docName = data.nextToken();
                   String specialty = data.nextToken();
                   String phoneNum = data.nextToken();
                   doctor.addLast(new Doctor(docID,docName,specialty,phoneNum));
                }
                else if (dataType.equals("Patient")) {
                    String patID = data.nextToken();
                    String NRIC = data.nextToken();
                    String patName = data.nextToken();
                    patient.addLast(new Patient(patID,NRIC,patName));
                }
                else if (dataType.equals("Invoice")) {
                    String invID = data.nextToken();
                    String appID = data.nextToken();
                    double amount = Double.parseDouble(data.nextToken());
                    String payMethod = data.nextToken();
                    invoice.addLast(new Invoice(invID,appID,amount,payMethod));
                }
                else if (dataType.equals("Medicine")) {
                    // to be determined
                }
                else {
                    System.out.println("Data fetching error!");
                    return;
                }
                currentLine = br.readLine();
            }
        }
        catch (Exception e) { System.err.println(e.getMessage()); }
        
        // Main processes
        while (sessionCode != 2) {
            if (sessionCode == 1) {
                dashboard();
            }
            else {
                sessionCode = login();
            }
        }
        
        // Store data to clinicdata.txt
        try {
            FileWriter fw = new FileWriter("clinicdata.txt");
            Appointment currentApp = (Appointment) appointment.getFirst();
            while (currentApp != null) {
                fw.write(currentApp.rawData()+"\n");
                currentApp = (Appointment) appointment.getNext();
            }
            Doctor currentDoc = (Doctor) doctor.getFirst();
            while (currentDoc != null) {
                fw.write(currentDoc.rawData()+"\n");
                currentDoc = (Doctor) doctor.getNext();
            }
            Patient currentPat = (Patient) patient.getFirst();
            while (currentPat != null) {
                fw.write(currentPat.rawData()+"\n");
                currentPat = (Patient) patient.getNext();
            }
            Invoice currentInv = (Invoice) invoice.getFirst();
            while (currentInv != null) {
                fw.write(currentInv.rawData()+"\n");
                currentInv = (Invoice) invoice.getNext();
            }
            Medicine currentMed = (Medicine) medicine.getFirst();
            while (currentMed != null) {
                // fw.write(currentMed.rawData());
                currentMed = (Medicine) medicine.getNext();
            }
            // close file write
            fw.close();
        }
        catch (Exception e) { System.err.println(e.getMessage()); }
    }
    
    // LOGIN - TRUE/FALSE
    public static int login() {
        System.out.print("\f");
        System.out.println("+--------------------------------------+");
        System.out.println("|       CLINIC MANAGEMENT SYSTEM       |");
        System.out.println("+--------------------------------------+");
        System.out.print(" Security key ('0' - Exit) : ");
        String userKey = inText.nextLine();
        if (userKey.equals("0")) {
            System.out.print("\f");
            System.out.println("+--------------------------------------+");
            System.out.println("|         Session Terminated           |");
            System.out.println("+--------------------------------------+");
            return 2;
        }
        System.out.println("+--------------------------------------+");
        System.out.print(" Password : ");
        String userPass = inText.nextLine();
        System.out.println("+--------------------------------------+");
        if (userKey.equals(SECURITY_KEY) || userPass.equals(PASSWORD)) {
            System.out.println("|      LOGGED IN SUCCESSFULLY!         |");
            System.out.println("+--------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
            return 1;
        }
        else {
            System.out.println("|    INVALID USERNAME OR PASSWORD!     |");
            System.out.println("+--------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
            return 0;
        }
    }
    
    // DASHBOARD - MAIN INTERFACE
    public static void dashboard() {
        System.out.print("\f");
        System.out.println("+--------------------------------------+");
        System.out.println("|       CLINIC MANAGEMENT SYSTEM       |");
        System.out.println("+--------------------------------------+");
        System.out.println("|   [A] Appointment                    |");
        System.out.println("|   [B] Patient                        |");
        System.out.println("|   [C] Doctor                         |");
        System.out.println("|   [D] Invoice                        |");
        System.out.println("|   [E] Medicine                       |");
        System.out.println("|   [F] Log Out                        |");
        System.out.println("+--------------------------------------+");
        System.out.print(" Option : ");
        char optDash = inChar.next().charAt(0);
        System.out.println("+--------------------------------------+");
        if (optDash == 'A' || optDash == 'a') {
            viewList(optDash);
        }
        else if (optDash == 'B' || optDash == 'b') {
            viewList(optDash);
        }
        else if (optDash == 'C' || optDash == 'c') {
            viewList(optDash);
        }
        else if (optDash == 'D' || optDash == 'd') {
            viewList(optDash);
        }
        else if (optDash == 'E' || optDash == 'e') {
            viewList(optDash);
        }
        else if (optDash == 'F' || optDash == 'f') {
            sessionCode = 0;
        }
        else {
            System.out.println("+--------------------------------------+");
            System.out.println("|           Invalid Key!               |");
            System.out.println("+--------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
        }
    }
    
    // VIEW LIST
    public static void viewList(char listCode) {
        int currentPage = 1;
        int dataFloor = 0;
        while (true) {
            if (listCode == 'A' || listCode == 'a') {
                int counter = 0;
                System.out.print("\f");
                System.out.println("+--------------------------------------+");
                System.out.println("|           Appointments               |");
                System.out.println("+--------------------------------------+");
                Appointment current = (Appointment) appointment.getFirst();
                while (counter != dataFloor) {
                    current = (Appointment) appointment.getNext();
                    counter++;
                }
                while (current != null && counter < (10*currentPage)) {
                    System.out.println(" "+(counter+1)+" ] "+current.getAppID()+" | "+current.getDate()+" | "+current.getTime());
                    System.out.println("+--------------------------------------+");
                    current = (Appointment) appointment.getNext();
                    counter++;
                }
            }
            else if (listCode == 'B' || listCode == 'b') {
                int counter = 0;
                System.out.print("\f");
                System.out.println("+--------------------------------------+");
                System.out.println("|             Patients                 |");
                System.out.println("+--------------------------------------+");
                Patient current = (Patient) patient.getFirst();
                while (counter != dataFloor) {
                    current = (Patient) patient.getNext();
                    counter++;
                }
                while (current != null && counter < (10*currentPage)) {
                    System.out.println(" "+(counter+1)+" ] "+current.getPatientID()+" | "+current.getNRIC()+" | "+current.getPatientName());
                    System.out.println("+--------------------------------------+");
                    current = (Patient) patient.getNext();
                    counter++;
                }
            }
            else if (listCode == 'C' || listCode == 'c') {
                int counter = 0;
                System.out.print("\f");
                System.out.println("+--------------------------------------+");
                System.out.println("|               Doctor                 |");
                System.out.println("+--------------------------------------+");
                Doctor current = (Doctor) doctor.getFirst();
                while (counter != dataFloor) {
                    current = (Doctor) doctor.getNext();
                    counter++;
                }
                while (current != null && counter < (10*currentPage)) {
                    System.out.println(" "+(counter+1)+" ] "+current.getDoctorID()+" | "+current.getDoctorName()+" | "+current.getPhoneNum());
                    System.out.println("+--------------------------------------+");
                    current = (Doctor) doctor.getNext();
                    counter++;
                }
            }
            else {
                int counter = 0;
                System.out.print("\f");
                System.out.println("+--------------------------------------+");
                System.out.println("|              Invoice                 |");
                System.out.println("+--------------------------------------+");
                Invoice current = (Invoice) invoice.getFirst();
                while (counter != dataFloor) {
                    current = (Invoice) invoice.getNext();
                    counter++;
                }
                while (current != null && counter < (10*currentPage)) {
                    System.out.println(" "+(counter+1)+" ] "+current.getInvoiceID()+" | "+current.getAmount()+" | "+current.getPaymentMethod());
                    System.out.println("+--------------------------------------+");
                    current = (Invoice) invoice.getNext();
                    counter++;
                }
            }
            
            // traversing through pages
            if (currentPage == 1) {
                System.out.println(" [S] Search - [V] Next Page - [H] Home");
                System.out.println("+--------------------------------------+");
            }
            else if (currentPage > 1) {
                System.out.println(" [S] Search - [C] Previous Page - [V] Next Page - [H] Home");
                System.out.println("+--------------------------------------+");
            }
            System.out.print(" Option : ");
            String option = inText.nextLine();
            System.out.println("+--------------------------------------+");
            if (option.equalsIgnoreCase("S")) {
                // to be determined
            }
            else if (option.equalsIgnoreCase("C")) {
                currentPage--;
                dataFloor-=10;
            }
            else if (option.equalsIgnoreCase("V")) {
                currentPage++;
                dataFloor+=10;
            }
            else if (option.equalsIgnoreCase("H")) {
                break;
            }
            else {
                try {
                    int key = Integer.parseInt(option);
                    getData(listCode, key);
                }
                catch (NumberFormatException e) {
                    System.out.println("+--------------------------------------+");
                    System.out.println("|           Invalid Key!               |");
                    System.out.println("+--------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
            }
        }
    }
    
    public static void getData(char listCode, int key) {
        while (true) {
            if (listCode == 'A' || listCode == 'a') {
                System.out.print("\f");
                System.out.println("+--------------------------------------+");
                System.out.println("|           Appointments               |");
                System.out.println("+--------------------------------------+");
                int counter = 1;
                Appointment current = (Appointment) appointment.getFirst();
                while (counter != key) {
                    current = (Appointment) appointment.getNext();
                    counter++;
                }
                System.out.println(current.toString2());
                System.out.println("+--------------------------------------+");
                Doctor currentDoc = (Doctor) searchDataByID(current.getDoctorID());
                System.out.println(currentDoc.toString2());
                System.out.println("+--------------------------------------+");
                Patient currentPat = (Patient) searchDataByID(current.getPatientID());
                System.out.println(currentPat.toString2());
                System.out.println("+--------------------------------------+");
                System.out.println("|    [1] Edit  [2] Delete  [3] Back    |");
                System.out.println("+--------------------------------------+");
                System.out.print(" Option : ");
                int option = inNum.nextInt();
                if (option == 1) {
                    editList(listCode);
                }
                else if (option == 2) {
                    deleteFromList(listCode);
                }
                else {
                    // to be determined
                    return;
                }
            }
            else if (listCode == 'B' || listCode == 'b') {
                System.out.print("\f");
                System.out.println("+--------------------------------------+");
                System.out.println("|             PATIENTS                 |");
                System.out.println("+--------------------------------------+");
                int counter = 1;
                Patient current = (Patient) patient.getFirst();
                while (counter != key) {
                    current = (Patient) patient.getNext();
                    counter++;
                }
                System.out.println(current.toString2());
                System.out.println("+--------------------------------------+");
                System.out.println("|      Press [Enter] to continue       |");
                System.out.println("+--------------------------------------+");
                String enter = inText.nextLine();
                return;
            }
            else if (listCode == 'C' || listCode == 'c') {
                System.out.print("\f");
                System.out.println("+--------------------------------------+");
                System.out.println("|              DOCTOR                  |");
                System.out.println("+--------------------------------------+");
                int counter = 1;
                Doctor current = (Doctor) doctor.getFirst();
                while (counter != key) {
                    current = (Doctor) doctor.getNext();
                    counter++;
                }
                System.out.println(current.toString2());
                System.out.println("+--------------------------------------+");
                System.out.println("|      Press [Enter] to continue       |");
                System.out.println("+--------------------------------------+");
                String enter = inText.nextLine();
                return;
            }
            else if (listCode == 'D' || listCode == 'd') {
                System.out.print("\f");
                System.out.println("+--------------------------------------+");
                System.out.println("|             INVOICE                  |");
                System.out.println("+--------------------------------------+");
                int counter = 1;
                Invoice current = (Invoice) invoice.getFirst();
                while (counter != key) {
                    current = (Invoice) invoice.getNext();
                    counter++;
                }
                System.out.println(current.toString2());
                System.out.println("+--------------------------------------+");
                Appointment currentApp = (Appointment) searchDataByID(current.getAppID());
                System.out.println(currentApp.toString2());
                System.out.println("+--------------------------------------+");
                System.out.println("|      Press [Enter] to continue       |");
                System.out.println("+--------------------------------------+");
                String enter = inText.nextLine();
                return;
            }
            else {
                // to be determined
            }
        }
    }
    
    public static Object searchDataByID(String key) {
        Appointment current1 = (Appointment) appointment.getFirst();
        while (current1 != null) {
            if (current1.getAppID().equals(key)) {
                return current1;
            }
            current1 = (Appointment) appointment.getNext();
        }
        Doctor current2 = (Doctor) doctor.getFirst();
        while (current2 != null) {
            if (current2.getDoctorID().equals(key)) {
                return current2;
            }
            current2 = (Doctor) doctor.getNext();
        }
        Patient current3 = (Patient) patient.getFirst();
        while (current3 != null) {
            if (current3.getPatientID().equals(key)) {
                return current3;
            }
            current3 = (Patient) patient.getNext();
        }
        return null;
    }
    
    public static Object search(char listCode, String key) {
        // to be determined
        return null;
    }
    
    public static void editList(char listCode) {
        System.out.println("+--------------------------------------+");
        System.out.println("|              EDIT LIST               |");
        System.out.println("+--------------------------------------+");
        System.out.print(" Press [Enter] to continue");
        String enter = inText.nextLine();
    }
    
    public static void deleteFromList(char listCode) {
        System.out.println("+--------------------------------------+");
        System.out.println("|          DELETE FROM LIST            |");
        System.out.println("+--------------------------------------+");
        System.out.print(" Press [Enter] to continue");
        String enter = inText.nextLine();
    }
}
