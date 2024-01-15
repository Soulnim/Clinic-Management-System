
/**
 * Write a description of class Invoice here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Invoice
{
    private Appointment app;
    private Patient pat;
    private Doctor doc;
    //private double paymentAmount;
    private int paymentMethod;
    private String paymentStatus; // Pending, Succesful
    
    public Invoice(Appointment a,Patient p,Doctor d,int pm,String ps) {
        this.app=a;
        this.pat=pat;
        this.doc=d;
        this.paymentMethod=pm;
        this.paymentStatus=ps;
    }
    
    public void setPayMethod(int pm) { this.paymentMethod=pm; }
    public void setPayStatus(String ps) { this.paymentStatus=ps; }
    
    public String getPayMethod() {
        String desc = "";
        if (paymentMethod==0) { desc="Not set"; }
        else if (paymentMethod==1) { desc="Cash"; }
        else if (paymentMethod==2) { desc="Debit"; }
        return desc;
    }
    
    public String toString() {
        return "To be determined";
    }
}
