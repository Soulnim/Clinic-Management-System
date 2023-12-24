public class Doctor {
    private String doctorID;
    private String doctorName;
    private String specialty;
    private String phone;

    // Constructor
    public Doctor(String doctorID, String doctorName, String specialty, String phone) {
        this.doctorName=doctorName;
        this.doctorID=doctorID;
        this.specialty=specialty;
        this.phone=phone;
    }

    // Setter/Mutator
    public void setDoctorName(String doctorName) {this.doctorName=doctorName;}
    public void setDoctorID(String doctorID) {this.doctorID=doctorID;}
    public void setSpecialty(String specialty) {this.specialty=specialty;}
    public void setPhone(String phone) {this.phone=phone;}

    // Getter/Accessor
    public String getDoctorName() {return this.doctorName;}
    public String getDoctorID() {return this.doctorID;}
    public String getSpecialty() {return this.specialty;}
    public String getPhone() {return this.phone;}
     
    // Printer
    public String toString() {
       return "DoctorID : "+this.doctorID+"Doctor Name: "+this.doctorName+"Specialty :    "+
       this.specialty+"Phone : "+this.phone;
    }
}
