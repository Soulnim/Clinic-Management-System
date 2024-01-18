import java.util.*;
import java.io.*;

public class ClinicQApps
{
    static Scanner inText = new Scanner(System.in);
    static Scanner inChar = new Scanner(System.in);
    static Scanner inNum = new Scanner(System.in);
    
    static Queue appQueue = new Queue();
    static Queue patQueue = new Queue();
    static Queue docQueue = new Queue();
    static Queue invQueue = new Queue(); // to be determined
    
    static int sessionCode = 1; // 0-Terminate, 1-Idle, 2-Logged in
    static String SECURITY_KEY = "1"; // to be determined
    static String PASSWORD = "1"; // to be determined
    
    public static void main(String args[]) throws Exception {
        // Fetch data from clinicdata.txt
        try {
            BufferedReader br = new BufferedReader(new FileReader("clinicdata.txt"));
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
                    String status = st.nextToken();
                    appQueue.enqueue(new Appointment(appID,patID,docID,date,slot,status));
                }
                else if (dataType.equals("PATIENT")) {
                    String patID = st.nextToken();
                    String NRIC = st.nextToken();
                    String patName = st.nextToken();
                    String patPhone = st.nextToken();
                    patQueue.enqueue(new Patient(patID,NRIC,patName,patPhone));
                }
                else if (dataType.equals("DOCTOR")) {
                    String docID = st.nextToken();
                    String name = st.nextToken();
                    String specialty = st.nextToken();
                    String docPhone = st.nextToken();
                    docQueue.enqueue(new Doctor(docID,name,specialty,docPhone));
                }
                else if (dataType.equals("INVOICE")) {
                    String invID = st.nextToken();
                    Patient pat = (Patient) getObjectByID(st.nextToken(),patQueue);
                    Doctor doc = (Doctor) getObjectByID(st.nextToken(),docQueue);
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
                    invQueue.enqueue(new Invoice(invID,app,pat,doc,payMethod,payStatus));
                }
                data = br.readLine();
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        
        // MAIN PROCESSES
        while (sessionCode != 0) {
            if (sessionCode == 2) {
                dashboard(appQueue,patQueue,docQueue);
            }
            else {
                login();
            }
        }
        
        // STORE Data back to clinicdata.txt
        try {
            FileWriter fw = new FileWriter("clinicdata.txt");
            while (!appQueue.isEmpty()) {
                Appointment appObj = (Appointment) appQueue.dequeue();
                fw.write(appObj.txtFormat()+"\n");
            }
            while (!patQueue.isEmpty()) {
                Patient patObj = (Patient) patQueue.dequeue();
                fw.write(patObj.txtFormat()+"\n");
            }
            while (!docQueue.isEmpty()) {
                Doctor docObj = (Doctor) docQueue.dequeue();
                fw.write(docObj.txtFormat()+"\n");
            }
            while (!invQueue.isEmpty()) {
                Invoice invObj = (Invoice) invQueue.dequeue();
                fw.write(invObj.txtFormat()+"\n");
            }
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // LOGIN
    public static void login() {
        System.out.print("\f");
        System.out.println("+---------------------------------------------------------+");
        System.out.println("|                         LOGIN                           |");
        System.out.println("+---------------------------------------------------------+");
        System.out.print(" Enter security key ('0' to exit) : ");
        String secKey = inText.nextLine();
        if (secKey.equals("0")) {
            sessionCode = 0;
            System.out.print("\f");
            System.out.println("+---------------------------------------------------------+");
            System.out.println("|                    Session Terminated                   |");
            System.out.println("+---------------------------------------------------------+");
            return;
        }
        System.out.println("+---------------------------------------------------------+");
        System.out.print(" Enter password : ");
        String pass = inText.nextLine();
        if (secKey.equals(SECURITY_KEY) && pass.equals(PASSWORD)) {
            sessionCode = 2;
            System.out.println("+---------------------------------------------------------+");
            System.out.println("|                Logged in successfully!                  |");
            System.out.println("+---------------------------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
        }
        else {
            System.out.println("+---------------------------------------------------------+");
            System.out.println("|            Invalid security key or password!            |");
            System.out.println("+---------------------------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
        }
    }
    
    public static void dashboard(Queue appQueue, Queue patQueue, Queue docQueue) {
        System.out.print("\f");
        System.out.println("+---------------------------------------------------------+");
        System.out.println("|                       MAIN MENU                         |");
        System.out.println("+---------------------------------------------------------+");
        System.out.println("|     A] Schedule New Appointment                         |");
        System.out.println("|     B] Manage Appointment                               |");
        System.out.println("|     C] Manage Patient                                   |");
        System.out.println("|     D] Manage Invoice                                   |");
        System.out.println("|     E] Manage Doctor                                    |");
        System.out.println("|     F] Exit                                             |");
        System.out.println("+---------------------------------------------------------+");
        System.out.print(" Option : ");
        char option = inChar.next().charAt(0);
        if (option == 'A' || option == 'a') {
            addData(appQueue,patQueue);
        }
        else if (option == 'B' || option == 'b') {
            displayList(appQueue,patQueue);
        }
        else if (option == 'C' || option == 'c') {
            displayList(patQueue,null);
        }
        else if (option == 'D' || option == 'd') {
            displayList(invQueue,null);
        }
        else if (option == 'E' || option == 'e') {
            displayList(docQueue,null);
        }
        else if (option == 'F' || option == 'f') {
            sessionCode = 1;
        }
        else {
            System.out.println("+---------------------------------------------------------+");
            System.out.println("|                      Invalid key!                       |");
            System.out.println("+---------------------------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
        }
    }
    
    public static void displayList(Queue list1, Queue list2) {
        int page = 1;
        int floor = 0;
        
        while (true) {
            // Check for queue type
            Object object = (Object) list1.getFront();
            if (object != null) {
                if (object instanceof Appointment) {
                    System.out.print("\f");
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                    APPOINTMENT LIST                     |");
                    System.out.println("+---------------------------------------------------------+");
                } else if (object instanceof Patient) {
                    System.out.print("\f");
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                      PATIENT LIST                       |");
                    System.out.println("+---------------------------------------------------------+");
                } else if (object instanceof Doctor) {
                    System.out.print("\f");
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                       DOCTOR LIST                       |");
                    System.out.println("+---------------------------------------------------------+");
                }
                else if (object instanceof Invoice) {
                    System.out.print("\f");
                    System.out.println("+------------------------------------------+");
                    System.out.println("|               INVOICE LIST               |");
                    System.out.println("+------------------------------------------+");
                }
            } else {
                System.out.print("\f");
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|                      List is empty!                     |");
                System.out.println("+---------------------------------------------------------+");
                System.out.print(" Press [Enter] to continue");
                String enter = inText.nextLine();
                return;
            }

            int counter = 0;
            // Set page and its displayed data
            Queue temp = new Queue();
            if (list1.getFront() instanceof Appointment) {
                // Store patient data in temporary Queue
                Queue tempp = new Queue();
                Queue tempPatQ = new Queue();
                while (!patQueue.isEmpty()) {
                    Patient currPat = (Patient) patQueue.dequeue();
                    tempPatQ.enqueue(currPat);
                    tempp.enqueue(currPat);
                }
                while (!tempp.isEmpty()) {
                    patQueue.enqueue(tempp.dequeue());
                }
                // Proceed
                while (!list1.isEmpty() && counter != floor) {
                    Appointment appObj = (Appointment) list1.dequeue();
                    temp.enqueue(appObj);
                    counter++;
                }
                while (!list1.isEmpty() && counter < (page * 10)) {
                    Appointment appObj = (Appointment) list1.dequeue();
                    String patID = appObj.getPatID();
                    Patient pat = (Patient) getObjectByID(patID,tempPatQ);
                    temp.enqueue(appObj);
                    System.out.println(" "+(counter+1)+"] NRIC : "+pat.getNRIC()+appObj.toString());
                    counter++;
                    System.out.println("+---------------------------------------------------------+");
                }
                while (!list1.isEmpty()) {
                    temp.enqueue(list1.dequeue());
                }
                // store temp's data back to its original list
                while (!temp.isEmpty()) {
                    list1.enqueue(temp.dequeue());
                }
            }
            else {
                while (!list1.isEmpty() && counter != floor) {
                    object = list1.dequeue();
                    temp.enqueue(object);
                    counter++;
                }
                while (!list1.isEmpty() && counter < (page * 10)) {
                    object = list1.dequeue();
                    temp.enqueue(object);
                    System.out.println(" " + (counter + 1) + "] " + object.toString());
                    counter++;
                    System.out.println("+---------------------------------------------------------+");
                }
                while (!list1.isEmpty()) {
                    temp.enqueue(list1.dequeue());
                }
                // store temp's data back to its original list
                while (!temp.isEmpty()) {
                    list1.enqueue(temp.dequeue());
                }
            }

            // Check total page
            System.out.println(" Enter index (1..) to choose -");
            if (page == 1) {
                System.out.println(" [V] Next , [H] Home");
            } else {
                System.out.println(" [C] Previous , [V] Next , [H] Home");
            }
            System.out.println("+---------------------------------------------------------+ Page : "+page);
            System.out.println("|                    [S] Search Data                      |");
            System.out.println("+---------------------------------------------------------+");
            System.out.print(" Option : ");
            String option = inText.nextLine();
            if (option.equalsIgnoreCase("V")) {
                page++;
                floor += 10;
            } else if (option.equalsIgnoreCase("C")) {
                if (page - 1 < 1) {
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                   Page limit reached!                   |");
                    System.out.println("+---------------------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                } else {
                    page--;
                    floor -= 10;
                }
            } else if (option.equalsIgnoreCase("H")) {
                break;
            }
            else if (option.equalsIgnoreCase("S")) {
                searchData(list1,list2);
            }
            else {
                try {
                    int key = Integer.parseInt(option);
                    if (key > 0 && key <= counter) {
                        displayData(list1, key);
                    }
                    else {
                        System.out.println("+---------------------------------------------------------+");
                        System.out.println("|                      Invalid key!                       |");
                        System.out.println("+---------------------------------------------------------+");
                        System.out.print(" Press [Enter] to continue");
                        String enter = inText.nextLine();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                      Invalid key!                       |");
                    System.out.println("+---------------------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
            }
        }
    }
    
    // DISPLAY SPECIFIC DATA FROM LIST
    public static void displayData(Queue list, int key) {
        int counter = 1;
        Queue temp = new Queue();
        Object object = list.getFront();
        while (!list.isEmpty()) {
            Object current = (Object) list.dequeue();
            if (counter == key) {
                object = current;
            }
            temp.enqueue(current);
            counter++;
        }
        while (!temp.isEmpty()) {
            list.enqueue(temp.dequeue());
        }
        while (true) {
            if (object instanceof Appointment) {
                Appointment appObj = (Appointment) object;
                Patient patObj = (Patient) getObjectByID(appObj.getPatID(),patQueue);
                Doctor docObj = (Doctor) getObjectByID(appObj.getDocID(),docQueue);
                System.out.print("\f");
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|                    APPOINTMENT DATA                     |");
                System.out.println("+---------------------------------------------------------+");
                System.out.println(appObj.toStringFormatted());
                System.out.println("+---------------------------------------------------------+");
                System.out.println(patObj.toStringFormatted());
                System.out.println("+---------------------------------------------------------+");
                System.out.println(docObj.toStringFormatted());
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|             [A] Edit, [B] Delete, [C] Back              |");
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|                 [V] Verify Appointment                  |");
                System.out.println("+---------------------------------------------------------+");
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
                else if (option == 'V' || option == 'v') {
                    System.out.print(" Are you sure? (Y/N) : ");
                    char optVer = inChar.next().charAt(0);
                    if (optVer == 'Y' || optVer == 'y') {
                        // Appointment has been verified
                        // Invoice is issued, and data has been removed
                        // All data has been transferred to invoice
                        Invoice inv = new Invoice("I"+generateID(invQueue),appObj,patObj,docObj,0,"Pending");
                        invQueue.enqueue(inv);
                        // Remove appointment
                        Queue tempApp = new Queue();
                        while (!appQueue.isEmpty()) {
                            Appointment currApp = (Appointment) appQueue.dequeue();
                            if (currApp.getAppID().equals(appObj.getAppID())) {
                                // do nothing
                            }
                            else {
                                tempApp.enqueue(currApp);
                            }
                        }
                        while (!tempApp.isEmpty()) {
                            appQueue.enqueue(tempApp.dequeue());
                        }
                        
                        System.out.println("+---------------------------------------------------------+");
                        System.out.println("|             Appointment has been verified.              |");
                        System.out.println("|                   New invoice added!                    |");
                        System.out.println("+---------------------------------------------------------+");
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
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|                      PATIENT DATA                       |");
                System.out.println("+---------------------------------------------------------+");
                System.out.println(" "+patObj.toStringFormatted());
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|                   [A] Edit, [C] Back                    |");
                System.out.println("+---------------------------------------------------------+");
                System.out.print(" Option : ");
                char option = inChar.next().charAt(0);
                if (option == 'A' || option == 'a') {
                    editData(list,key);
                }
                else if (option == 'C' || option == 'c') {
                    break;
                }
                else {
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                       Invalid key!                      |");
                    System.out.println("+---------------------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
            }
            else if (object instanceof Doctor) {
                Doctor docObj = (Doctor) object;
                System.out.print("\f");
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|                       DOCTOR DATA                       |");
                System.out.println("+---------------------------------------------------------+");
                System.out.println(" "+docObj.toStringFormatted());
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|                    [A] Edit, [C] Back                   |");
                System.out.println("+---------------------------------------------------------+");
                System.out.print(" Option : ");
                char option = inChar.next().charAt(0);
                if (option == 'A' || option == 'a') {
                    editData(list,key);
                }
                else if (option == 'C' || option == 'c') {
                    break;
                }
                else {
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                     Invalid key!                        |");
                    System.out.println("+---------------------------------------------------------+");
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
    public static void addData(Queue list, Queue list2) {
        if (list.getFront() instanceof Appointment) {
            System.out.print("\f");
            System.out.println("+---------------------------------------------------------+");
            System.out.println("|                  ADD NEW APPOINTMENT                    |");
            System.out.println("+---------------------------------------------------------+");
            Appointment app = new Appointment();
            Patient pat = new Patient(); // placeholder var
            Doctor doc = new Doctor(); // placeholder var
            System.out.print(" NRIC : ");
            String NRIC = inText.nextLine();
            if (!patientIsExist(list2,NRIC)) {
                System.out.print(" Patient Name : ");
                String patName = inText.nextLine();
                System.out.print(" Patient phone number : ");
                String patPhone = inText.nextLine();
                pat = new Patient("P"+generateID(list),NRIC,patName,patPhone);
                list2.enqueue(pat);
            } else {
                Queue temp = new Queue();
                while (!list2.isEmpty()) {
                    Patient patObj = (Patient) list2.dequeue();
                    if (patObj.getNRIC().equals(NRIC)) {
                        pat = patObj;
                    }
                    temp.enqueue(patObj);
                }
                while(!temp.isEmpty()) {
                    list2.enqueue(temp.dequeue());
                }
                System.out.println(" Patient Name : "+pat.getPatName());
                System.out.println(" Phone : "+pat.getPatPhone());
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|                    Patient exist!                       |");
                System.out.println("+---------------------------------------------------------+");
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
            System.out.println("+---------------------------------------------------------+");
            int optCat = inNum.nextInt();
            if (optCat == 1) { category = "Medical Checkup"; }
            else if (optCat == 2) { category = "Pregnancy Test"; }
            else if (optCat == 3) { category = "Blood Test"; }
            else if (optCat == 4) { category = "Eye Test"; }
            else { category = "Vaccination"; }
            // SET DATE AND TIME
            String date = ""; // Data holding
            int slot = 0; // Data holding
            while (true) {
                System.out.print("\f");
                Queue temp = new Queue();
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|                   Set Date and Time                     |");
                System.out.println("+---------------------------------------------------------+");
                boolean dateIsChoosen = false;
                System.out.print(" Date (DD/MM/YYYY) : ");
                date = inText.nextLine();
                System.out.println("+---------------------------------------------------------+");
                while (true) {
                    System.out.print("\f");
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                    Set Date and Time                    |");
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println(" Date (DD/MM/YYYY) : "+date);
                    System.out.println("+---------------------------------------------------------+");
                    int time = 1000;
                    // A total of 6 slots, 1 hour each, from 10 am to 3 pm
                    Queue slotList = new Queue();
                    for (int i=0; i<6; i++) {
                        boolean isExist = false;
                        while (!list.isEmpty()) {
                            Appointment currentObj = (Appointment) list.dequeue();
                            if (currentObj.getDate().equals(date) && currentObj.getSlot() == (i+1)) {
                                isExist = true;
                                // NEW
                            }
                            temp.enqueue(currentObj);
                        }
                        while (!temp.isEmpty()) {
                            list.enqueue(temp.dequeue());
                        }
                        if (isExist == true) {
                            System.out.println(" Slot "+(i+1)+" is reserved [/]");
                            System.out.println("+---------------------------------------------------------+");
                            slotList.enqueue(1);
                        }
                        else {
                            System.out.println(" ["+time+"-"+(time+100)+"] Slot "+(i+1)+" is empty!");
                            System.out.println("+---------------------------------------------------------+");
                            slotList.enqueue(0);
                        }
                        time+=100;
                    }
                    System.out.println(slotList);
                    System.out.print(" Choose available slot ('0' to back) : ");
                    int slotOpt = inNum.nextInt();
                    boolean isEmpty = true;
                    if (slotOpt == 0) {
                        break;
                    }
                    else if (slotOpt >= 1 && slotOpt <= 6) {
                        int counter2 = 1;
                        while (!slotList.isEmpty()) {
                            int currentSlot = (int) slotList.dequeue();
                            if (counter2 == slotOpt) {
                                if (currentSlot == 1) {
                                    isEmpty = false;
                                }
                            }
                            counter2++;
                        }
                        if (isEmpty == true) {
                            slot = slotOpt;
                            dateIsChoosen = true;
                            break;
                        }
                        else {
                            System.out.println("+---------------------------------------------------------+");
                            System.out.println("|                   Slot is reserved!                     |");
                            System.out.println("+---------------------------------------------------------+");
                            System.out.print(" Press [Enter] to continue");
                            String enter = inText.nextLine();
                        }
                    } else {
                        System.out.println("+---------------------------------------------------------+");
                        System.out.println("|                     Invalid key!                        |");
                        System.out.println("+---------------------------------------------------------+");
                        System.out.print(" Press [Enter] to continue");
                        String enter = inText.nextLine();
                    }
                }
                if (dateIsChoosen == true) {
                    break;
                }
            }
            //===================
            while (true) {
                // Choose doctor
                int counter = 0;
                System.out.print("\f");
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|             Choose a doctor to be assigned :            |");
                System.out.println("+---------------------------------------------------------+");
                Queue temp = new Queue();
                while (!docQueue.isEmpty()) {
                    Doctor docObj = (Doctor) docQueue.dequeue();
                    System.out.println(" "+(counter+1)+"]"+docObj.toStringFormatted());
                    counter++;
                    System.out.println("+---------------------------------------------------------+");
                    temp.enqueue(docObj);
                }
                while(!temp.isEmpty()) {
                    docQueue.enqueue(temp.dequeue());
                }
                System.out.print(" Option : ");
                int option = inNum.nextInt();
                if (option <= counter && option >= 1) {
                    int counter2 = 0;                    
                    while (!docQueue.isEmpty()) {
                        Doctor docObj = (Doctor) docQueue.dequeue();
                        if (counter2 == option-1) {
                            doc = docObj;
                        }
                        counter2++;
                        temp.enqueue(docObj);
                    }
                    while(!temp.isEmpty()) {
                        docQueue.enqueue(temp.dequeue());
                    }
                    break;
                }
                else {
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                     Invalid key!                        |");
                    System.out.println("+---------------------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
            }
            // add to list
            app = new Appointment("A"+generateID(list),pat.getPatID(),doc.getDocID(),date,slot,category);
            list.enqueue(app);
            System.out.print("\f");
            System.out.println("+---------------------------------------------------------+");
            System.out.println("|                   ADD NEW APPOINTMENT                   |");
            System.out.println("+---------------------------------------------------------+");
            System.out.println(" Date : "+app.getDate());
            System.out.println(" Slot : "+app.getSlot()+" | Time : "+app.getTime());
            System.out.println("+---------------------------------------------------------+");
            System.out.println(pat.toStringFormatted());
            System.out.println("+---------------------------------------------------------+");
            System.out.println(doc.toStringFormatted());
            
            System.out.println("+---------------------------------------------------------+");
            System.out.println("|                   Data has been added!                  |");
            System.out.println("+---------------------------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
        }
    }
    
    // GENERATE RANDOM NUMBER FOR ID
    public static int generateID(Queue list) {
        Queue temp = new Queue();
        while (true) {
            if (list.getFront() instanceof Appointment) {
                boolean isExist = false;
                Random rand = new Random();
                int randInt = rand.nextInt(100);
                while (!list.isEmpty()) {
                    Appointment appObj = (Appointment) list.dequeue();
                    int getID = Integer.parseInt(appObj.getAppID().substring(1));
                    if (getID == randInt) {
                        isExist = true;
                    }
                    temp.enqueue(appObj);
                }
                while(!temp.isEmpty()) {
                    list.enqueue(temp.dequeue());
                }
                if (isExist == false) {
                    return randInt;
                }
            }
            else if (list.getFront() instanceof Patient) {
                boolean isExist = false;
                Random rand = new Random();
                int randInt = rand.nextInt(100);
                while (!list.isEmpty()) {
                    Patient patObj = (Patient) patQueue.dequeue();
                    int getID = Integer.parseInt(patObj.getPatID().substring(1));
                    if (getID == randInt) {
                        isExist = true;
                    }
                    temp.enqueue(patObj);
                }
                while(!temp.isEmpty()) {
                    list.enqueue(temp.dequeue());
                }
                if (isExist == false) {
                    return randInt;
                }
            }
            else if (list.getFirst() instanceof Invoice) {
                boolean isExist = false;
                Random rand = new Random();
                int randInt = rand.nextInt(100);
                while (!list.isEmpty()) {
                    Invoice invObj = (Invoice) invQueue.dequeue();
                    int getID = Integer.parseInt(invObj.getInvID().substring(1));
                    if (getID == randInt) {
                        isExist = true;
                    }
                    temp.enqueue(invObj);
                }
                while(!temp.isEmpty()) {
                    list.enqueue(temp.dequeue());
                }
                if (isExist == false) {
                    return randInt;
                }
            }
        }
    }
    
    // Check wether patient is exist or not
    public static boolean patientIsExist(Queue list,String NRIC) {
        Queue temp = new Queue();
        boolean isExist = false;
        while (!list.isEmpty()) {
            Patient patObj = (Patient) list.dequeue();
            if (patObj.getNRIC().equals(NRIC)) {
                isExist = true;
            }
            temp.enqueue(patObj);
        }
        while(!temp.isEmpty()) {
            list.enqueue(temp.dequeue());
        }
        return isExist;
    }
    
    // EDIT DATA IN LIST
    public static void editData(Queue list, int key) {
        int counter = 1;
        Object object = (Object) list.getFront();
        Queue temp = new Queue();
        while (!list.isEmpty()) {
            Object curObj = (Object) list.dequeue();
            if (counter == key) {
                object = curObj;
            }
            counter++;
        }
        while (true) {
            if (object instanceof Appointment) {
                Appointment appObj = (Appointment) object;
                Doctor docObj = (Doctor) getObjectByID(appObj.getDocID(),docQueue);
                System.out.print("\f");
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|                  EDIT APPOINTMENT DATA                  |");
                System.out.println("+---------------------------------------------------------+");
                System.out.println(" A] Slot : "+appObj.getSlot());
                System.out.println("    Date : "+appObj.getTime());
                System.out.println("    Time : "+appObj.getTime());
                System.out.println(" B] Assigned Doctor : "+docObj.getDocName());
                System.out.println(" C] Category : "+appObj.getCategory());
                System.out.println(" E] Back");
                System.out.println("+---------------------------------------------------------+");
                System.out.println(" Option : ");
                char option = inChar.next().charAt(0);
                if (option == 'A' || option =='a') {
                    String date = ""; // Data holding
                    int slot = 0; // Data holding
                    while (true) {
                        System.out.print("\f");
                        System.out.println("+---------------------------------------------------------+");
                        System.out.println("|                   Set Date and Time                     |");
                        System.out.println("+---------------------------------------------------------+");
                        boolean dateIsChoosen = false;
                        System.out.print(" Date (DD/MM/YYYY) : ");
                        date = inText.nextLine();
                        System.out.println("+---------------------------------------------------------+");
                        while (true) {
                            System.out.print("\f");
                            System.out.println("+---------------------------------------------------------+");
                            System.out.println("|                    Set Date and Time                    |");
                            System.out.println("+---------------------------------------------------------+");
                            System.out.println(" Date (DD/MM/YYYY) : "+date);
                            System.out.println("+---------------------------------------------------------+");
                            int time = 1000;
                            // A total of 6 slots, 1 hour each, from 10 am to 3 pm
                            Queue slotList = new Queue();
                            for (int i=0; i<6; i++) {
                                boolean isExist = false;
                                while (!list.isEmpty()) {
                                    Appointment currentObj = (Appointment) list.dequeue();
                                    if (currentObj.getDate().equals(date) && currentObj.getSlot() == (i+1)) {
                                        isExist = true;
                                    }
                                    temp.enqueue(currentObj);
                                }
                                while (!temp.isEmpty()) {
                                    list.enqueue(temp.dequeue());
                                }
                                if (isExist == true) {
                                    System.out.println(" Slot "+(i+1)+" is reserved [/]");
                                    System.out.println("+---------------------------------------------------------+");
                                    slotList.addLast(1);
                                }
                                else {
                                    System.out.println(" ["+time+"] Slot "+(i+1)+" is empty!");
                                    System.out.println("+---------------------------------------------------------+");
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
                                int counter2 = 1;
                                while (!slotList.isEmpty()) {
                                    int currentSlot = (int) slotList.dequeue();
                                    if (counter2 == slotOpt) {
                                        if (currentSlot == 1) {
                                            isEmpty = false;
                                        }
                                    }
                                }
                                if (isEmpty == true) {
                                    slot = slotOpt;
                                    dateIsChoosen = true;
                                    break;
                                }
                                else {
                                    System.out.println("+---------------------------------------------------------+");
                                    System.out.println("|                    Slot is reserved!                    |");
                                    System.out.println("+---------------------------------------------------------+");
                                    System.out.print(" Press [Enter] to continue");
                                    String enter = inText.nextLine();
                                }
                            } else {
                                System.out.println("+---------------------------------------------------------+");
                                System.out.println("|                       Invalid key!                      |");
                                System.out.println("+---------------------------------------------------------+");
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
                        System.out.println("+---------------------------------------------------------+");
                        System.out.println("|             Choose a doctor to be assigned :            |");
                        System.out.println("+---------------------------------------------------------+");
                        while (!docQueue.isEmpty()) {
                            Doctor currentDoc = (Doctor) docQueue.dequeue();
                            System.out.println(" "+(countDoc+1)+"]"+currentDoc.toStringFormatted());
                            countDoc++;
                            System.out.println("+---------------------------------------------------------+");
                            temp.enqueue(currentDoc);
                        }
                        while(!temp.isEmpty()) {
                            docQueue.enqueue(temp.dequeue());
                        }
                        System.out.print(" Option : ");
                        int optionDoc = inNum.nextInt();
                        if (optionDoc <= countDoc && optionDoc >= 1) {
                            int counter2 = 0;                    
                            while (!docQueue.isEmpty()) {
                                Doctor currentDoc = (Doctor) docQueue.dequeue();
                                if (counter2 == optionDoc-1) {
                                    appObj.setDocID(currentDoc.getDocID());
                                }
                                counter2++;
                                temp.enqueue(currentDoc);
                            }
                            while(!temp.isEmpty()) {
                                docQueue.enqueue(temp.dequeue());
                            }
                            break;
                        }
                        else {
                            System.out.println("+---------------------------------------------------------+");
                            System.out.println("|                     Invalid key!                        |");
                            System.out.println("+---------------------------------------------------------+");
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
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|                    EDIT PATIENT DATA                    |");
                System.out.println("+---------------------------------------------------------+");
                System.out.println(" A] NRIC : "+patObj.getNRIC());
                System.out.println(" B] Name : "+patObj.getPatName());
                System.out.println(" C] Phone : "+patObj.getPatPhone());
                System.out.println(" D] Back");
                System.out.println("+---------------------------------------------------------+");
                System.out.println(" Option : ");
                char option = inChar.next().charAt(0);
                if (option == 'A' || option =='a') {
                    System.out.println(" Current NRIC : "+patObj.getNRIC());
                    System.out.print(" New NRIC : ");
                    String newNRIC = inText.nextLine();
                    patObj.setNRIC(newNRIC);
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                  Data has been edited!                  |");
                    System.out.println("+---------------------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
                else if (option == 'B' || option == 'b') {
                    System.out.println(" Current name : "+patObj.getPatName());
                    System.out.print(" New name : ");
                    String newName = inText.nextLine();
                    patObj.setPatName(newName);
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                  Data has been edited!                  |");
                    System.out.println("+---------------------------------------------------------+");
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
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|                   EDIT DOCTOR'S DATA                    |");
                System.out.println("+---------------------------------------------------------+");
                System.out.println(" A] Name : "+docObj.getDocName());
                System.out.println(" B] Specialty : "+docObj.getSpecialty());
                System.out.println(" C] Phone : "+docObj.getDocPhone());
                System.out.println(" D] Back");
                System.out.println("+---------------------------------------------------------+");
                System.out.println(" Option : ");
                char option = inChar.next().charAt(0);
                if (option == 'A' || option =='a') {
                    System.out.println(" Current name : "+docObj.getDocName());
                    System.out.print(" New name : ");
                    String newName = inText.nextLine();
                    docObj.setDocName(newName);
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                  Data has been edited!                  |");
                    System.out.println("+---------------------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                }
                else if (option == 'B' || option == 'b') {
                    System.out.println(" Current specialty : "+docObj.getSpecialty());
                    System.out.print(" New specialty : ");
                    String newSpec = inText.nextLine();
                    docObj.setSpecialty(newSpec);
                    System.out.println("+---------------------------------------------------------+");
                    System.out.println("|                  Data has been edited!                  |");
                    System.out.println("+---------------------------------------------------------+");
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
    public static boolean deleteData(Queue list, int key) {
        int counter = 0;
        Queue temp = new Queue();
        Object object = new Object();
        while (!list.isEmpty()) {
            Object current = (Object) list.dequeue();
            if (counter == key-1) {
                object = current;
            }
            temp.enqueue(current);
            counter++;
        }
        while (!temp.isEmpty()) {
            list.enqueue(temp.dequeue());
        }
        System.out.print("\f");
        System.out.println("+---------------------------------------------------------+");
        System.out.println("|                         Delete?                         |");
        System.out.println("+---------------------------------------------------------+");
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
        System.out.println("+---------------------------------------------------------+");
        System.out.println("|                     [Y] Yes, [N] No                     |");
        System.out.println("+---------------------------------------------------------+");
        System.out.print(" Option : ");
        char option = inChar.next().charAt(0);
        if (option == 'Y' || option == 'y') {
            counter = 0;
            Queue tempQ = new Queue();
            while (!list.isEmpty()) {
                Object currObj = (Object) list.dequeue();
                if (counter != key-1) {
                    tempQ.enqueue(currObj);
                }
                counter++;
            }
            while (!tempQ.isEmpty()) {
                list.enqueue(tempQ.dequeue());
            }
            System.out.println("+---------------------------------------------------------+");
            System.out.println("|                  Data has been deleted!                 |");
            System.out.println("+---------------------------------------------------------+");
            System.out.print(" Press [Enter] to continue");
            String enter = inText.nextLine();
            return true;
        }
        else {
            return false;
        }
    }
    
    // Getting specific data by ID
    public static Object getObjectByID(String id,Queue list) {
        Queue temp = new Queue();
        Object object = new Object();
        if (id.substring(0,1).equals("A")) {
            while (!list.isEmpty()) {
                Appointment appObj = (Appointment) list.dequeue();
                if (appObj.getAppID().equals(id)) {
                    object = appObj;
                }
                temp.enqueue(appObj);
            }
            while (!temp.isEmpty()) {
                list.enqueue(temp.dequeue());
            }
        }
        else if (id.substring(0,1).equals("P")) {
            while (!list.isEmpty()) {
                Patient patObj = (Patient) list.dequeue();
                if (patObj.getPatID().equals(id)) {
                    object = patObj;
                }
                temp.enqueue(patObj);
            }
            while (!temp.isEmpty()) {
                list.enqueue(temp.dequeue());
            }
        }
        else if (id.substring(0,1).equals("D")) {
            while (!list.isEmpty()) {
                Doctor docObj = (Doctor) list.dequeue();
                if (docObj.getDocID().equals(id)) {
                    object = docObj;
                }
                temp.enqueue(docObj);
            }
            while (!temp.isEmpty()) {
                list.enqueue(temp.dequeue());
            }
        }
        return object;
    }
    
    // SEARCH FOR DATA IN LIST - NOT MODIFIED YET
    public static void searchData(Queue list,Queue list2) {
        while (true) {
            System.out.print("\f");
            System.out.println("+---------------------------------------------------------+");
            System.out.println("|                       SEARCH DATA                       |");
            System.out.println("+---------------------------------------------------------+");
            System.out.print(" Enter keyword ('0' to back) : ");
            String keyword = inText.nextLine();
            System.out.println("+---------------------------------------------------------+");
            if (keyword.equals("0")) {
                break;
            }
            while (true) {
                System.out.print("\f");
                System.out.println("+---------------------------------------------------------+");
                System.out.println("|                      SEARCH DATA                        |");
                System.out.println("+---------------------------------------------------------+");
                int counter = 1;
                int countFound = 0;
                Queue keyFound = new Queue();
                Queue temp = new Queue();
                if (list.getFront() instanceof Appointment) {
                    while (!list.isEmpty()) {
                        Appointment appObj = (Appointment) list.dequeue();
                        // Get temp list for patient
                        Queue tempp = new Queue();
                        Queue tempPatQ = new Queue();
                        while (!list2.isEmpty()) {
                            Patient currPat = (Patient) list2.dequeue();
                            tempPatQ.enqueue(currPat);
                            tempp.enqueue(currPat);
                        }
                        while (!tempp.isEmpty()) {
                            list2.enqueue(tempp.dequeue());
                        }
                        //---------------
                        String patID = appObj.getPatID();
                        Patient patObj = (Patient) getObjectByID(patID,tempPatQ);
                        if (appObj.getDate().equalsIgnoreCase(keyword) || appObj.getTime().equalsIgnoreCase(keyword) ||
                            appObj.getAppID().equalsIgnoreCase(keyword) || appObj.getCategory().equalsIgnoreCase(keyword) ||
                            patObj.getNRIC().equals(keyword)) {
                            System.out.println(" "+(countFound+1)+"] NRIC : "+patObj.getNRIC()+appObj.toString());
                            System.out.println("+---------------------------------------------------------+");
                            keyFound.enqueue(counter);
                            countFound++;
                        }
                        counter++;
                        temp.enqueue(appObj);
                    }
                    while (!temp.isEmpty()) {
                        list.enqueue(temp.dequeue());
                    }
                }
                else if (list.getFirst() instanceof Patient) {
                    while (!list.isEmpty()) {
                        Patient patObj = (Patient) list.dequeue();
                        if (patObj.getPatID().equalsIgnoreCase(keyword) || patObj.getNRIC().equalsIgnoreCase(keyword) ||
                            patObj.getPatName().equalsIgnoreCase(keyword)) {
                            System.out.println(" "+(countFound+1)+"] "+patObj.toString()); 
                            System.out.println("+---------------------------------------------------------+");
                            keyFound.enqueue(counter);
                            countFound++;
                        }
                        counter++;
                        temp.enqueue(patObj);
                    }
                    while (!temp.isEmpty()) {
                        list.enqueue(temp.dequeue());
                    }
                }
                else if (list.getFirst() instanceof Doctor) {
                    while (!list.isEmpty()) {
                        Doctor docObj = (Doctor) list.dequeue();
                        if (docObj.getDocID().equalsIgnoreCase(keyword) || docObj.getDocName().equalsIgnoreCase(keyword) ||
                            docObj.getSpecialty().equalsIgnoreCase(keyword)) {
                            System.out.println(" "+(countFound+1)+"] "+docObj.toString()); 
                            System.out.println("+---------------------------------------------------------+");
                            keyFound.enqueue(counter);
                            countFound++;
                        }
                        counter++;
                        temp.enqueue(docObj);
                    }
                    while (!temp.isEmpty()) {
                        list.enqueue(temp.dequeue());
                    }
                }
                else if (list.getFirst() instanceof Invoice) {
                    while (!list.isEmpty()) {
                        Invoice invObj = (Invoice) list.dequeue();
                        if (invObj.getInvID().equalsIgnoreCase(keyword) || invObj.getPatNRIC().equalsIgnoreCase(keyword) ||
                            invObj.getPatNRIC().equals(keyword)) {
                            System.out.println(" "+(countFound+1)+"] "+invObj.toString()); 
                            System.out.println("+------------------------------------------+");
                            keyFound.addLast(counter);
                            countFound++;
                        }
                        counter++;
                        temp.enqueue(invObj);
                    }
                    while (!temp.isEmpty()) {
                        list.enqueue(temp.dequeue());
                    }
                }
                
                if (countFound == 0) {
                    System.out.println(" No data matches with the keyword : "+keyword); 
                    System.out.println("+---------------------------------------------------------+");
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
                        Object numKey = (Object) list.getFront(); // holding next data
                        while (!keyFound.isEmpty()) {
                            int currentNum = (int) keyFound.dequeue();
                            if (counter2 == num) {
                                numKey = (int) currentNum;
                            }
                            counter2++;
                            temp.enqueue(currentNum);
                        }
                        while (!temp.isEmpty()) {
                            keyFound.enqueue(temp.dequeue());
                        }
                        displayData(list,(int) numKey);
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
    }
}
