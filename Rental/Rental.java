package Rental;

import BasicIO.*;
import static BasicIO.Formats.*;
public class Rental {

 private Node avail = null;   // list of available cars
 private Node rented = null;  // list of rented cars
 private ASCIIDisplayer display = null; // for displaying the lists
 private ASCIIDataFile input = null; // file that holds the cars' info
 private BasicForm form = null;  // for user interaction


 public Rental() {
  int button = 0;//which button will be pressed.
  display = new ASCIIDisplayer();
  form = new BasicForm("Rent", "Return", "List", "Quit");
  loadCars();
  setupForm();

  //get user input
  while (true) {
   form.clearAll();
   button = form.accept();
   if (button == 0) {  // Rent
    doRent();
   } else if (button == 1) { // Return
    doReturn();
   } else if (button == 2) { // List
    doList();
   } else {    // Quit
    break;
   }
   form.accept("OK");
  }
  form.close();
  display.close();
 }

 private void doRent() {

  int cat = 0;   // rental category
  Car c = null;  // car to be rented

  cat = form.readInt("cat");
  c = removeAvail(cat);

  if (c == null) {//car not found
   form.writeString("msg", "No cars available in that category");
  } else {
   fillForm(c);
   addRented(c);
   form.writeString("msg", "Rented: " + c.getLicence());
  }
 }

 private void doReturn() {

  String licence;  // licence plate
  Car c = null;  // car being returned
  int mileage = 0; // mileage at return
  double cost = 0; // cost of rental

  licence = form.readString("licence");
  c = removeRented(licence);
  if (c == null) {//car was not found
   form.writeString("msg", "Licence: " + licence + " is not currently rented");
  }
  else {
   fillForm(c);
   form.accept("OK");
   mileage = form.readInt("nMileage");
   cost = c.returned(mileage);
   form.writeDouble("amt", cost);
   addAvail(c);
   form.writeString("msg", "Returned: " + c.getLicence());
  }
 }

 private void doList() {
  display.writeLine("Available");
  listAvail();
  display.writeLine("Rented");
  listRented();
  form.writeString("msg", "Listed");
 }

 private void loadCars() {
  Car c;
  input = new ASCIIDataFile();
  while (true) {
   c = new Car(input);
   if (input.isEOF()) {
    break;
   }
   addAvail(c);
  }
 }

 private void addAvail(Car c) {
  Node p = avail;

  //We don't have any elements in that list
  if (avail == null) {
   avail = new Node(c, null);
  } else {
   while (p.next != null) {
    p = p.next;
   }
   p.next = new Node(c, null);
  }
 }

 private Car removeAvail(int cat) {

  Car result = null;
  Node p = avail;
  Node q = null;

  if (avail == null) {
   return null;
  }

  if (cat == avail.item.getCategory()) {
   result = avail.item;
   avail = avail.next;
   return result;
  }

  while (p != null) {
   if (cat == p.item.getCategory()) { //we have a match
    result = p.item;
    q.next = p.next;
    return result;
   }
   q = p;
   p = p.next;
  }
  //the car with the passed category was not found.
  return null;
 }

 /**
  * Will output all the information of all the cars stored in the avail list.
  * Note that the while loop has its condition to be (p != null) where
  * in addAvail, the condition was (p.next != null).The difference is:
  * (p.next != null) will ONLY point to the element.
  * (p != null) will point to the element AND access it.
  */
 private void listAvail() {
  Node p = avail;
  while (p != null) {
   display.writeString(p.item.getLicence());
   display.writeInt(p.item.getMileage());
   display.writeInt(p.item.getCategory());
   display.newLine();
   p = p.next;
  }
  display.newLine();
 }

 /**
  * Will add a car to the end of the rented list.
  * if the list is empty (i.e., rented is pointing to null), then
  * perform insertion to the front.
  *
  * if the list is non-empty, then use a while loop and a pointer p.
  * The goal is to make p point to the last element in the list. Then, create
  * a new node and make p.next point to that new node.
  */
 private void addRented(Car c) {
  Node p = rented;

  //We don't have any elements in that list
  if (rented == null) {
   rented = new Node(c, null);
  } else {
   while (p.next != null) {
    p = p.next;
   }
   p.next = new Node(c, null);
  }
 }

 /**
  * Will remove a car from the rented list based on the licence passed.
  * There will be 4 scenarios:
  * Scenario 1: The list is null (i.e., there are no cars rented, all
  * are still available), hence, return null.
  * Scenario 2: The list is non-empty and the first item in the list is the
  * item to be removed, perform deletion from the front and return the item.
  * Scenario 3: The list is non-empty and somewhere in the list, perform the
  * deletion by using two pointers (q and p), then return the item.
  * Scenario 4: The item wasn't found, don't delete anything and return null.
  * Note that the first two if statements (line 293 and 298) will always
  * return something if they get executed. The while loop will return
  * something if and only if there was a match found. If we get to the end of
  * the method (line 317), it means there was no match so just return null.
  *
  * the licence of the car to be removed.
  * @return the first car that matches the licence passed, null otherwise.
  */
 private Car removeRented(String licence) {
  Car result = null;
  Node p = rented;
  Node q = null;

  if (rented == null) { //There are no cars in the avail list
   return null;
  }

  if (p.item.getLicence().compareTo(licence) == 0) { // or licence == rented.item.getLicence())
   result = rented.item;
   rented = rented.next;
   return result;
  }

  while (p != null) {
   if (p.item.getLicence().compareTo(licence) == 0) { //we have a match
    result = p.item;
    q.next = p.next;
    return result;
   }
   q = p;
   p = p.next;
  }
  return null;
 }

 private void listRented() {
  Node p = rented;
  while (p != null) {
   display.writeString(p.item.getLicence());
   display.writeInt(p.item.getMileage());
   display.writeInt(p.item.getCategory());
   display.newLine();
   p = p.next;
  }
  display.newLine();
 }

 /**
  * Will fill the form based on the information stored in the car passed.
  */
 private void fillForm(Car c) {
  form.writeString("licence", c.getLicence());
  form.writeInt("oMileage", c.getMileage());
  form.writeInt("cat", c.getCategory());
  form.writeDouble("rate", c.getRate());
 }

 /**
  * Sets up the form by adding the needed text boxes and radio buttons.
  */
 private void setupForm() {
  form.setTitle("Acme Rentals");
  form.addTextField("licence", "Licence", 8, 10, 10);
  form.addRadioButtons("cat", "Category", true, 10, 40, Car.CATEGORIES);
  form.addTextField("rate", "Rate", getCurrencyInstance(), 8, 294, 40);
  form.setEditable("rate", false);
  form.addTextField("oMileage", "Rental Mileage", getIntegerInstance(), 8, 10, 160);
  form.setEditable("oMileage", false);
  form.addTextField("nMileage", "Returned Mileage", getIntegerInstance(), 8, 222, 160);
  form.addTextField("amt", "Amount", getCurrencyInstance(), 10, 10, 190);
  form.setEditable("amt", false);
  form.addTextField("msg", "Message", 45, 10, 220);
  form.setEditable("msg", false);
 }

 /**
  * The main execution of the program will be started from here.
  */
 public static void main(String[] args) {
  Rental r = new Rental();
 }
}
