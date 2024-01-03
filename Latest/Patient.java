
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
    
    public Patient() {
        patID="";
        NRIC="";
        patName="";
    }
    
    public Patient(String patID,String NRIC,String patName) {
        this.patID=patID;
        this.NRIC=NRIC;
        this.patName=patName;
    }
    
    public void setNRIC(String NRIC) { this.NRIC=NRIC; }
    public void setPatName(String patName) { this.patName=patName; }
    
    public String getPatID() { return this.patID; }
    public String getNRIC() { return this.NRIC; }
    public String getPatName() { return this.patName; }
    
    // for data inspecting
    public String toString() {
        return " NRIC : "+this.NRIC+" | Patient Name : "+this.patName;
    }
    // to display data with beautified format
    public String toStringFormatted() {
        return " Patient ID : "+this.patID+
            "\n NRIC : "+this.NRIC+" | Patient Name : "+this.patName;
    }
    // for storing purpose
    public String txtFormat() {
        return "PATIENT;"+this.patID+";"+this.NRIC+";"+this.patName;
    }
}
