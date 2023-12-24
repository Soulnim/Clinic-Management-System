public class Appointment {
    private String appID;
    private String patientID;
    private String date;
    private String time;
    private String type;

    // Constructor
    public Appointment(String appID,String date,String time,String type) {
        this.appID=appID;
        this.date=date;
        this.time=time;
        this.type=type;
    }

    // Setter/Mutator
    public void setAppID(String appID) { this.appID=appID; }
    public void setDate(String date) { this.date=date; }
    public void setTime(String time) { this.time=time; }
    public void setType(String type) { this.type=type; }

    // Getter/Accessor
    public String getAppID() { return this.appID; }
    public String getDate() { return this.date; }
    public String getTime() { return this.time; }
    public String getType() { return this.type; }

    // Printer
    public String toString() {
        return "Appointment ID : "+this.appID+"Date : "+this.date+"Time : "+this.time
            +"Type : "+this.type;
    }
}
