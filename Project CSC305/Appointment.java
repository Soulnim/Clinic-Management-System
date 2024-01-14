
/**
 * Write a description of class Appointment here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Appointment
{
    private String apptID;
    private String date;
    private String time;
    private int category;
    private boolean isPaid;
    private int payMethod;
    
    public Appointment() {
        apptID="Not set";
        date="Not set";
        time="Not set";
        category=0;
        isPaid=false;
        payMethod=0;
    }
    public Appointment(String a,String b,String c,int d) {
        this.apptID = a;
        this.date = b;
        this.time = c;
        this.category=d;
        this.isPaid=false;
        this.payMethod=0;
    }
    
    public void setApptID(String a) { this.apptID = a; }
    public void setDate(String b) { this.date = b; }
    public void setTime(String c) { this.time = c; }
    public void setCategory(int d) { this.category = d; }
    public void setIsPaid(boolean e) { this.isPaid = e; }
    public void setPayMethod(int f) { this.payMethod = f; }
    
    public String getApptID() { return this.apptID; }
    public String getDate() { return this.date; }
    public String getTime() { return this.time; }
    public int getCategory() { return this.category; }
    public boolean getIsPaid() { return this.isPaid; }
    public int getPayMethod() { return this.payMethod; }
    
    public double getPayAmount() {
        double amount = 0;
        if (this.category == 1) {
            amount = 50;
        }
        else if (this.category == 2) {
            amount = 60;
        }
        else if (this.category == 3) {
            // to be determined
        }
        return amount;
    }
    public String categoryDesc() {
        String catDesc = "";
        if (this.category == 1) {
            catDesc = "Medical Checkup";
        }
        else if (this.category == 2) {
            catDesc = "Pregnancy Test";
        }
        else if (this.category == 3) {
            // to be determined;
        }
        return catDesc;
    }
    public String payMethodDesc() {
        String payDesc = "";
        if (this.payMethod == 0) {
            payDesc = "Not set";
        }
        else if (this.payMethod == 1) {
            payDesc = "Cash";
        }
        else if (this.payMethod == 2) {
            payDesc = "Debit";
        }
        else if (this.payMethod == 3) {
            // to be determined
        }
        return payDesc;
    }
    
    public String toString() {
        return " Appointment ID : "+this.apptID+" | Date : "+this.date+" | Time : "+this.time+
            "\n Category : "+this.categoryDesc()+" | Payment amount : RM "+this.getPayAmount()+
            "\n | Paid : "+this.isPaid+" | Payment Method : "+this.payMethodDesc();
    }
}
