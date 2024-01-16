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
    static LinkedList invLL = new LinkedList();
    
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
                    int slot = Integer.parseInt(st.nextToken());
                    String category = st.nextToken();
                    appLL.addLast(new Appointment(appID,patID,docID,date,slot,category));
                }
                else if (dataType.equals("PATIENT")) {
                    String patID = st.nextToken();
                    String NRIC = st.nextToken();
                    String patName = st.nextToken();
                    String patPhone = st.nextToken();
                    patLL.addLast(new Patient(patID,NRIC,patName,patPhone));
                }
                else if (dataType.equals("DOCTOR")) {
                    String docID = st.nextToken();
                    String name = st.nextToken();
                    String specialty = st.nextToken();
                    String docPhone = st.nextToken();
                    docLL.addLast(new Doctor(docID,name,specialty,docPhone));
                    System.out.println("Doc");
                }
                else if (dataType.equals("INVOICE")) {
                    String invID = st.nextToken();
                    Patient pat = (Patient) getObjectByID(st.nextToken(),patLL);
                    Doctor doc = (Doctor) getObjectByID(st.nextToken(),docLL);
                    int payMethod = Integer.parseInt(st.nextToken());
                    String payStatus = st.nextToken();
                    // read new line to get appointment details
                    data = br.readLine();
                    StringTokenizer st2 = new StringTokenizer(data, ";");
                    String type = st2.nextToken(); // Ignore
                    String appID = st2.nextToken();
                    String patID = st2.nextToken();
                    String docID = st2.nextToken();
                    String date = st2.nextToken();
                    int slot = Integer.parseInt(st2.nextToken());
                    String category = st2.nextToken();
                    Appointment app = new Appointment(appID,patID,docID,date,slot,category);
                    invLL.addLast(new Invoice(invID,app,pat,doc,payMethod,payStatus));
                }
                data = br.readLine();
            }
        }
        catch (Exception e) { System.err.println(e.getMessage()); }
        
        // MAIN PROCESSES
        while (sessionCode != 0) {
            if (sessionCode == 2) {
                dashboard();
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
            Invoice invObj = (Invoice) invLL.getFirst();
            while (invObj != null) {
                fw.write(invObj.txtFormat()+"\n");
                invObj = (Invoice) invLL.getNext();
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
    
    public static void dashboard() {
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
            addData(appLL,patLL);
        }
        else if (option == 'B' || option == 'b') {
            displayList(appLL,patLL);
        }
        else if (option == 'C' || option == 'c') {
            displayList(patLL,null);
        }
        else if (option == 'D' || option == 'd') {
            displayList(invLL,null);
        }
        else if (option == 'E' || option == 'e') {
            displayList(docLL,null);
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
        
        // Filters - to be determined
        
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
                else if (object instanceof Invoice) {
                    System.out.print("\f");
                    System.out.println("+------------------------------------------+");
                    System.out.println("|               INVOICE LIST               |");
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
            if (object instanceof Appointment) {
                // Store patient data in temporary LL
                LinkedList tempPatLL = new LinkedList();
                Patient currPat = (Patient) patLL.getFirst();
                while (currPat != null) {
                    tempPatLL.addLast(currPat);
                    currPat = (Patient) patLL.getNext();
                }
                // Proceed
                Appointment appObj = (Appointment) list.getFirst();
                while (counter != floor) {
                    appObj = (Appointment) list.getNext();
                    counter++;
                }
                while (appObj != null && counter < (page*10)) {
                    String patID = appObj.getPatID();
                    Patient pat = (Patient) getObjectByID(patID,tempPatLL);
                    System.out.println(" "+(counter+1)+"] NRIC : "+pat.getNRIC()+appObj.toString());
                    appObj = (Appointment) list.getNext();
                    counter++;
                    System.out.println("+------------------------------------------+");
                }
            }
            else {
                object = (Object) list.getFirst();
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
                System.out.println(" [V] Next , [H] Home");
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
                Patient patObj = (Patient) getObjectByID(appObj.getPatID(),patLL);
                Doctor docObj = (Doctor) getObjectByID(appObj.getDocID(),docLL);
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
                System.out.println("|          [V] Verify Appointment          |");
                System.out.println("+------------------------------------------+");
                System.out.print(" Option : ");
                char option = inChar.next().charAt(0);
                System.out.println("+------------------------------------------+");
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
                else if (option == 'V' || option == 'v') {
                    System.out.print(" Are you sure? (Y/N) : ");
                    char optVer = inChar.next().charAt(0);
                    if (optVer == 'Y' || optVer == 'y') {
                        // Appointment has been verified
                        // Invoice is issued, and data has been removed
                        // All data has been transferred to invoice
                        Invoice inv = new Invoice("I"+generateID(invLL),appObj,patObj,docObj,0,"Not set");
                        invLL.addFirst(inv);
                        // Remove appointment
                        LinkedList temp = new LinkedList();
                        Appointment currApp = (Appointment) appLL.getFirst();
                        while (currApp != null) {
                            if (currApp.getAppID().equals(appObj.getAppID())) {
                                // do nothing
                            }
                            else {
                                temp.addLast(currApp);
                            }
                            currApp = (Appointment) appLL.getNext();
                        }
                        appLL.clear();
                        currApp = (Appointment) temp.getFirst();
                        while (currApp != null) {
                            appLL.addLast(currApp);
                            currApp = (Appointment) temp.getNext();
                        }
                        
                        System.out.println("+------------------------------------------+");
                        System.out.println("|     Appointment has been verified.       |");
                        System.out.println("|           New invoice added!             |");
                        System.out.println("+------------------------------------------+");
                        System.out.print(" Press [Enter] to continue");
                        String enter = inText.nextLine();
                        break;
                    }
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
            else if (object instanceof Invoice) {
                Invoice invObj = (Invoice) object;
                System.out.print("\f");
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|                      INVOICE DATA                       |");
                System.out.println("+---------------------------------------------------------+");
                System.out.println(" "+invObj.toStringFormatted());
                if (invObj.getPayStatus().equals("Pending")) {
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                  [A] Verify, [C] Back                   |");
                    System.out.println("+---------------------------------------------------------+");
                }
                else {
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                        [C] Back                         |");
                    System.out.println("+---------------------------------------------------------+");
                }
                System.out.print(" Option : ");
                char option = inChar.next().charAt(0);
                if (option == 'A' || option == 'a') {
                    if (invObj.getPayStatus().equals("Pending")) {
                        editData(list,key);
                    }
                    else {
                        System.out.println("+---------------------------------------------------------+");
                        System.out.println("|                      Invalid key!                       |");
                        System.out.println("+---------------------------------------------------------+");
                        System.out.print(" Press [Enter] to continue");
                        String enter = inText.nextLine();
                    }
                }
                else if (option == 'C' || option == 'c') {
                    break;
                }
                else {
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                      Invalid key!                       |");
                    System.out.println("+---------------------------------------------------------+");
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
            Appointment app = new Appointment();
            Patient pat = new Patient();; // placeholder var
            Doctor doc = new Doctor(); // placeholder var
            System.out.print(" NRIC : ");
            String NRIC = inText.nextLine();
            if (!patientIsExist(list2,NRIC)) {
                System.out.print(" Patient Name : ");
                String patName = inText.nextLine();
                System.out.print(" Patient phone number : ");
                String patPhone = inText.nextLine();
                pat = new Patient("P"+generateID(list),NRIC,patName,patPhone);
                patLL.addLast(pat);
                System.out.println("+------------------------------------------+");
            } else {
                Patient patObj = (Patient) list2.getFirst();
                while (patObj != null) {
                    if (patObj.getNRIC().equals(NRIC)) {
                        pat = patObj;
                    }
                    patObj = (Patient) list2.getNext();
                }
                System.out.println(" Patient Name : "+pat.getPatName());
                System.out.println(" Phone : "+pat.getPatPhone());
                System.out.println("+------------------------------------------+");
                System.out.println("|             Patient exist!               |");
                System.out.println("+------------------------------------------+");
            }
            // Choose category
            String category = "Not set";
            System.out.println("+---------------------------------------------------------+");
            System.out.println(" Choose category,");
            System.out.println(" [1] Medical Checkup");
            System.out.println(" [2] Pregnancy Test");
            System.out.println(" [3] Blood Test");
            System.out.println(" [4] Eye Test");
            System.out.println(" [5] Vaccination");
            System.out.println("+------------------------------------------+");
            int option = inNum.nextInt();
            if (option == 1) { category = "Medical Checkup"; }
            else if (option == 2) { category = "Pregnancy Test"; }
            else if (option == 3) { category = "Blood Test"; }
            else if (option == 4) { category = "Eye Test"; }
            else { category = "Vaccination"; }
            // Determine if any slot for the day is available, else get another date
            String date = ""; // Data holding
            int slot = 0; // Data holding
            while (true) {
                System.out.print("\f");
                System.out.println("+------------------------------------------+");
                System.out.println("|            Set Date and Time             |");
                System.out.println("+------------------------------------------+");
                boolean dateIsChoosen = false;
                System.out.print(" Date (DD/MM/YYYY) : ");
                date = inText.nextLine();
                System.out.println("+------------------------------------------+");
                while (true) {
                    System.out.print("\f");
                    System.out.println("+------------------------------------------+");
                    System.out.println("|            Set Date and Time             |");
                    System.out.println("+------------------------------------------+");
                    System.out.println(" Date (DD/MM/YYYY) : "+date);
                    System.out.println("+------------------------------------------+");
                    int time = 1000;
                    // A total of 6 slots, 1 hour each, from 10 am to 3 pm
                    LinkedList slotList = new LinkedList();
                    for (int i=0; i<6; i++) {
                        boolean isExist = false;
                        Appointment appObj = (Appointment) list.getFirst();
                        while (appObj != null) {
                            if (appObj.getDate().equals(date) && appObj.getSlot() == (i+1)) {
                                isExist = true;
                            }
                            appObj = (Appointment) list.getNext();
                        }
                        if (isExist == true) {
                            System.out.println(" Slot "+(i+1)+" is reserved [/]");
                            System.out.println("+------------------------------------------+");
                            slotList.addLast(1);
                        }
                        else {
                            System.out.println(" ["+time+"-"+(time+100)+"] Slot "+(i+1)+" is empty!");
                            System.out.println("+------------------------------------------+");
                            slotList.addLast(0);
                        }
                        time+=100;
                    }
                    System.out.print(" Choose available slot ('0' to back) : ");
                    int slotOpt = inNum.nextInt();
                    boolean isEmpty = true;
                    if (slotOpt == 0) {
                        break;
                    }
                    else if (slotOpt >= 1 && slotOpt <= 6) {
                        int currentSlot = (int) slotList.getFirst();
                        for (int i=1; i<=6; i++) {
                            if (i == slotOpt) {
                                if (currentSlot == 1) {
                                    isEmpty = false;
                                }
                            }
                            if (i != 6) {
                                currentSlot = (int) slotList.getNext();
                            }
                        }
                        if (isEmpty == true) {
                            slot = slotOpt;
                            dateIsChoosen = true;
                            break;
                        }
                        else {
                            System.out.println("+------------------------------------------+");
                            System.out.println("|            Slot is reserved!             |");
                            System.out.println("+------------------------------------------+");
                            System.out.print(" Press [Enter] to continue");
                            String enter = inText.nextLine();
                        }
                    } else {
                        System.out.println("+------------------------------------------+");
                        System.out.println("|               Invalid key!               |");
                        System.out.println("+------------------------------------------+");
                        System.out.print(" Press [Enter] to continue");
                        String enter = inText.nextLine();
                    }
                }
                if (dateIsChoosen == true) {
                    break;
                }
            }
            // Choose doctor
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
                int optionDoc = inNum.nextInt();
                if (optionDoc <= counter && optionDoc >= 0) {
                    int counter2 = 1;
                    docObj = (Doctor) docLL.getFirst();
                    while (counter2 < optionDoc) {
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
            // add to list
            app = new Appointment("A"+generateID(list),pat.getPatID(),doc.getDocID(),date,slot,category);
            list.addLast(app);
            System.out.print("\f");
            System.out.println("+------------------------------------------+");
            System.out.println("|              NEW APPOINTMENT             |");
            System.out.println("+------------------------------------------+");
            System.out.println(" Date : "+app.getDate()); // to be determined
            System.out.println(" Slot : "+app.getSlot()+" | Time : "+app.getTime()); // to be determined
            System.out.println("+------------------------------------------+");
            System.out.println(pat.toStringFormatted());
            System.out.println("+------------------------------------------+");
            System.out.println(doc.toStringFormatted());
            
            System.out.println("+------------------------------------------+");
            System.out.println("|           Data has been added!           |");
            System.out.println("+------------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
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
            else if (list.getFirst() instanceof Invoice) {
                boolean isExist = false;
                Random rand = new Random();
                int randInt = rand.nextInt(100);
                Invoice invObj = (Invoice) list.getFirst();
                while (invObj != null) {
                    int getID = Integer.parseInt(invObj.getInvID().substring(1));
                    if (getID == randInt) {
                        isExist = true;
                    }
                    invObj = (Invoice) list.getNext();
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
                Doctor docObj = (Doctor) getObjectByID(appObj.getDocID(),docLL);
                System.out.print("\f");
                System.out.println("+------------------------------------------+");
                System.out.println("|           EDIT APPOINTMENT DATA          |");
                System.out.println("+------------------------------------------+");
                System.out.println(" A] Slot : "+appObj.getSlot());
                System.out.println("    Date : "+appObj.getDate());
                System.out.println("    Time : "+appObj.getTime());
                System.out.println(" B] Assigned Doctor : "+docObj.getDocName());
                System.out.println(" C] Category : "+appObj.getCategory());
                System.out.println(" E] Back");
                System.out.print(" Option : ");
                char option = inChar.next().charAt(0);
                if (option == 'A' || option =='a') {
                    String date = ""; // Data holding
                    int slot = 0; // Data holding
                    while (true) {
                        System.out.print("\f");
                        System.out.println("+------------------------------------------+");
                        System.out.println("|            Set Date and Time             |");
                        System.out.println("+------------------------------------------+");
                        boolean dateIsChoosen = false;
                        System.out.print(" Date (DD/MM/YYYY) : ");
                        date = inText.nextLine();
                        System.out.println("+------------------------------------------+");
                        while (true) {
                            System.out.print("\f");
                            System.out.println("+------------------------------------------+");
                            System.out.println("|            Set Date and Time             |");
                            System.out.println("+------------------------------------------+");
                            System.out.println(" Date (DD/MM/YYYY) : "+date);
                            System.out.println("+------------------------------------------+");
                            int time = 1000;
                            // A total of 6 slots, 1 hour each, from 10 am to 3 pm
                            LinkedList slotList = new LinkedList();
                            for (int i=0; i<6; i++) {
                                boolean isExist = false;
                                Appointment currentObj = (Appointment) list.getFirst();
                                while (currentObj != null) {
                                    if (currentObj.getDate().equals(date) && currentObj.getSlot() == (i+1)) {
                                        isExist = true;
                                    }
                                    currentObj = (Appointment) list.getNext();
                                }
                                if (isExist == true) {
                                    System.out.println(" Slot "+(i+1)+" is reserved [/]");
                                    System.out.println("+------------------------------------------+");
                                    slotList.addLast(1);
                                }
                                else {
                                    System.out.println(" ["+time+"] Slot "+(i+1)+" is empty!");
                                    System.out.println("+------------------------------------------+");
                                    slotList.addLast(0);
                                }
                                time+=100;
                            }
                            System.out.print(" Choose available slot ('0' to back) : ");
                            int slotOpt = inNum.nextInt();
                            boolean isEmpty = true;
                            if (slotOpt == 0) {
                                break;
                            }
                            else if (slotOpt >= 1 && slotOpt <= 6) {
                                int currentSlot = (int) slotList.getFirst();
                                for (int i=1; i<=6; i++) {
                                    if (i == slotOpt) {
                                        if (currentSlot == 1) {
                                            isEmpty = false;
                                        }
                                    }
                                    if (i != 6) {
                                        currentSlot = (int) slotList.getNext();
                                    }
                                }
                                if (isEmpty == true) {
                                    slot = slotOpt;
                                    dateIsChoosen = true;
                                    break;
                                }
                                else {
                                    System.out.println("+------------------------------------------+");
                                    System.out.println("|            Slot is reserved!             |");
                                    System.out.println("+------------------------------------------+");
                                    System.out.print(" Press [Enter] to continue");
                                    String enter = inText.nextLine();
                                }
                            } else {
                                System.out.println("+------------------------------------------+");
                                System.out.println("|               Invalid key!               |");
                                System.out.println("+------------------------------------------+");
                                System.out.print(" Press [Enter] to continue");
                                String enter = inText.nextLine();
                            }
                        }
                        if (dateIsChoosen == true) {
                            appObj.setDate(date);
                            appObj.setSlot(slot);
                            break;
                        }
                    }
                }
                else if (option == 'B' || option == 'b') {
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
                else if (option == 'C' || option == 'c') {
                    System.out.println(" Current category : "+appObj.getCategory());
                    String category = "Not set";
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println(" Choose category,");
                    System.out.println(" [1] Medical Checkup");
                    System.out.println(" [2] Pregnancy Test");
                    System.out.println(" [3] Blood Test");
                    System.out.println(" [4] Eye Test");
                    System.out.println(" [5] Vaccination");
                    System.out.println("+------------------------------------------+");
                    int optCat = inNum.nextInt();
                    if (optCat == 1) { category = "Medical Checkup"; }
                    else if (optCat == 2) { category = "Pregnancy Test"; }
                    else if (optCat == 3) { category = "Blood Test"; }
                    else if (optCat == 4) { category = "Eye Test"; }
                    else { category = "Vaccination"; }
                    appObj.setCategory(category);
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
            else if (object instanceof Patient) {
                Patient patObj = (Patient) object;
                System.out.print("\f");
                System.out.println("+------------------------------------------+");
                System.out.println("|             EDIT PATIENT DATA            |");
                System.out.println("+------------------------------------------+");
                System.out.println(" A] NRIC : "+patObj.getNRIC());
                System.out.println(" B] Name : "+patObj.getPatName());
                System.out.println(" C] Phone : "+patObj.getPatPhone());
                System.out.println(" D] Back");
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
                else if (option == 'C' || option == 'c') {
                    System.out.println(" Current phone : "+patObj.getPatPhone());
                    System.out.print(" New phone : ");
                    String newPhone = inText.nextLine();
                    patObj.setPatPhone(newPhone);
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
                System.out.println(" C] Phone : "+docObj.getDocPhone());
                System.out.println(" D] Back");
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
                else if (option == 'C' || option == 'c') {
                    System.out.println(" Current phone : "+docObj.getDocPhone());
                    System.out.print(" New phone : ");
                    String newPhone = inText.nextLine();
                    docObj.setDocPhone(newPhone);
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
            else if (object instanceof Invoice) {
                Invoice invObj = (Invoice) object;
                System.out.print("\f");
                System.out.println("+------------------------------------------+");
                System.out.println("|              VERIFY INVOICE              |");
                System.out.println("+------------------------------------------+");
                System.out.print(" Verify payment? (Y/N) : ");
                char option = inChar.next().charAt(0);
                System.out.println("+------------------------------------------+");
                if (option == 'Y' || option =='y') {
                    System.out.println(" Choose payment method,");
                    System.out.println(" [1] Cash");
                    System.out.println(" [2] Debit");
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Option : ");
                    int optPay = inNum.nextInt();
                    // Set
                    invObj.setPayMethod(optPay);
                    invObj.setPayStatus("Confirmed");
                    System.out.println("+------------------------------------------+");
                    System.out.println("|             Payment verified!            |");
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                    break;
                }
                else if (option == 'N' || option == 'n') {
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
    public static Object getObjectByID(String id, LinkedList list) {
        if (id.substring(0,1).equals("A")) {
            Appointment appObj = (Appointment) list.getFirst();
            while (appObj != null) {
                if (appObj.getAppID().equals(id)) {
                    return appObj;
                }
                appObj = (Appointment) list.getNext();
            }
        }
        else if (id.substring(0,1).equals("P")) {
            Patient patObj = (Patient) list.getFirst();
            while (patObj != null) {
                if (patObj.getPatID().equals(id)) {
                    return patObj;
                }
                patObj = (Patient) list.getNext();
            }
        }
        else if (id.substring(0,1).equals("D")) {
            Doctor docObj = (Doctor) list.getFirst();
            while (docObj != null) {
                if (docObj.getDocID().equals(id)) {
                    return docObj;
                }
                docObj = (Doctor) list.getNext();
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
                            appObj.getAppID().equalsIgnoreCase(keyword) || appObj.getCategory().equalsIgnoreCase(keyword)) {
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
                else if (list.getFirst() instanceof Invoice) {
                    Invoice invObj = (Invoice) list.getFirst();
                    while (invObj != null) {
                        if (invObj.getInvID().equalsIgnoreCase(keyword) || invObj.getPatNRIC().equalsIgnoreCase(keyword)) {
                            System.out.println(" "+(countFound+1)+"] "+invObj.toString()); 
                            System.out.println("+------------------------------------------+");
                            keyFound.addLast(counter);
                            countFound++;
                        }
                        counter++;
                        invObj = (Invoice) list.getNext();
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
}
