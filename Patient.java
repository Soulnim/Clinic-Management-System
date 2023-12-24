public class Patient {
    private String patientID;
    private String NRIC;
    private String patientName;
    private int patientAge;

    // Constructor    
    public Patient(String patientID, String NRIC, String patientName, int patientAge) {
        this.patientID=patientID;
        this.NRIC=NRIC;
        this.patientName=patientName;
        this.patientAge=patientAge;
    }

    // Setter/Mutator
    public void setPatientID(String patientID) { this.patientID=patientID; }
    public void setNRIC(String NRIC) {this.NRIC=NRIC;}
    public void setPatientName(String name) {this.patientName=patientName;}
    public void setPatientAge(int age) {this.patientAge=patientAge;}

    // Getter/Accessor
    public String getPatientID() { return this.patientID; }
    public String getNRIC() {return this.NRIC;}
    public String getPatientName() {return  this.patientName;}
    public int getPatientAge() {return  this.patientAge;}

    // Printer
    public String toString() {
      return "Patient ID : "+this.patientID+"NRIC: "+this.NRIC+"PatientName : "+
      this.patientName+"PatientAge:  "+this.patientAge;   
    }
}
