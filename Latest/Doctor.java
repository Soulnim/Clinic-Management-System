
/**
 * Write a description of class Doctor here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Doctor
{
    private String docID;
    private String docName;
    private String docPhone;
    private String specialty;
    
    public Doctor() {
        docID="";
        docName="";
        docPhone="";
        specialty="";
    }
    
    public Doctor(String docID,String docName,String specialty,String docPhone) {
        this.docID=docID;
        this.docName=docName;
        this.docPhone=docPhone;
        this.specialty=specialty;
    }
    
    public void setDocName(String docName) { this.docName=docName; }
    public void setDocPhone(String docPhone) { this.docPhone=docPhone; }
    public void setSpecialty(String specialty) { this.specialty=specialty; }
    
    public String getDocID() { return this.docID; }
    public String getDocName() { return this.docName; }
    public String getSpecialty() { return this.specialty; }
    public String getDocPhone() {return this.docPhone; }
    
    // for unformatted data inspecting
    public String toString() {
        return " Name : "+this.docName+" | Specialty : "+this.specialty;
    }
    // for displaying data in beautified format
    public String toStringFormatted() {
        return " Doctor ID : "+this.docID+
            "\n Doctor Name : "+this.docName+" | Phone : "+this.docPhone+
            "\n Specialty : "+this.specialty;
    }
    // for storing to clinicdata.txt
    public String txtFormat() {
        return "DOCTOR;"+this.docID+";"+this.docName+";"+this.specialty+";"+this.docPhone;
    }
}
