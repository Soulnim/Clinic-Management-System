
/**
 * Write a description of class Appointment here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Appointment
{
    private String appID;
    private String patID;
    private String docID;
    private String date;
    private int slot; // 1-6
    private String category;
    
    public Appointment() {
        appID="";
        patID="";
        docID="";
        date="";
        slot=0;
        category="";
    }
    
    public Appointment(String appID,String patID,String docID,String date,int slot,String category) {
        this.appID=appID;
        this.docID=docID;
        this.patID=patID;
        this.date=date;
        this.slot=slot;
        this.category=category;
    }
    
    // appID is restricted from any changes
    public void setPatID(String patID) { this.patID=patID; }
    public void setDocID(String docID) { this.docID=docID; }
    public void setDate(String date) { this.date=date; }
    public void setSlot(int slot) { this.slot=slot; }
    
    public String getAppID() { return this.appID; }
    public String getPatID() { return this.patID; }
    public String getDocID() { return this.docID; }
    public String getDate() { return this.date; }
    public String getTime() {
        if (this.slot == 1) { return "1000"; }
        else if (this.slot == 2) { return "1100"; }
        else if (this.slot == 3) { return "1200"; }
        else if (this.slot == 4) { return "1300"; }
        else if (this.slot == 5) { return "1400"; }
        else { return "1500"; }
    }
    public int getSlot() { return this.slot; }
    public String getCategory() { return this.category; }
    
    // for unformatted data inspecting
    public String toString() {
        return " | Date : "+this.date+" | Time : "+this.getTime()+" | Status : "+this.category;
    }
    public String toStringAll() {
        return " App ID : "+this.appID+" | Pat ID : "+this.patID+" | Doc ID : "+this.docID
            +" | Date : "+this.date+" | Time : "+this.getTime()+" | Slot : "+this.slot+" | Status : "+this.category;
    }
    // for displaying data in beautified format
    public String toStringFormatted() {
        // Patient and Doctor's data is not local
        return " Appointment ID : "+this.appID+
            "\n Date : "+this.date+" | Time : "+this.getTime()+
            "\n Status : "+this.category;
    }
    // for storing data to clinicdata.txt
    public String txtFormat() {
        return "APPOINTMENT;"+this.appID+";"+this.patID+";"+this.docID+";"+this.date+
            ";"+this.slot+";"+this.category;
    }
}
