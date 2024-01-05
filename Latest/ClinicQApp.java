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
                    String time = st.nextToken();
                    String status = st.nextToken();
                    appQueue.enqueue(new Appointment(appID,patID,docID,date,time,status));
                }
                else if (dataType.equals("PATIENT")) {
                    String patID = st.nextToken();
                    String NRIC = st.nextToken();
                    String patName = st.nextToken();
                    patQueue.enqueue(new Patient(patID,NRIC,patName));
                }
                else if (dataType.equals("DOCTOR")) {
                    String docID = st.nextToken();
                    String name = st.nextToken();
                    String specialty = st.nextToken();
                    docQueue.enqueue(new Doctor(docID,name,specialty));
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
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
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
    
    public static void dashboard(Queue appQueue, Queue patQueue, Queue docQueue) {
        System.out.print("\f");
        System.out.println("+------------------------------------------+");
        System.out.println("|                MAIN MENU                 |");
        System.out.println("+------------------------------------------+");
        System.out.println("|     A] Schedule New Appointment          |");
        System.out.println("|     B] Manage Appointment                |");
        System.out.println("|     C] Manage Patient                    |");
        System.out.println("|     D] Manage Invoice                    |");
        System.out.println("|     E] View Doctor's Info                |");
        System.out.println("|     F] Exit                              |");
        System.out.println("+------------------------------------------+");
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
            // to be determined
        }
        else if (option == 'E' || option == 'e') {
            displayList(docQueue,null);
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
    
    public static void displayList(Queue queue, Queue queue2) {
        int page = 1;
        int floor = 0;
        boolean showCompleted = true;
        
        while (true) {
            // Check for queue type
            Object object = (Object) queue.getFront();
            if (object != null) {
                if (object instanceof Appointment) {
                    System.out.print("\f");
                    System.out.println("+------------------------------------------+");
                    System.out.println("|             APPOINTMENT LIST             |");
                    System.out.println("+------------------------------------------+");
                } else if (object instanceof Patient) {
                    System.out.print("\f");
                    System.out.println("+------------------------------------------+");
                    System.out.println("|               PATIENT LIST               |");
                    System.out.println("+------------------------------------------+");
                } else if (object instanceof Doctor) {
                    System.out.print("\f");
                    System.out.println("+------------------------------------------+");
                    System.out.println("|                DOCTOR LIST               |");
                    System.out.println("+------------------------------------------+");
                }
            } else {
                System.out.print("\f");
                System.out.println("+------------------------------------------+");
                System.out.println("|              List is empty!              |");
                System.out.println("+------------------------------------------+");
                System.out.print(" Press [Enter] to continue");
                String enter = inText.nextLine();
                return;
            }

            int counter = 0;
            // Check if the queue is appQueue
            object = (Object) queue.getFront();
            if (object instanceof Appointment) {
                Queue tempApp = new Queue();
                Queue tempPat = new Queue();
                while (!queue.isEmpty() && counter != floor) {
                    Object current = (Object) queue.dequeue();
                    tempApp.enqueue(current);
                    counter++;
                }
                while (!queue.isEmpty() && counter < (page * 10)) {
                    Appointment appObj = (Appointment) queue.dequeue();
                    tempApp.enqueue(appObj);
                    if (showCompleted || appObj.getStatus().equals("Pending")) {
                        System.out.println(" " + (counter + 1) + "] " + appObj.toString());
                        System.out.println("+------------------------------------------+");
                    }
                    counter++;
                }
                // store temp's data back to its original list
                while (!tempApp.isEmpty()) {
                    queue.enqueue(tempApp.dequeue());
                }
                while (!tempPat.isEmpty()) {
                    queue2.enqueue(tempPat.dequeue());
                }
            } else {
                Queue temp = new Queue();
                while (!queue.isEmpty() && counter != floor) {
                    Object current = queue.dequeue();
                    temp.enqueue(current);
                    counter++;
                }
                while (!queue.isEmpty() && counter < (page * 10)) {
                    object = queue.dequeue();
                    temp.enqueue(object);
                    System.out.println(" " + (counter + 1) + "] " + object.toString());
                    counter++;
                    System.out.println("+------------------------------------------+");
                }
                // store temp's data back to its original list
                while (!temp.isEmpty()) {
                    queue.enqueue(temp.dequeue());
                }
            }

            // Check total page
            System.out.println(" Enter index (1..) to choose -");
            if (page == 1) {
                if (object instanceof Appointment) {
                    if (showCompleted) {
                        System.out.println(" [V] Next , [H] Home, [K] Hide 'Completed'");
                    } else {
                        System.out.println(" [V] Next , [H] Home, [K] Show 'Completed'");
                    }
                } else {
                    System.out.println(" [V] Next , [H] Home");
                }
            } else {
                System.out.println(" [C] Previous , [V] Next , [H] Home");
            }
            System.out.println("+------------------------------------------+ Page : " + page);
            System.out.print(" Option : ");
            String option = inText.nextLine();
            if (option.equalsIgnoreCase("V")) {
                page++;
                floor += 10;
            } else if (option.equalsIgnoreCase("C")) {
                if (page - 1 < 1) {
                    System.out.println("+------------------------------------------+");
                    System.out.println("|            Page limit reached!           |");
                    System.out.println("+------------------------------------------+");
                    System.out.print(" Press [Enter] to continue");
                    String enter = inText.nextLine();
                } else {
                    page--;
                    floor -= 10;
                }
            } else if (option.equalsIgnoreCase("H")) {
                break;
            }
            else if (option.equalsIgnoreCase("K")) {
                if (object instanceof Appointment) {
                    showCompleted = !showCompleted;
                }
            } else {
                try {
                    int key = Integer.parseInt(option);
                    if (key > 0 && key <= counter) {
                        displayData(queue, key);
                    }
                    else {
                        System.out.println("+------------------------------------------+");
                        System.out.println("|               Invalid key!               |");
                        System.out.println("+------------------------------------------+");
                        System.out.print(" Press [Enter] to continue");
                        String enter = inText.nextLine();
                    }
                } catch (NumberFormatException e) {
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
                System.out.println("|           [A] Edit, [C] Back             |");
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
                System.out.println("|                DOCTOR DATA               |");
                System.out.println("+------------------------------------------+");
                System.out.println(" "+docObj.toStringFormatted());
                System.out.println("+------------------------------------------+");
                System.out.println("|         Press [Enter] to continue        |");
                System.out.println("+------------------------------------------+");
                String enter = inText.nextLine();
                break;
            }
        }
    }
    
    // ADD DATA TO LIST
    public static void addData(Queue list, Queue list2) {
        if (list.getFront() instanceof Appointment) {
            System.out.print("\f");
            System.out.println("+------------------------------------------+");
            System.out.println("|            ADD NEW APPOINTMENT           |");
            System.out.println("+------------------------------------------+");
            Patient pat = new Patient(); // placeholder var
            Doctor doc = new Doctor(); // placeholder var
            System.out.print(" NRIC : ");
            String NRIC = inText.nextLine();
            if (!patientIsExist(list2,NRIC)) {
                System.out.print(" Patient Name : ");
                String patName = inText.nextLine();
                pat = new Patient("P"+generateID(list),NRIC,patName);
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
                Queue temp = new Queue();
                while (!docQueue.isEmpty()) {
                    Doctor docObj = (Doctor) docQueue.dequeue();
                    System.out.println(" "+(counter+1)+"]"+docObj.toStringFormatted());
                    counter++;
                    System.out.println("+------------------------------------------+");
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
            list.enqueue(new Appointment("A"+generateID(list),pat.getPatID(),doc.getDocID(),date,time,"Pending"));
            
            System.out.println("+------------------------------------------+");
            System.out.println("|           Data has been added!           |");
            System.out.println("+------------------------------------------+");
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
                        Doctor currentDoc = (Doctor) docQueue.getFirst();
                        while (currentDoc != null) {
                            System.out.println(" "+(countDoc+1)+"] "+currentDoc.toStringFormatted());
                            currentDoc = (Doctor) docQueue.getNext();
                            countDoc++;
                            System.out.println("+------------------------------------------+");
                        }
                        System.out.print(" Option : ");
                        int optionDoc = inNum.nextInt();
                        if (optionDoc <= countDoc && optionDoc >= 0) {
                            int counter2 = 1;
                            currentDoc = (Doctor) docQueue.getFirst();
                            while (counter2 < optionDoc) {
                                currentDoc = (Doctor) docQueue.getNext();
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
                // to be determined
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
        Queue temp = new Queue();
        Object object = new Object();
        if (id.substring(0,1).equals("A")) {
            while (!appQueue.isEmpty()) {
                Appointment appObj = (Appointment) appQueue.dequeue();
                if (appObj.getAppID().equals(id)) {
                    object = appObj;
                }
                temp.enqueue(appObj);
            }
            while (!temp.isEmpty()) {
                appQueue.enqueue(temp.dequeue());
            }
        }
        else if (id.substring(0,1).equals("P")) {
            while (!patQueue.isEmpty()) {
                Patient patObj = (Patient) patQueue.dequeue();
                if (patObj.getPatID().equals(id)) {
                    object = patObj;
                }
                temp.enqueue(patObj);
            }
            while (!temp.isEmpty()) {
                patQueue.enqueue(temp.dequeue());
            }
        }
        else if (id.substring(0,1).equals("D")) {
            while (!docQueue.isEmpty()) {
                Doctor docObj = (Doctor) docQueue.dequeue();
                if (docObj.getDocID().equals(id)) {
                    object = docObj;
                }
                temp.enqueue(docObj);
            }
            while (!temp.isEmpty()) {
                docQueue.enqueue(temp.dequeue());
            }
        }
        return object;
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
