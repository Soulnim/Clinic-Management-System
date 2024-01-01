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
    static LinkedList service = new LinkedList();
    
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
                    String medID = data.nextToken();
                    String medName = data.nextToken();
                    String packDesc = data.nextToken();
                    String manufacturer = data.nextToken();
                    double price = Double.parseDouble(data.nextToken());
                    medicine.addLast(new Medicine(medID,medName,packDesc,manufacturer,price));
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
                fw.write(currentMed.rawData()+"\n");
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
            else if (listCode == 'D' || listCode =='d') {
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
            else if (listCode == 'E' || listCode =='e') {
                int counter = 0;
                System.out.print("\f");
                System.out.println("+--------------------------------------+");
                System.out.println("|              Medicine                |");
                System.out.println("+--------------------------------------+");
                Medicine current = (Medicine) medicine.getFirst();
                while (counter != dataFloor) {
                    current = (Medicine) medicine.getNext();
                    counter++;
                }
                while (current != null && counter < (10*currentPage)) {
                    System.out.println(" "+(counter+1)+" ] "+current.getMedicineID()+" | "+current.getPackagingDesc()+" | RM "+current.getPrice());
                    System.out.println("+--------------------------------------+");
                    current = (Medicine) medicine.getNext();
                    counter++;
                }
            }
            
            // traversing through pages
            if (currentPage == 1) {
                System.out.println(" [S] Search - [V] Next Page - [H] Home");
            }
            else if (currentPage > 1) {
                System.out.println(" [S] Search - [C] Previous Page - [V] Next Page - [H] Home");
            }
            System.out.println("+--------------------------------------+");
            System.out.println("|          [A] Add new data            |");
            System.out.println("+--------------------------------------+");
            System.out.print(" Option : ");
            String option = inText.nextLine();
            System.out.println("+--------------------------------------+");
            if (option.equalsIgnoreCase("S")) {
                search(listCode);
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
            else if (option.equalsIgnoreCase("A")) {
                addData(listCode);
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
    
    // 'ADD' PROCESS
    public static void addData(char listCode) {
        System.out.print("\f");
        System.out.println("+--------------------------------------+");
        System.out.println("|         Data added to list!          |");
        System.out.println("+--------------------------------------+");
        System.out.print(" Press [Enter] to continue");
        String enter = inText.nextLine();
        // to be modified
    }
    
    // GET SPECIFIC DATA
    public static void getData(char listCode, int key) {
        while (true) {
            if (listCode == 'A' || listCode == 'a') {
                System.out.print("\f");
                System.out.println("+--------------------------------------+");
                System.out.println("|           Appointments               |");
                System.out.println("+--------------------------------------+");
                int counter = 1;
                Appointment currentApp = (Appointment) appointment.getFirst();
                while (counter != key) {
                    currentApp = (Appointment) appointment.getNext();
                    counter++;
                }
                if (currentApp == null) {
                    System.out.println("|        Data is not found!            |");
                    System.out.println("+--------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                    break;
                }
                else {
                    System.out.println(currentApp.toString2());
                    System.out.println("+--------------------------------------+");
                    Doctor currentDoc = (Doctor) getDataByID(currentApp.getDoctorID());
                    System.out.println(currentDoc.toString2());
                    System.out.println("+--------------------------------------+");
                    Patient currentPat = (Patient) getDataByID(currentApp.getPatientID());
                    System.out.println(currentPat.toString2());
                    System.out.println("+--------------------------------------+");
                    System.out.println("|    [1] Edit  [2] Delete  [3] Back    |");
                    System.out.println("+--------------------------------------+");
                    System.out.print(" Option : ");
                    int option = inNum.nextInt();
                    if (option == 1) {
                        editList(listCode,currentApp,currentPat,currentDoc,null,null);
                    }
                    else if (option == 2) {
                        if (deleteFromList(listCode,currentApp,currentPat,currentDoc,null,null)) {
                            return;
                        }
                    }
                    else {
                        // to be determined
                        return;
                    }
                }
            }
            else if (listCode == 'B' || listCode == 'b') {
                System.out.print("\f");
                System.out.println("+--------------------------------------+");
                System.out.println("|             PATIENTS                 |");
                System.out.println("+--------------------------------------+");
                int counter = 1;
                Patient currentPat = (Patient) patient.getFirst();
                while (counter != key) {
                    currentPat = (Patient) patient.getNext();
                    counter++;
                }
                if (currentPat != null) {
                    System.out.println(currentPat.toString2());
                    System.out.println("+--------------------------------------+");
                    System.out.println("|    [1] Edit  [2] Delete  [3] Back    |");
                    System.out.println("+--------------------------------------+");
                    System.out.print(" Option : ");
                    int option = inNum.nextInt();
                    if (option == 1) {
                        editList(listCode,null,currentPat,null,null,null);
                    }
                    else if (option == 2) {
                        if (deleteFromList(listCode,null,currentPat,null,null,null)) {
                            return;
                        }
                    }
                    else {
                        // to be determined
                        return;
                    }
                }
                else {
                    System.out.println("|        Data is not found!            |");
                    System.out.println("+--------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                    break;
                }
            }
            else if (listCode == 'C' || listCode == 'c') {
                System.out.print("\f");
                System.out.println("+--------------------------------------+");
                System.out.println("|              DOCTOR                  |");
                System.out.println("+--------------------------------------+");
                int counter = 1;
                Doctor currentDoc = (Doctor) doctor.getFirst();
                while (counter != key) {
                    currentDoc = (Doctor) doctor.getNext();
                    counter++;
                }
                if (currentDoc != null) {
                    System.out.println(currentDoc.toString2());
                    System.out.println("+--------------------------------------+");
                    System.out.println("|    [1] Edit  [2] Delete  [3] Back    |");
                    System.out.println("+--------------------------------------+");
                    System.out.print(" Option : ");
                    int option = inNum.nextInt();
                    if (option == 1) {
                        editList(listCode,null,null, currentDoc,null,null);
                    }
                    else if (option == 2) {
                        if (deleteFromList(listCode,null,null,currentDoc,null,null)) {
                            return;
                        }
                    }
                    else {
                        // to be determined
                        return;
                    }
                }
                else {
                    System.out.println("|        Data is not found!            |");
                    System.out.println("+--------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                    break;
                }
            }
            else if (listCode == 'E' || listCode == 'e') {
                System.out.print("\f");
                System.out.println("+--------------------------------------+");
                System.out.println("|             MEDICINE                 |");
                System.out.println("+--------------------------------------+");
                int counter = 1;
                Medicine currentMed = (Medicine) medicine.getFirst();
                while (counter != key) {
                    currentMed = (Medicine) medicine.getNext();
                    counter++;
                }
                if (currentMed != null) {
                    System.out.println(currentMed.toString2());
                    System.out.println("+--------------------------------------+");
                    System.out.println("|    [1] Edit  [2] Delete  [3] Back    |");
                    System.out.println("+--------------------------------------+");
                    System.out.print(" Option : ");
                    int option = inNum.nextInt();
                    if (option == 1) {
                        editList(listCode,null,null,null,null,currentMed);
                    }
                    else if (option == 2) {
                        if (deleteFromList(listCode,null,null,null,null,currentMed)) {
                            return;
                        }
                    }
                    else {
                        // to be determined
                        return;
                    }
                }
                else {
                    System.out.println("|        Data is not found!            |");
                    System.out.println("+--------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                    break;
                }
            }
            else if (listCode == 'D' || listCode == 'd') {
                System.out.print("\f");
                System.out.println("+--------------------------------------+");
                System.out.println("|             INVOICE                  |");
                System.out.println("+--------------------------------------+");
                int counter = 1;
                Invoice currentInv = (Invoice) invoice.getFirst();
                while (counter != key) {
                    currentInv = (Invoice) invoice.getNext();
                    counter++;
                }
                if (currentInv != null) {
                    System.out.println(currentInv.toString2());
                    System.out.println("+--------------------------------------+");
                    Appointment currentApp = (Appointment) getDataByID(currentInv.getAppID());
                    System.out.println(currentApp.toString2());
                    System.out.println("+--------------------------------------+");
                    System.out.println("|    [1] Edit  [2] Delete  [3] Back    |");
                    System.out.println("+--------------------------------------+");
                    System.out.print(" Option : ");
                    int option = inNum.nextInt();
                    if (option == 1) {
                        editList(listCode,currentApp,null,null,currentInv,null);
                    }
                    else if (option == 2) {
                        if (deleteFromList(listCode,currentApp,null,null,currentInv,null)) {
                            return;
                        }
                    }
                    else {
                        // to be determined
                        return;
                    }
                }
                else {
                    System.out.println("|        Data is not found!            |");
                    System.out.println("+--------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                    break;
                }
            }
            else {
                // to be determined
            }
        }
    }
    
    public static Object getDataByID(String key) {
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
    
    public static Object search(char listCode) {
        while (true) {
            LinkedList dataMatched = new LinkedList();
            int totalMatched = 0;
            if (listCode == 'A' || listCode == 'a') {
                // to be determined
            }
            else if (listCode == 'B' || listCode == 'b') {
                // to be determined
            }
            else if (listCode == 'C' || listCode == 'c') {
                // to be determined
            }
            else if (listCode == 'D' || listCode == 'd') {
                // to be determined
            }
        }
    }
    
    public static void editList(char listCode, Appointment cA, Patient cP,Doctor cD,Invoice cI,Medicine cM) {
        while (true) {
            System.out.print("\f");
            System.out.println("+--------------------------------------+");
            System.out.println("|              EDIT DATA               |");
            System.out.println("+--------------------------------------+");
            if (listCode == 'A' || listCode == 'a') {
                System.out.println(cP.toString2());
                System.out.println("+--------------------------------------+");
                System.out.println(cP.toString2());
                System.out.println("+--------------------------------------+");
                System.out.println(" 1] Edit Date");
                System.out.println(" 2] Edit Time");
                System.out.println(" 3] Edit Type");
                System.out.println(" 4] Edit NRIC");
                System.out.println(" 5] Back");
                System.out.println("+--------------------------------------+");
                System.out.print(" Option : ");
                int option = inNum.nextInt();
                if (option == 1) {
                    System.out.println("+--------------------------------------+");
                    System.out.println("|      Date Edited                     |");
                    System.out.println("|      Press [Enter] to continue       |");
                    System.out.println("+--------------------------------------+");
                    String enter = inText.nextLine();
                }
                else if (option == 2) {
                    System.out.println("+--------------------------------------+");
                    System.out.println("|      Time edited                     |");
                    System.out.println("|      Press [Enter] to continue       |");
                    System.out.println("+--------------------------------------+");
                    String enter = inText.nextLine();
                }
                else if (option == 3) {
                    System.out.println("+--------------------------------------+");
                    System.out.println("|      Type edited                     |");
                    System.out.println("|      Press [Enter] to continue       |");
                    System.out.println("+--------------------------------------+");
                    String enter = inText.nextLine();
                }
                else if (option == 4) {
                    System.out.println("+--------------------------------------+");
                    System.out.println("|      NRIC edited                     |");
                    System.out.println("|      Press [Enter] to continue       |");
                    System.out.println("+--------------------------------------+");
                    String enter = inText.nextLine();
                }
                else if (option == 5) {
                    break;
                }
            }
            else if (listCode == 'B' || listCode == 'b') {
                System.out.println(cP.toString2());
                System.out.println("+--------------------------------------+");
                System.out.println(" 1] Edit NRIC");
                System.out.println(" 2] Edit Name");
                System.out.println(" 3] Back");
                System.out.println("+--------------------------------------+");
                System.out.print(" Option : ");
                int option = inNum.nextInt();
                if (option == 1) {
                    System.out.println("+--------------------------------------+");
                    System.out.println("|      NRIC Edited                     |");
                    System.out.println("|      Press [Enter] to continue       |");
                    System.out.println("+--------------------------------------+");
                    String enter = inText.nextLine();
                }
                else if (option == 2) {
                    System.out.println("+--------------------------------------+");
                    System.out.println("|      Name edited                     |");
                    System.out.println("|      Press [Enter] to continue       |");
                    System.out.println("+--------------------------------------+");
                    String enter = inText.nextLine();
                }
                else if (option == 3) {
                    break;
                }
            }
            else if (listCode == 'C' || listCode == 'c') {
                System.out.println(cD.toString2());
                System.out.println("+--------------------------------------+");
                System.out.println(" 1] Edit Name");
                System.out.println(" 2] Edit Phone Number");
                System.out.println(" 3] Back");
                System.out.println("+--------------------------------------+");
                System.out.print(" Option : ");
                int option = inNum.nextInt();
                if (option == 1) {
                    System.out.println("+--------------------------------------+");
                    System.out.println("|      Name Edited                     |");
                    System.out.println("|      Press [Enter] to continue       |");
                    System.out.println("+--------------------------------------+");
                    String enter = inText.nextLine();
                }
                else if (option == 2) {
                    System.out.println("+--------------------------------------+");
                    System.out.println("|      Phone Number Edited             |");
                    System.out.println("|      Press [Enter] to continue       |");
                    System.out.println("+--------------------------------------+");
                    String enter = inText.nextLine();
                }
                else if (option == 3) {
                    break;
                }
            }
            else if (listCode == 'D' || listCode == 'd') {
                System.out.println(cI.toString2());
                System.out.println("+--------------------------------------+");
                System.out.println(" 1] Edit Amount");
                System.out.println(" 2] Edit Payment Method");
                System.out.println(" 3] Back");
                System.out.println("+--------------------------------------+");
                System.out.print(" Option : ");
                int option = inNum.nextInt();
                if (option == 1) {
                    System.out.println("+--------------------------------------+");
                    System.out.println("|      Amount Edited                    |");
                    System.out.println("|      Press [Enter] to continue       |");
                    System.out.println("+--------------------------------------+");
                    String enter = inText.nextLine();
                }
                else if (option == 2) {
                    System.out.println("+--------------------------------------+");
                    System.out.println("|      Payment Method Edited           |");
                    System.out.println("|      Press [Enter] to continue       |");
                    System.out.println("+--------------------------------------+");
                    String enter = inText.nextLine();
                }
                else if (option == 3) {
                    break;
                }
            }
            else if (listCode == 'E' || listCode == 'e') {
                System.out.println(cM.toString2());
                System.out.println("+--------------------------------------+");
                System.out.println(" 1] Edit Packaging Desc");
                System.out.println(" 2] Edit Price");
                System.out.println(" 3] Back");
                System.out.println("+--------------------------------------+");
                System.out.print(" Option : ");
                int option = inNum.nextInt();
                if (option == 1) {
                    System.out.println("+--------------------------------------+");
                    System.out.println("|      PackDesc Edited                 |");
                    System.out.println("|      Press [Enter] to continue       |");
                    System.out.println("+--------------------------------------+");
                    String enter = inText.nextLine();
                }
                else if (option == 2) {
                    System.out.println("+--------------------------------------+");
                    System.out.println("|      Price Edited                    |");
                    System.out.println("|      Press [Enter] to continue       |");
                    System.out.println("+--------------------------------------+");
                    String enter = inText.nextLine();
                }
                else if (option == 3) {
                    break;
                }
            }
        }
    }
    
    public static boolean deleteFromList(char listCode,Appointment cA,Patient cP,Doctor cD,Invoice cI,Medicine cM) {
        System.out.print("\f");
        System.out.println("+--------------------------------------+");
        System.out.println("|   Are you sure you want to delete?:  |");
        System.out.println("+--------------------------------------+");
        if (listCode == 'A' || listCode == 'a') {
            System.out.println(cA.toString2());
            System.out.println("+--------------------------------------+");
            System.out.println(cP.toString2());
            System.out.println("+--------------------------------------+");
            System.out.println(cD.toString2());
            System.out.println("+--------------------------------------+");
            System.out.println("+          [Y] Yes, [N] No             +");
            System.out.println("+--------------------------------------+");
            System.out.print(" Option : ");
            char option = inChar.next().charAt(0);
            if (option == 'Y' || option == 'y') {

                LinkedList temp = new LinkedList();
                Appointment current = (Appointment) appointment.getFirst();
                while (current != null) {
                    if (!current.getAppID().equals(cA.getAppID())) {
                        temp.addLast(current);
                    }
                    current = (Appointment) appointment.getNext();
                }
                appointment.clear();
                current = (Appointment) temp.getFirst();
                while (current != null) {
                    appointment.addLast(current);
                    current = (Appointment) temp.getNext();
                }
                System.out.println("+--------------------------------------+");
                System.out.println("+        Data has been deleted         +");
                System.out.println("+--------------------------------------+");
                System.out.print(" Press [Enter] to continue");
                String enter = inText.nextLine();
                return true;
            }
            else {
                return false;
            }
        }
        else if (listCode == 'B' || listCode == 'b') {
            System.out.println(cP.toString2());
            System.out.println("+--------------------------------------+");
            System.out.println("+          [Y] Yes, [N] No             +");
            System.out.println("+--------------------------------------+");
            System.out.print(" Option : ");
            char option = inChar.next().charAt(0);
            if (option == 'Y' || option == 'y') {
                System.out.println("+--------------------------------------+");
                System.out.println("+        Data has been deleted         +");
                System.out.println("+--------------------------------------+");
                System.out.print(" Press [Enter] to continue");
                String enter = inText.nextLine();
                LinkedList temp = new LinkedList();
                Patient current = (Patient) patient.getFirst();
                while (current != null) {
                    if (!current.getPatientID().equals(cP.getPatientID())) {
                        temp.addLast(current);
                    }
                    current = (Patient) patient.getNext();
                }
                patient.clear();
                current = (Patient) temp.getFirst();
                while (current != null) {
                    patient.addLast(current);
                    current = (Patient) temp.getNext();
                }
                return true;
            }
            else {
                return false;
            }
        }
        else if (listCode == 'C' || listCode == 'c') {
            System.out.println(cD.toString2());
            System.out.println("+--------------------------------------+");
            System.out.println("+          [Y] Yes, [N] No             +");
            System.out.println("+--------------------------------------+");
            System.out.print(" Option : ");
            char option = inChar.next().charAt(0);
            if (option == 'Y' || option == 'y') {
                System.out.println("+--------------------------------------+");
                System.out.println("+        Data has been deleted         +");
                System.out.println("+--------------------------------------+");
                System.out.print(" Press [Enter] to continue");
                String enter = inText.nextLine();
                LinkedList temp = new LinkedList();
                Doctor current = (Doctor) doctor.getFirst();
                while (current != null) {
                    if (!current.getDoctorID().equals(cA.getDoctorID())) {
                        temp.addLast(current);
                    }
                    current = (Doctor) doctor.getNext();
                }
                doctor.clear();
                current = (Doctor) temp.getFirst();
                while (current != null) {
                    doctor.addLast(current);
                    current = (Doctor) temp.getNext();
                }
                return true;
            }
            else {
                return false;
            }
        }
        else if (listCode == 'D' || listCode == 'd') {
            System.out.println(cI.toString2());
            System.out.println("+--------------------------------------+");
            System.out.println("+          [Y] Yes, [N] No             +");
            System.out.println("+--------------------------------------+");
            System.out.print(" Option : ");
            char option = inChar.next().charAt(0);
            if (option == 'Y' || option == 'y') {
                System.out.println("+--------------------------------------+");
                System.out.println("+        Data has been deleted         +");
                System.out.println("+--------------------------------------+");
                System.out.print(" Press [Enter] to continue");
                String enter = inText.nextLine();
                LinkedList temp = new LinkedList();
                Invoice current = (Invoice) invoice.getFirst();
                while (current != null) {
                    if (!current.getInvoiceID().equals(cA.getInvoiceID())) {
                        temp.addLast(current);
                    }
                    current = (Invoice) invoice.getNext();
                }
                invoice.clear();
                current = (Invoice) temp.getFirst();
                while (current != null) {
                    invoice.addLast(current);
                    current = (Invoice) temp.getNext();
                }
                return true;
            }
            
            else {
                return false;
            }
        }
        else if (listCode == 'E' || listCode == 'e') {
            System.out.println(cM.toString2());
            System.out.println("+--------------------------------------+");
            System.out.println("+          [Y] Yes, [N] No             +");
            System.out.println("+--------------------------------------+");
            System.out.print(" Option : ");
            char option = inChar.next().charAt(0);
            if (option == 'Y' || option == 'y') {
                System.out.println("+--------------------------------------+");
                System.out.println("+        Data has been deleted         +");
                System.out.println("+--------------------------------------+");
                System.out.print(" Press [Enter] to continue");
                String enter = inText.nextLine();
                LinkedList temp = new LinkedList();
                Medicine current = (Medicine) medicine.getFirst();
                while (current != null) {
                    if (!current.getMedicineID().equals(cM.getMedicineID())) {
                        temp.addLast(current);
                    }
                    current = (Medicine) medicine.getNext();
                }
                medicine.clear();
                current = (Medicine) temp.getFirst();
                while (current != null) {
                    medicine.addLast(current);
                    current = (Medicine) temp.getNext();
                }
                return true;
            }
            
            else {
                return false;
            }
        }
        return false;
    }
}
