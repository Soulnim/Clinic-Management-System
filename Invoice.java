public class Invoice {
   private String invoiceID;
   private double invoiceValue;
   private String paymentMethod;

   // Constructor
   public Invoice(String invoiceID, double invoiceValue, String paymentMethod) {
       this.invoiceID=invoiceID;
       this.invoiceValue=invoiceValue;
       this.paymentMethod=paymentMethod;
    }

   // Setter/Mutator
   public void setInvoiceID(String invoiceID) {this.invoiceID=invoiceID;}
   public void setInvoiceValue(double InvoiceValue) {this.invoiceValue=invoiceValue;}
   public void setPaymentMethod(String paymentMethod)  {this.paymentMethod=paymentMethod;}

    // Getter/Accessor
    public String getInvoiceID() {return this.invoiceID;}
    public double getInvoiceValue() {return this.invoiceValue;}
    public String getPaymentMethod() {return this.paymentMethod;}

    // Printer
    public String toString() {
       return "Invoice ID : "+this.invoiceID+"Invoice Value : "+this.invoiceValue+
       "Payment Method : "+this.paymentMethod;
    }
}
