public class Doctor {
    private String doctorID;
    private String doctorName;
    private String specialty;
    private String phoneNum;

    // Constructor
    public Doctor(String doctorID, String doctorName, String specialty, String phoneNum) {
        this.doctorID=doctorID;
        this.doctorName=doctorName;
        this.specialty=specialty;
        this.phoneNum=phoneNum;
    }

    // Setter/Mutator
    public void setDoctorName(String doctorName) {this.doctorName=doctorName;}
    public void setDoctorID(String doctorID) {this.doctorID=doctorID;}
    public void setSpecialty(String specialty) {this.specialty=specialty;}
    public void setPhoneNum(String phoneNum) {this.phoneNum=phoneNum;}

    // Getter/Accessor
    public String getDoctorName() {return this.doctorName;}
    public String getDoctorID() {return this.doctorID;}
    public String getSpecialty() {return this.specialty;}
    public String getPhoneNum() {return this.phoneNum;}
     
    // Printer
    public String toString() {
       return "DoctorID : "+this.doctorID+"Doctor Name: "+this.doctorName+"Specialty :    "+
       this.specialty+"Phone Number : "+this.phoneNum;
    }
    public String toString2() {
        return " Doctor ID : "+this.doctorID+
                "\n Doctor Name : "+this.doctorName+" | Specialty : "+this.specialty+
                "\n Phone Number : "+this.phoneNum;
    }
    public String rawData() {
        return "Doctor;"+this.doctorID+";"+this.doctorName+";"+this.specialty+";"+this.phoneNum;
    }
}