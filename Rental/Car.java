package Rental;

import BasicIO.*;

/**
 * Creating a car object which will store license plate,
 * mileage and the category of the car.
 */
public class Car {

 public static final String[] CATEGORIES = {"Economy", "Full Size", "Van", "SUV"};
 private final double[] RATES = {0.25, 0.50, 1.00, 1.25};

 private String licence; // licence plate
 private int mileage;  // mileage on car
 private int category; // category of the car

 public Car(ASCIIDataFile in) {
  licence = in.readString();
  if (!in.isEOF()) {//not end of file
   mileage = in.readInt();
   category = in.readInt();
  }
 }

 /**
  * returns the licence of the car.
  *
  * @return the licence of the car.
  */
 public String getLicence() {
  return licence;
 }

 /**
  * returns the mileage of the car.
  *
  * @return the mileage of the car.
  */
 public int getMileage() {
  return mileage;
 }

 /**
  * returns the category of the car.
  *
  * @return the category of the car.
  */
 public int getCategory() {
  return category;
 }

 /**
  * returns the rate of the car.
  *
  * @return the rate of the car.
  */
 public double getRate() { return RATES[getCategory()];
 }

 /**
  * Will update the mileage after a car is returned and calculate the charge.
  * m the new mileage.
  * return the charge of mileage usage.
  */
 public double returned(int m) {
  double charge = 0;
  charge = (m - mileage) * getRate();
  mileage = m;
  return charge;
 }
}
