
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
    private String specialty;
    
    public Doctor() {
        docID="";
        docName="";
        specialty="";
    }
    
    public Doctor(String docID,String docName,String specialty) {
        this.docID=docID;
        this.docName=docName;
        this.specialty=specialty;
    }
    
    public void setDocName(String docName) { this.docName=docName; }
    public void setSpecialty(String specialty) { this.specialty=specialty; }
    
    public String getDocID() { return this.docID; }
    public String getDocName() { return this.docName; }
    public String getSpecialty() { return this.specialty; }
    
    // for unformatted data inspecting
    public String toString() {
        return " Doctor ID : "+this.docID+" | Name : "+this.docName+" | Specialty : "+this.specialty;
    }
    // for displaying data in beautified format
    public String toStringFormatted() {
        return " Doctor ID : "+this.docID+
            "\n Doctor Name : "+this.docName+" | Specialty : "+this.specialty;
    }
    // for storing to clinicdata.txt
    public String txtFormat() {
        return "DOCTOR;"+this.docID+";"+this.docName+";"+this.specialty;
    }
}
