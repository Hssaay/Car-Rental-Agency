package Rental;

/**
 * A wrapper class which will hold two things: the car and the next element to
 * point to.
 */
class Node {

 Car item;  // the car object
 Node next;// the link to the next node

 /**
  * The only constructor which will accepts the car to be stored and the link
  * to the next element.
  *
  * @param c the car to be stored.
  * @param n the link to the next node.
  */
 Node(Car c, Node n) {
  item = c;
  next = n;
 }
}
