
/**
 * Write a description of class Patient here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Patient
{
    private String patID;
    private String NRIC;
    private String patName;
    private String patPhone;
    
    public Patient() {
        patID="";
        NRIC="";
        patName="";
        patPhone="";
    }
    
    public Patient(String patID,String NRIC,String patName,String patPhone) {
        this.patID=patID;
        this.NRIC=NRIC;
        this.patName=patName;
        this.patPhone=patPhone;
    }
    
    public void setNRIC(String NRIC) { this.NRIC=NRIC; }
    public void setPatName(String patName) { this.patName=patName; }
    public void setPatPhone(String patPhone) { this.patPhone=patPhone; }
    
    public String getPatID() { return this.patID; }
    public String getNRIC() { return this.NRIC; }
    public String getPatName() { return this.patName; }
    public String getPatPhone() { return this.patPhone; }
    
    // for data inspecting
    public String toString() {
        return " NRIC : "+this.NRIC+" | Patient Name : "+this.patName;
    }
    // to display data with beautified format
    public String toStringFormatted() {
        return " Patient ID : "+this.patID+
            "\n NRIC : "+this.NRIC+" | Patient Name : "+this.patName+
            "\n Phone : "+this.patPhone;
    }
    // for storing purpose
    public String txtFormat() {
        return "PATIENT;"+this.patID+";"+this.NRIC+";"+this.patName+";"+this.patPhone;
    }
}
