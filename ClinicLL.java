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
    static Scanner inText = new Scanner(System.in);
    static Scanner inChar = new Scanner(System.in);
    static Scanner inNum = new Scanner(System.in);
    public static void main(String args[]) {
        // Declare necessary variable
        LinkedList appointment = new LinkedList();
        LinkedList patient = new LinkedList();
        LinkedList doctor = new LinkedList();
        LinkedList invoice = new LinkedList();
        LinkedList medicine = new LinkedList();
        // Fetch data from clinic.txt
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("clinicdata.txt")));
            String data = br.readLine();
            while (data != null) {
                StringTokenizer st = new StringTokenizer(data, ";");
                String dataType = st.nextToken();
                if (dataType.equals("Appointment")) {
                    String appID = st.nextToken();
                    String patID = st.nextToken();
                    String date = st.nextToken();
                    String time = st.nextToken();
                    appointment.addLast(new Appointment(appID,patID,date,time));
                }
                else if (dataType.equals("Patient")) {
                    String patID = st.nextToken();
                    String NRIC = st.nextToken();
                    String name = st.nextToken();
                    int age = Integer.parseInt(st.nextToken());
                    patient.addLast(new Patient(patID,NRIC,name,age));
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
            }
        }
    }
    
    // Login system, return either true/false
    public static boolean login() { return true; }
    
    // Dashboard
    public static void dashboard() {
        // Dashboard
        System.out.println("\f");
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
            System.out.println("\f");
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
                viewAppointment();
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
            System.out.println("\f");
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
                viewPatient();
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
            System.out.println("\f");
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
                viewDoctor();
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
            System.out.println("\f");
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
                viewInvoice();
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
            System.out.println("\f");
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
                viewMedicine();
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
            System.out.println("\f");
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
            if (option_logout == 1) {
                viewAppointment();
            }
            else if (option_logout == 2) {
                addAppointment();
            }
        }
        else {
            System.out.println("\f");
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
        }
    }
    
    // Appointment's Processes
    public static void viewAppointment() {}
    public static void addAppointment() {}
    public static void editAppointment() {}
    public static void deleteAppointment() {}
    
    // Patient's Processes
    public static void viewPatient() {}
    public static void editPatient() {}
    
    // Doctor's Processes
    public static void viewDoctor() {}
    public static void editDoctor() {}
    
    // Invoice's Processes
    public static void viewInvoice() {}
    public static void editInvoice() {}
    
    // Medicine's Processes
    public static void viewMedicine() {}
    public static void addMedicine() {}
    public static void editMedicine() {}
    public static void deleteMedicine() {}
}

