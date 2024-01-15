
/**
 * Write a description of class Invoice here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Invoice
{
    private String invID;
    private Appointment app;
    private Patient pat;
    private Doctor doc;
    private int paymentMethod;
    private String paymentStatus; // Pending, Succesful
    
    public Invoice(String id,Appointment a,Patient p,Doctor d,int pm,String ps) {
        this.invID=id;
        this.app=a;
        this.pat=p;
        this.doc=d;
        this.paymentMethod=pm;
        this.paymentStatus=ps;
    }
    
    public void setPayMethod(int pm) { this.paymentMethod=pm; }
    public void setPayStatus(String ps) { this.paymentStatus=ps; }
    
    public String getInvID() { return this.invID; }
    public String getPayMethod() {
        String desc = "";
        if (paymentMethod==1) { desc="Cash"; }
        else if (paymentMethod==2) { desc="Debit"; }
        else { desc="Not set"; }
        return desc;
    }
    public double getPayAmount() {
        double amount = 0;
        String cat = this.app.getCategory();
        if (cat.equals("Medical Checkup")) { amount = 50; }
        else if (cat.equals("Pregnancy Test")) { amount = 60; }
        return amount;
    }
    
    public String toString() {
        return " NRIC : "+pat.getNRIC()+" | Payment status : "+this.paymentStatus;
    }
    public String toStringFormatted() {
        return app.toStringFormatted()+
            "\n+------------------------------------------+\n"+
               pat.toStringFormatted()+
            "\n+------------------------------------------+\n"+
               doc.toStringFormatted()+
            "\n+------------------------------------------+\n"+
               " Invoice ID : "+this.getInvID()+
               "\n Payment amount : RM "+this.getPayAmount()+
               "\n Payment method : "+this.paymentMethod+
               "\n Status : "+this.paymentStatus;
    }
    public String txtFormat() {
        return "INVOICE;"+this.invID+";"+this.pat.getPatID()+";"+this.doc.getDocID()+";"+
            this.paymentMethod+";"+this.paymentStatus+
            "\n"+app.txtFormat();
    }
}
