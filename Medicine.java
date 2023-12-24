public class Medicine {
    private String medicineName;
    private double price;
    private String type;

     // Constructor
     public Medicine(String medicineName, double price, String type) {
         this.medicineName=medicineName;
         this.price=price;
         this.type=type;
        }
     // Setter/Mutator
     public void setMedicineName(String medicineName) {this.medicineName=medicineName;}
     public void setPrice(double price) {this.price=price;}
     public void setType(String type) {this.type=type;}

     // Getter/Accessor
     public String getPaymentID() {return this.medicineName;}
     public double getPrice() {return this.price;}
     public String getType() {return this.type;}

     // Printer
     public String toString(){
         return "Name : "+this.medicineName+"Price : "+this.price+"Type :    "+this.type;
    }
}
