
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
    private String time;
    private String status;
    
    public Appointment(String appID,String patID,String docID,String date,String time,String status) {
        this.appID=appID;
        this.docID=docID;
        this.patID=patID;
        this.date=date;
        this.time=time;
        this.status=status;
    }
    
    // appID is restricted from any changes
    public void setPatID(String patID) { this.patID=patID; }
    public void setDocID(String docID) { this.docID=docID; }
    public void setDate(String date) { this.date=date; }
    public void setTime(String time) { this.time=time; }
    
    public String getAppID() { return this.appID; }
    public String getPatID() { return this.patID; }
    public String getDocID() { return this.docID; }
    public String getDate() { return this.date; }
    public String getTime() { return this.time; }
    public String getStatus() { return this.status; }
    
    // for unformatted data inspecting
    public String toString() {
        return " App ID : "+this.appID+" | Date : "+this.date+" | Time : "+this.time+" | Status : "+this.status;
    }
    public String toStringAll() {
        return " App ID : "+this.appID+" | Pat ID : "+this.patID+" | Doc ID : "+this.docID
            +" | Date : "+this.date+" | Time : "+this.time+" | Status : "+this.status;
    }
    // for displaying data in beautified format
    public String toStringFormatted() {
        // Patient and Doctor's data is not local
        return " Appointment ID : "+this.appID+
            "\n Date : "+this.date+" | Time : "+this.time+
            "\n Status : "+this.status;
    }
    // for storing data to clinicdata.txt
    public String txtFormat() {
        return "APPOINTMENT;"+this.appID+";"+this.patID+";"+this.docID+";"+this.date+
            ";"+this.time+";"+this.status;
    }
}
