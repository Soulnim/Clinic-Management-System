// Subject to be change
// Reacord => Disease
// Used to identify the suitable treatment for patient with specific disease
// an their suitable type and amount of medicine

public class Record {
    private  Doctor doctor;
    private  Appointment appointment;

    //Constructor
    public Record (Doctor doctor, Appointment appointment ){
       this.doctor=doctor;
       this.appointment = appointment;
    }
    // Setter/Mutator
    public void setDoctor(Doctor doctor) {this.doctor=doctor;}
    public void setAppointment(Appointment appointment) {this.appointment = appointment;}
     
    // Getter/Accessor
    public Doctor getDoctor() {return this.doctor;}
    public Appointment getAppointment() {return this.appointment;}

    //Printer
    public String toString() {
        return "Doctor : "+this.doctor+"Appointment : "+this.appointment;
    }
}
