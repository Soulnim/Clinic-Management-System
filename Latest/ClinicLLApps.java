import java.util.*;
import java.io.*;

public class ClinicLLApps
{
    static Scanner inText = new Scanner(System.in);
    static Scanner inChar = new Scanner(System.in);
    static Scanner inNum = new Scanner(System.in);
    
    static LinkedList appLL = new LinkedList();
    static LinkedList patLL = new LinkedList();
    static LinkedList docLL = new LinkedList();
    static LinkedList invLL = new LinkedList(); // to be determined
    
    static int sessionCode = 1; // 0-Terminate, 1-Idle, 2-Logged in
    static String SECURITY_KEY = "1"; // to be determined
    static String PASSWORD = "1"; // to be determined
    
    public static void main(String args[]) {
        // Fetch data from clinicdata.txt
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("clinicdata.txt")));
            String data = br.readLine();
            while (data != null) {
                StringTokenizer st = new StringTokenizer(data, ";");
                String dataType = st.nextToken();
                if (dataType.equals("APPOINTMENT")) {
                    String appID = st.nextToken();
                    String patID = st.nextToken();
                    String docID = st.nextToken();
                    String date = st.nextToken();
                    String time = st.nextToken();
                    String status = st.nextToken();
                    appLL.addLast(new Appointment(appID,patID,docID,date,time,status));
                }
                else if (dataType.equals("PATIENT")) {
                    String patID = st.nextToken();
                    String NRIC = st.nextToken();
                    String patName = st.nextToken();
                    patLL.addLast(new Patient(patID,NRIC,patName));
                }
                else if (dataType.equals("DOCTOR")) {
                    String docID = st.nextToken();
                    String name = st.nextToken();
                    String specialty = st.nextToken();
                    docLL.addLast(new Doctor(docID,name,specialty));
                }
                data = br.readLine();
            }
        }
        catch (Exception e) { System.err.println(e.getMessage()); }
        
        // MAIN PROCESSES
        while (sessionCode != 0) {
            if (sessionCode == 2) {
                dashboard(appLL,patLL,docLL);
            }
            else {
                login();
            }
        }
        
        // STORE Data back to clinicdata.txt
        try {
            FileWriter fw = new FileWriter("clinicdata.txt");
            Appointment appObj = (Appointment) appLL.getFirst();
            while (appObj != null) {
                fw.write(appObj.txtFormat()+"\n");
                appObj = (Appointment) appLL.getNext();
            }
            Patient patObj = (Patient) patLL.getFirst();
            while (patObj != null) {
                fw.write(patObj.txtFormat()+"\n");
                patObj = (Patient) patLL.getNext();
            }
            Doctor docObj = (Doctor) docLL.getFirst();
            while (docObj != null) {
                fw.write(docObj.txtFormat()+"\n");
                docObj = (Doctor) docLL.getNext();
            }
            fw.close();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    // LOGIN
    public static void login() {
        System.out.print("\f");
        System.out.println("+------------------------------------------+");
        System.out.println("|                 LOGIN                    |");
        System.out.println("+------------------------------------------+");
        System.out.print(" Enter security key ('0' to exit) : ");
        String secKey = inText.nextLine();
        if (secKey.equals("0")) {
            sessionCode = 0;
            System.out.print("\f");
            System.out.println("+------------------------------------------+");
            System.out.println("|           Session Terminated             |");
            System.out.println("+------------------------------------------+");
            return;
        }
        System.out.println("+------------------------------------------+");
        System.out.print(" Enter password : ");
        String pass = inText.nextLine();
        if (secKey.equals(SECURITY_KEY) && pass.equals(PASSWORD)) {
            sessionCode = 2;
            System.out.println("+------------------------------------------+");
            System.out.println("|         Logged in successfully!          |");
            System.out.println("+------------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
        }
        else {
            System.out.println("+------------------------------------------+");
            System.out.println("|     Invalid security key or password!    |");
            System.out.println("+------------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
        }
    }
    
    public static void dashboard(LinkedList appList,LinkedList patList,LinkedList docList) {
        System.out.print("\f");
        System.out.println("+------------------------------------------+");
        System.out.println("|                MAIN MENU                 |");
        System.out.println("+------------------------------------------+");
        System.out.println("|     A] Schedule New Appointment          |");
        System.out.println("|     B] Manage Appointment                |");
        System.out.println("|     C] Manage Patient                    |");
        System.out.println("|     D] Manage Invoice                    |");
        System.out.println("|     E] Manage Doctor                     |");
        System.out.println("|     F] Exit                              |");
        System.out.println("+------------------------------------------+");
        System.out.print(" Option : ");
        char option = inChar.next().charAt(0);
        if (option == 'A' || option == 'a') {
            addData(appList,patList);
        }
        else if (option == 'B' || option == 'b') {
            displayList(appList,patList);
        }
        else if (option == 'C' || option == 'c') {
            displayList(patList,null);
        }
        else if (option == 'D' || option == 'd') {
            // to be determined
        }
        else if (option == 'E' || option == 'e') {
            displayList(docList,null);
        }
        else if (option == 'F' || option == 'f') {
            sessionCode = 1;
        }
        else {
            System.out.println("+------------------------------------------+");
            System.out.println("|               Invalid key!               |");
            System.out.println("+------------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
        }
    }
    
    public static void displayList(LinkedList list, LinkedList list2) {
        int page = 1;
        int floor = 0;
        boolean showCompleted = true;
        while (true) {
            // Check for list type
            Object object = (Object) list.getFirst();
            if (object != null) {
                if (object instanceof Appointment) {
                    System.out.print("\f");
                    System.out.println("+------------------------------------------+");
                    System.out.println("|             APPOINTMENT LIST             |");
                    System.out.println("+------------------------------------------+");
                }
                else if (object instanceof Patient) {
                    System.out.print("\f");
                    System.out.println("+------------------------------------------+");
                    System.out.println("|               PATIENT LIST               |");
                    System.out.println("+------------------------------------------+");
                }
                else if (object instanceof Doctor) {
                    System.out.print("\f");
                    System.out.println("+------------------------------------------+");
                    System.out.println("|                DOCTOR LIST               |");
                    System.out.println("+------------------------------------------+");
                }
            }
            else {
                System.out.print("\f");
                System.out.println("+------------------------------------------+");
                System.out.println("|              List is empty!              |");
                System.out.println("+------------------------------------------+");
                System.out.print(" Press [Enter] to continue");
                String enter = inText.nextLine();
                return;
            }
            
            int counter = 0;
            // Check if the list is AppLL
            object = (Object) list.getFirst();
            if (object instanceof Appointment) {
                Appointment appObj = (Appointment) object;
                while (counter != floor) {
                    appObj = (Appointment) list.getNext();
                    counter++;
                }
                while (appObj != null && counter < (page*10)) {
                    if (showCompleted) {
                        System.out.println(" "+(counter+1)+"] "+appObj.toString());
                        System.out.println("+------------------------------------------+");
                    }
                    else {
                        if (appObj.getStatus().equals("Pending")) {
                            System.out.println(" "+(counter+1)+"] "+appObj.toString());
                            System.out.println("+------------------------------------------+");
                        }
                    }
                    appObj = (Appointment) list.getNext();
                    counter++;
                }
            }
            else {
                while (counter != floor) {
                    object = (Object) list.getNext();
                    counter++;
                }
                while (object != null && counter < (page*10)) {
                    System.out.println(" "+(counter+1)+"] "+object.toString());
                    object = (Object) list.getNext();
                    counter++;
                    System.out.println("+------------------------------------------+");
                }
            }
            // Check total page
            System.out.println(" Enter index (1..) to choose -");
            if (page == 1) {
                if (list.getFirst() instanceof Appointment) {
                    if (showCompleted) {
                        System.out.println(" [V] Next , [H] Home, [K] Hide \'Completed\'");
                    }
                    else {
                        System.out.println(" [V] Next , [H] Home, [K] Show \'Completed\'");
                    }
                }
                else {
                    System.out.println(" [V] Next , [H] Home");
                }
            }
            else {
                System.out.println(" [C] Previous , [V] Next , [H] Home");
            }
            System.out.println("+------------------------------------------+ Page : "+page);
            System.out.println("|             [S] Search Data              |");
            System.out.println("+------------------------------------------+");
            // Determine wether its is addable or not
            System.out.print(" Option : ");
            String option = inText.nextLine();
            if (option.equalsIgnoreCase("V")) {
                page++;
                floor+=10;
            }
            else if (option.equalsIgnoreCase("C")) {
                if (page-1<1) {
                    System.out.println("+------------------------------------------+");
                    System.out.println("|            Page limit reached!           |");
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
                else {
                    page--;
                    floor-=10;
                }
            }
            else if (option.equalsIgnoreCase("H")) {
                break;
            }
            else if (option.equalsIgnoreCase("K")) {
                if (list.getFirst() instanceof Appointment) {
                    if (showCompleted) {
                        showCompleted = false;
                    }
                    else {
                        showCompleted = true;
                    }
                }
            }
            else if (option.equalsIgnoreCase("S")) {
                searchData(list,list2);
            }
            else {
                try {
                    int key = Integer.parseInt(option);
                    if (key > 0 && key <= counter) {
                        displayData(list, key);
                    }
                    else {
                        System.out.println("+------------------------------------------+");
                        System.out.println("|               Invalid key!               |");
                        System.out.println("+------------------------------------------+");
                        System.out.print(" Press [Enter] to continue");
                        String enter = inText.nextLine();
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println("+------------------------------------------+");
                    System.out.println("|               Invalid key!               |");
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
            }
        }
    }
    
    // DISPLAY SPECIFIC DATA FROM LIST
    public static void displayData(LinkedList list, int key) {
        int counter = 1;
        Object object = (Object) list.getFirst();
        while (counter != key) {
            object = (Object) list.getNext();
            counter++;
        }
        while (true) {
            if (object instanceof Appointment) {
                Appointment appObj = (Appointment) object;
                Patient patObj = (Patient) getObjectByID(appObj.getPatID());
                Doctor docObj = (Doctor) getObjectByID(appObj.getDocID());
                System.out.print("\f");
                System.out.println("+------------------------------------------+");
                System.out.println("|             APPOINTMENT DATA             |");
                System.out.println("+------------------------------------------+");
                System.out.println(appObj.toStringFormatted());
                System.out.println("+------------------------------------------+");
                System.out.println(patObj.toStringFormatted());
                System.out.println("+------------------------------------------+");
                System.out.println(docObj.toStringFormatted());
                System.out.println("+------------------------------------------+");
                System.out.println("|     [A] Edit, [B] Delete, [C] Back       |");
                System.out.println("+------------------------------------------+");
                System.out.print(" Option : ");
                char option = inChar.next().charAt(0);
                if (option == 'A' || option == 'a') {
                    editData(list,key);
                }
                else if (option == 'B' || option == 'b') {
                    if (deleteData(list,key)) {
                        break;
                    }
                }
                else if (option == 'C' || option == 'c') {
                    break;
                }
                else {
                    // display error
                }
            }
            else if (object instanceof Patient) {
                Patient patObj = (Patient) object;
                System.out.print("\f");
                System.out.println("+------------------------------------------+");
                System.out.println("|               PATIENT DATA               |");
                System.out.println("+------------------------------------------+");
                System.out.println(" "+patObj.toStringFormatted());
                System.out.println("+------------------------------------------+");
                System.out.println("|            [A] Edit, [C] Back            |");
                System.out.println("+------------------------------------------+");
                System.out.print(" Option : ");
                char option = inChar.next().charAt(0);
                if (option == 'A' || option == 'a') {
                    editData(list,key);
                }
                else if (option == 'C' || option == 'c') {
                    break;
                }
                else {
                    System.out.println("+------------------------------------------+");
                    System.out.println("|               Invalid key!               |");
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
            }
            else if (object instanceof Doctor) {
                Doctor docObj = (Doctor) object;
                System.out.print("\f");
                System.out.println("+------------------------------------------+");
                System.out.println("|               DOCTOR DATA                |");
                System.out.println("+------------------------------------------+");
                System.out.println(" "+docObj.toStringFormatted());
                System.out.println("+------------------------------------------+");
                System.out.println("|            [A] Edit, [C] Back            |");
                System.out.println("+------------------------------------------+");
                System.out.print(" Option : ");
                char option = inChar.next().charAt(0);
                if (option == 'A' || option == 'a') {
                    editData(list,key);
                }
                else if (option == 'C' || option == 'c') {
                    break;
                }
                else {
                    System.out.println("+------------------------------------------+");
                    System.out.println("|               Invalid key!               |");
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
            }
        }
    }
    
    // ADD DATA TO LIST
    public static void addData(LinkedList list, LinkedList list2) {
        if (list.getFirst() instanceof Appointment) {
            System.out.print("\f");
            System.out.println("+------------------------------------------+");
            System.out.println("|            ADD NEW APPOINTMENT           |");
            System.out.println("+------------------------------------------+");
            Patient pat = new Patient();; // placeholder var
            Doctor doc = new Doctor(); // placeholder var
            System.out.print(" NRIC : ");
            String NRIC = inText.nextLine();
            if (!patientIsExist(list2,NRIC)) {
                System.out.print(" Patient Name : ");
                String patName = inText.nextLine();
                pat = new Patient("P"+generateID(list),NRIC,patName);
                patLL.addLast(pat);
            } else {
                Patient patObj = (Patient) list2.getFirst();
                while (patObj != null) {
                    if (patObj.getNRIC().equals(NRIC)) {
                        pat = patObj;
                    }
                    patObj = (Patient) list2.getNext();
                }
                System.out.println(" [Identical NRIC found!]");
                System.out.println(" Patient Name : "+pat.getPatName());
            }
            System.out.print(" Date (DD/MM/YYYY) : ");
            String date = inText.nextLine();
            System.out.print(" Time (24-hours format) : ");
            String time = inText.nextLine();
            while (true) {
                // Choose doctor
                int counter = 0;
                System.out.print("\f");
                System.out.println("+------------------------------------------+");
                System.out.println("|      Choose a doctor to be assigned :    |");
                System.out.println("+------------------------------------------+");
                Doctor docObj = (Doctor) docLL.getFirst();
                while (docObj != null) {
                    System.out.println(" "+(counter+1)+"]"+docObj.toStringFormatted());
                    docObj = (Doctor) docLL.getNext();
                    counter++;
                    System.out.println("+------------------------------------------+");
                }
                System.out.print(" Option : ");
                int option = inNum.nextInt();
                if (option <= counter && option >= 0) {
                    int counter2 = 1;
                    docObj = (Doctor) docLL.getFirst();
                    while (counter2 < option) {
                        docObj = (Doctor) docLL.getNext();
                        counter2++;
                    }
                    doc = docObj;
                    break;
                }
                else {
                    System.out.println("+------------------------------------------+");
                    System.out.println("|               Invalid key!               |");
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
            }
            System.out.print("\f");
            System.out.println("+------------------------------------------+");
            System.out.println("|            ADD NEW APPOINTMENT           |");
            System.out.println("+------------------------------------------+");
            System.out.println(" Date : "+date);
            System.out.println(" Time : "+time);
            System.out.println("+------------------------------------------+");
            System.out.println(pat.toStringFormatted());
            System.out.println("+------------------------------------------+");
            System.out.println(doc.toStringFormatted());
            // add to list
            list.addLast(new Appointment("A"+generateID(list),pat.getPatID(),doc.getDocID(),date,time,"Pending"));
            
            System.out.println("+------------------------------------------+");
            System.out.println("|           Data has been added!           |");
            System.out.println("+------------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
        }
        else if (list.getFirst() instanceof Doctor) {
            // 
        }
    }
    
    // GENERATE RANDOM NUMBER FOR ID
    public static int generateID(LinkedList list) {
        while (true) {
            if (list.getFirst() instanceof Appointment) {
                boolean isExist = false;
                Random rand = new Random();
                int randInt = rand.nextInt(100);
                Appointment appObj = (Appointment) list.getFirst();
                while (appObj != null) {
                    int getID = Integer.parseInt(appObj.getAppID().substring(1));
                    if (getID == randInt) {
                        isExist = true;
                    }
                    appObj = (Appointment) list.getNext();
                }
                if (isExist == false) {
                    return randInt;
                }
            }
            else if (list.getFirst() instanceof Patient) {
                boolean isExist = false;
                Random rand = new Random();
                int randInt = rand.nextInt(100);
                Patient patObj = (Patient) list.getFirst();
                while (patObj != null) {
                    int getID = Integer.parseInt(patObj.getPatID().substring(1));
                    if (getID == randInt) {
                        isExist = true;
                    }
                    patObj = (Patient) list.getNext();
                }
                if (isExist == false) {
                    return randInt;
                }
            }
        }
    }
    
    // Check wether patient is exist or not
    public static boolean patientIsExist(LinkedList list,String NRIC) {
        Patient patObj = (Patient) list.getFirst();
        while (patObj != null) {
            if (patObj.getNRIC().equals(NRIC)) {
                return true;
            }
            patObj = (Patient) list.getNext();
        }
        return false;
    }
    
    // EDIT DATA IN LIST
    public static void editData(LinkedList list, int key) {
        int counter = 1;
        Object object = (Object) list.getFirst();
        while (counter != key) {
            object = (Object) list.getNext();
            counter++;
        }
        while (true) {
            if (object instanceof Appointment) {
                Appointment appObj = (Appointment) object;
                Doctor docObj = (Doctor) getObjectByID(appObj.getDocID());
                System.out.print("\f");
                System.out.println("+------------------------------------------+");
                System.out.println("|           EDIT APPOINTMENT DATA          |");
                System.out.println("+------------------------------------------+");
                System.out.println(" A] Date : "+appObj.getDate());
                System.out.println(" B] Time : "+appObj.getTime());
                System.out.println(" C] Assigned Doctor : "+docObj.getDocName());
                System.out.println(" E] Back");
                System.out.println("+------------------------------------------+");
                System.out.println(" Option : ");
                char option = inChar.next().charAt(0);
                if (option == 'A' || option =='a') {
                    System.out.println(" Current date : "+appObj.getDate());
                    System.out.print(" New date (DD/MM/YYYY) : ");
                    String newDate = inText.nextLine();
                    appObj.setDate(newDate);
                    System.out.println("+------------------------------------------+");
                    System.out.println("|           Data has been edited!          |");
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
                else if (option == 'B' || option == 'b') {
                    System.out.println(" Current time : "+appObj.getTime());
                    System.out.print(" New date (24-hours format) : ");
                    String newTime = inText.nextLine();
                    appObj.setTime(newTime);
                    System.out.println("+------------------------------------------+");
                    System.out.println("|           Data has been edited!          |");
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
                else if (option == 'C' || option == 'c') {
                    while (true) {
                        // Choose doctor
                        int countDoc = 0;
                        System.out.print("\f");
                        System.out.println("+------------------------------------------+");
                        System.out.println("|      Choose a doctor to be assigned :    |");
                        System.out.println("+------------------------------------------+");
                        Doctor currentDoc = (Doctor) docLL.getFirst();
                        while (currentDoc != null) {
                            System.out.println(" "+(countDoc+1)+"] "+currentDoc.toStringFormatted());
                            currentDoc = (Doctor) docLL.getNext();
                            countDoc++;
                            System.out.println("+------------------------------------------+");
                        }
                        System.out.print(" Option : ");
                        int optionDoc = inNum.nextInt();
                        if (optionDoc <= countDoc && optionDoc >= 0) {
                            int counter2 = 1;
                            currentDoc = (Doctor) docLL.getFirst();
                            while (counter2 < optionDoc) {
                                currentDoc = (Doctor) docLL.getNext();
                                counter2++;
                            }
                            appObj.setDocID(currentDoc.getDocID());
                            break;
                        }
                        else {
                            System.out.println("+------------------------------------------+");
                            System.out.println("|               Invalid key!               |");
                            System.out.println("+------------------------------------------+");
                            System.out.print(" Press [Enter] to continue");
                            String enter = inText.nextLine();
                        }
                    }
                }
                else {
                    break;
                }
            }
            else if (object instanceof Patient) {
                Patient patObj = (Patient) object;
                System.out.print("\f");
                System.out.println("+------------------------------------------+");
                System.out.println("|             EDIT PATIENT DATA            |");
                System.out.println("+------------------------------------------+");
                System.out.println(" A] NRIC : "+patObj.getNRIC());
                System.out.println(" B] Name : "+patObj.getPatName());
                System.out.println(" C] Back");
                System.out.println("+------------------------------------------+");
                System.out.println(" Option : ");
                char option = inChar.next().charAt(0);
                if (option == 'A' || option =='a') {
                    System.out.println(" Current NRIC : "+patObj.getNRIC());
                    System.out.print(" New NRIC : ");
                    String newNRIC = inText.nextLine();
                    patObj.setNRIC(newNRIC);
                    System.out.println("+------------------------------------------+");
                    System.out.println("|           Data has been edited!          |");
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
                else if (option == 'B' || option == 'b') {
                    System.out.println(" Current name : "+patObj.getPatName());
                    System.out.print(" New name : ");
                    String newName = inText.nextLine();
                    patObj.setPatName(newName);
                    System.out.println("+------------------------------------------+");
                    System.out.println("|           Data has been edited!          |");
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
                else {
                    break;
                }
            }
            else if (object instanceof Doctor) {
                Doctor docObj = (Doctor) object;
                System.out.print("\f");
                System.out.println("+------------------------------------------+");
                System.out.println("|            EDIT DOCTOR'S DATA            |");
                System.out.println("+------------------------------------------+");
                System.out.println(" A] Name : "+docObj.getDocName());
                System.out.println(" B] Specialty : "+docObj.getSpecialty());
                System.out.println(" C] Back");
                System.out.println("+------------------------------------------+");
                System.out.println(" Option : ");
                char option = inChar.next().charAt(0);
                if (option == 'A' || option =='a') {
                    System.out.println(" Current name : "+docObj.getDocName());
                    System.out.print(" New name : ");
                    String newName = inText.nextLine();
                    docObj.setDocName(newName);
                    System.out.println("+------------------------------------------+");
                    System.out.println("|           Data has been edited!          |");
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
                else if (option == 'B' || option == 'b') {
                    System.out.println(" Current specialty : "+docObj.getSpecialty());
                    System.out.print(" New specialty : ");
                    String newSpec = inText.nextLine();
                    docObj.setSpecialty(newSpec);
                    System.out.println("+------------------------------------------+");
                    System.out.println("|           Data has been edited!          |");
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
                else {
                    break;
                }
            }
        }
    }
    
    // DELETE DATA FROM LIST
    public static boolean deleteData(LinkedList list, int key) {
        int counter = 1;
        Object object = (Object) list.getFirst();
        while (counter != key) {
            object = (Object) list.getNext();
            counter++;
        }
        System.out.print("\f");
        System.out.println("+------------------------------------------+");
        System.out.println("|                  Delete?                 |");
        System.out.println("+------------------------------------------+");
        if (object instanceof Appointment) {
            Appointment appObj = (Appointment) object;
            System.out.println(" "+appObj.toStringFormatted());
        }
        else if (object instanceof Patient) {
            // to be determined
        }
        else if (object instanceof Doctor) {
            // to be determined
        }
        System.out.println("+------------------------------------------+");
        System.out.println("|              [Y] Yes, [N] No             |");
        System.out.println("+------------------------------------------+");
        System.out.print(" Option : ");
        char option = inChar.next().charAt(0);
        if (option == 'Y' || option == 'y') {
            counter = 1;
            LinkedList temp = new LinkedList();
            Object currentObj = (Object) list.getFirst();
            while (currentObj != null) {
                if (counter != key) {
                    temp.addLast(currentObj);
                }
                counter++;
                currentObj = (Object) list.getNext();
            }
            list.clear();
            currentObj = (Object) temp.getFirst();
            while (currentObj != null) {
                list.addLast(currentObj);
                currentObj = (Object) temp.getNext();
            }
            System.out.println("+------------------------------------------+");
            System.out.println("|           Data has been deleted!         |");
            System.out.println("+------------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
            return true;
        }
        else {
            return false;
        }
    }
    
    // Getting specific data by ID
    public static Object getObjectByID(String id) {
        if (id.substring(0,1).equals("A")) {
            Appointment appObj = (Appointment) appLL.getFirst();
            while (appObj != null) {
                if (appObj.getAppID().equals(id)) {
                    return appObj;
                }
                appObj = (Appointment) appLL.getNext();
            }
        }
        else if (id.substring(0,1).equals("P")) {
            Patient patObj = (Patient) patLL.getFirst();
            while (patObj != null) {
                if (patObj.getPatID().equals(id)) {
                    return patObj;
                }
                patObj = (Patient) patLL.getNext();
            }
        }
        else if (id.substring(0,1).equals("D")) {
            Doctor docObj = (Doctor) docLL.getFirst();
            while (docObj != null) {
                if (docObj.getDocID().equals(id)) {
                    return docObj;
                }
                docObj = (Doctor) docLL.getNext();
            }
        }
        return null;
    }
    
    // SEARCH FOR DATA IN LIST
    public static void searchData(LinkedList list,LinkedList list2) {
        while (true) {
            System.out.print("\f");
            System.out.println("+------------------------------------------+");
            System.out.println("|               SEARCH DATA                |");
            System.out.println("+------------------------------------------+");
            System.out.print(" Enter keyword ('0' to back) : ");
            String keyword = inText.nextLine();
            System.out.println("+------------------------------------------+");
            if (keyword.equals("0")) {
                break;
            }
            while (true) {
                System.out.print("\f");
                System.out.println("+------------------------------------------+");
                System.out.println("|               SEARCH DATA                |");
                System.out.println("+------------------------------------------+");
                int counter = 1;
                int countFound = 0;
                LinkedList keyFound = new LinkedList();
                if (list.getFirst() instanceof Appointment) {
                    Appointment appObj = (Appointment) list.getFirst();
                    while (appObj != null) {
                        if (appObj.getDate().equalsIgnoreCase(keyword) || appObj.getTime().equalsIgnoreCase(keyword) ||
                            appObj.getAppID().equalsIgnoreCase(keyword) || appObj.getStatus().equalsIgnoreCase(keyword)) {
                            System.out.println(" "+(countFound+1)+"] "+appObj.toString()); 
                            System.out.println("+------------------------------------------+");
                            keyFound.addLast(counter);
                            countFound++;
                        }
                        counter++;
                        appObj = (Appointment) list.getNext();
                    }
                }
                else if (list.getFirst() instanceof Patient) {
                    Patient patObj = (Patient) list.getFirst();
                    while (patObj != null) {
                        if (patObj.getPatID().equalsIgnoreCase(keyword) || patObj.getNRIC().equalsIgnoreCase(keyword) ||
                            patObj.getPatName().equalsIgnoreCase(keyword)) {
                            System.out.println(" "+(countFound+1)+"] "+patObj.toString()); 
                            System.out.println("+------------------------------------------+");
                            keyFound.addLast(counter);
                            countFound++;
                        }
                        counter++;
                        patObj = (Patient) list.getNext();
                    }
                }
                else if (list.getFirst() instanceof Doctor) {
                    Doctor docObj = (Doctor) list.getFirst();
                    while (docObj != null) {
                        if (docObj.getDocID().equalsIgnoreCase(keyword) || docObj.getDocName().equalsIgnoreCase(keyword) ||
                            docObj.getSpecialty().equalsIgnoreCase(keyword)) {
                            System.out.println(" "+(countFound+1)+"] "+docObj.toString()); 
                            System.out.println("+------------------------------------------+");
                            keyFound.addLast(counter);
                            countFound++;
                        }
                        counter++;
                        docObj = (Doctor) list.getNext();
                    }
                }
                
                if (countFound == 0) {
                    System.out.println(" No data matches with the keyword : "+keyword); 
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                    break;
                }
                else {
                    if (countFound == 1) {
                        System.out.println(" A data matches with the keyword : "+keyword);
                    }
                    else {
                        System.out.println(" "+countFound+" data matches with the keyword : "+keyword);
                    }
                    System.out.print(" Enter number to select ('0' to back) : ");
                    int num = inNum.nextInt();
                    if (num == 0) {
                        break;
                    }
                    else if (num > 0 && num <= countFound+1) {
                        int counter2 = 1;
                        int realNum = (int) keyFound.getFirst();
                        while (counter2 != num) {
                            realNum = (int) keyFound.getNext();
                            counter2++;
                        }
                        displayData(list,realNum);
                    }
                    else {
                        System.out.println("+------------------------------------------+");
                        System.out.println("|               Invalid key!               |");
                        System.out.println("+------------------------------------------+");
                        System.out.print(" Press [Enter] to continue");
                        String enter = inText.nextLine();
                    }
                }
            }
        }
    }
    
    // PAUSE, FOR DEBUGGING - WILL BE DELETED SOON
    public static void pause() {
        System.out.println("+------------------------------------------+");
        System.out.println("|                  PAUSED!                 |");
        System.out.println("+------------------------------------------+");
        System.out.print(" Press [Enter] to continue");
        String enter = inText.nextLine();
    }
}
