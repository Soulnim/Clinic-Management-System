public class Invoice {
    private String invoiceID;
    private String appID;
    private double amount;
    private String paymentMethod;

    // Constructor
    public Invoice(String invoiceID,String appID,double amount,String paymentMethod) {
        this.invoiceID=invoiceID;
        this.appID=appID;
        this.amount=amount;
        this.paymentMethod=paymentMethod;
    }

    // Setter/Mutator
    public void setInvoiceID(String invoiceID) {this.invoiceID=invoiceID;}
    public void setAppointmentID(String appID) {this.appID=appID;}
    public void setAmount(double amount) {this.amount=amount;}
    public void setPaymentMethod(String paymentMethod)  {this.paymentMethod=paymentMethod;}

    // Getter/Accessor
    public String getInvoiceID() {return this.invoiceID;}
    public String getAppID() {return this.appID;}
    public double getAmount() {return this.amount;}
    public String getPaymentMethod() {return this.paymentMethod;}

    // Printer
    public String toString() {
        return "Invoice ID : "+this.invoiceID+" Appointment ID : "+this.appID+" Amount : "+
           this.amount+" Payment Method : "+this.paymentMethod;
    }
    public String toString2() {
        return " Invoice ID : "+this.invoiceID+
                "\n Amount : RM "+this.amount+" | Payment Method : "+this.paymentMethod;
    }
    public String rawData() {
        return "Invoice;"+this.invoiceID+";"+this.appID+";"+this.amount+";"+this.paymentMethod;
    }
}