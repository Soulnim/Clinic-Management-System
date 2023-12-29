public class Medicine {
    private String medicineID;
    private String medicineName;
    private String packagingDesc;
    private String manufacturer;
    private double price;

    // Constructor
    public Medicine(String medicineID,String medicineName,String packagingDesc,String manufacturer,double price) {
        this.medicineID=medicineID;
        this.medicineName=medicineName;
        this.packagingDesc=packagingDesc;
        this.manufacturer=manufacturer;
        this.price=price;
    }
        
    // Setter/Mutator
    public void setMedicineID(String medicineID) {this.medicineID=medicineID;}
    public void setMedicineName(String medicineName) {this.medicineName=medicineName;}
    public void setPackagingDesc(String packagingDesc) {this.packagingDesc=packagingDesc;}
    public void setManufacturer(String manufacturer) {this.manufacturer=manufacturer;}
    public void setPrice(double price) {this.price=price;}

    // Getter/Accessor
    public String getPaymentID() {return this.medicineName;}
    public double getPrice() {return this.price;} 
    // to be determined

    // Printer
    public String toString(){
        return "Name : "+this.medicineName+"Price : "+this.price; // to be determined
    }
}