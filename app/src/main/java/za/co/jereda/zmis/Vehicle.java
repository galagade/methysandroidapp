package za.co.jereda.zmis;

/**
 * Created by kineo on 2016/03/31.
 */
public class Vehicle {

    String name,email, manufacture,type, colour;
    int year,contactnum, mileage;

    public Vehicle(String name, int contactnum,String email, String manufacture, String type, int year, String colour, int mileage){
        this.name = name;
        this.email = email;
        this.manufacture = manufacture;
        this.contactnum = contactnum;
        this.type =type;
        this.year = year;
        this.colour = colour;
        this.mileage = mileage;
    }


}
