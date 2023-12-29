public class Appointment {
    private String appID;
    private String patientID;
    private String doctorID;
    private String invoiceID;
    private String date;
    private String time;
    private String type;

    // Constructor
    public Appointment(String appID,String patientID,String doctorID,String invoiceID,
        String date,String time,String type) {
        this.appID=appID;
        this.patientID=patientID;
        this.doctorID=doctorID;
        this.invoiceID=invoiceID;
        this.date=date;
        this.time=time;
        this.type=type;
    }

    // Setter/Mutator
    public void setAppID(String appID) { this.appID=appID; }
    public void setPatientID(String patientID) { this.patientID=patientID; }
    public void setDoctorID(String doctorID) { this.doctorID=doctorID; }
    public void setInvoiceID(String invoiceID) { this.invoiceID=invoiceID; }
    public void setDate(String date) { this.date=date; }
    public void setTime(String time) { this.time=time; }
    public void setType(String type) { this.type=type; }

    // Getter/Accessor
    public String getAppID() { return this.appID; }
    public String getPatientID() { return this.patientID; }
    public String getDoctorID() { return this.doctorID; }
    public String getInvoiceID() { return this.invoiceID; }
    public String getDate() { return this.date; }
    public String getTime() { return this.time; }
    public String getType() { return this.type; }

    // Printer
    public String toString() {
        return "Appointment ID : "+this.appID+"| Patient ID : "+this.patientID+" | Doctor ID : "+this.doctorID+
            "\nInvoice ID : "+this.invoiceID+" | Date : "+this.date+" | Time : "+this.time+" | Type : "+this.type;
    }
    public String toString2() {
        return " Appointment ID : "+this.appID+
                "\n Date : "+this.date+" | Time : "+this.time+
                "\n Type : "+this.type;
    }
    public String rawData() {
        return "Appointment;"+this.appID+";"+this.patientID+";"+this.doctorID+";"+this.invoiceID+";"+
            this.date+";"+this.time+";"+this.type;
    }
}