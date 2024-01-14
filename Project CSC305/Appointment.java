
/**
 * Write a description of class Appointment here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Appointment
{
    private String apptID;
    private String NRIC;
    private String date;
    private String time;
    private int category;
    private boolean payStatus;
    private int payMethod;
    
    public Appointment() {
        apptID="Not set";
        NRIC="Not set";
        date="Not set";
        time="Not set";
        category=0;
        payStatus=false;
        payMethod=0;
    }
    public Appointment(String a,String b,String c,String d,int e) {
        this.apptID = a;
        this.NRIC = b;
        this.date = c;
        this.time = d;
        this.category=e;
        this.payStatus=false;
        this.payMethod=0;
    }
    
    public void setApptID(String a) { this.apptID = a; }
    public void setDate(String b) { this.date = b; }
    public void setTime(String c) { this.time = c; }
    public void setCategory(int d) { this.category = d; }
    public void setPayStatus(boolean e) { this.payStatus = e; }
    public void setPayMethod(int f) { this.payMethod = f; }
    
    public String getApptID() { return this.apptID; }
    public String getDate() { return this.date; }
    public String getTime() { return this.time; }
    public int getCategory() { return this.category; }
    public boolean getPayStatus() { return this.payStatus; }
    public int getPayMethod() { return this.payMethod; }
    
    public double getPayAmount() {
        double amount = 0;
        if (this.category == 1) { amount = 50; }
        else if (this.category == 2) { amount = 60; }
        else if (this.category == 3) { amount = 50; }
        else if (this.category == 4) { amount = 60; }
        else if (this.category == 5) { amount = 30; }
        return amount;
    }
    public String categoryDesc() {
        String catDesc = "";
        if (this.category == 1) { catDesc = "Medical Checkup"; }
        else if (this.category == 2) { catDesc = "Pregnancy Test"; }
        else if (this.category == 3) { catDesc = "Vaccination"; }
        else if (this.category == 4) { catDesc = "Blood Test"; }
        else if (this.category == 5) { catDesc = "Eye Test"; }
        return catDesc;
    }
    public String payMethodDesc() {
        String payDesc = "";
        if (this.payMethod == 0) { payDesc = "Not set"; }
        else if (this.payMethod == 1) { payDesc = "Cash"; }
        else if (this.payMethod == 2) { payDesc = "Debit"; }
        return payDesc;
    }
    public String payStatDesc() {
        String paidDesc = "";
        if (this.payStatus == false) { paidDesc = "Pending"; }
        else { paidDesc = "Successful"; }
        return paidDesc;
    }
    
    public String toString() {
        return " Appointment ID : "+this.apptID+" | Date : "+this.date+" | Time : "+this.time+
            "\n Category : "+this.categoryDesc()+" | Payment amount : RM "+this.getPayAmount()+
            "\n Payment status : "+this.payStatDesc()+" | Payment method : "+this.payMethodDesc();
    }
}
