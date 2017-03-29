package edu.info448.review; //package declaration (needed)

//public class Husky extends Dog {
public class Husky extends Dog implements Huggable {
  /* class body goes here */

  public Husky() {
  	this("Dubs");
  }

  public Husky (String name) {
  	super(name, BEST_BREED);
  }

  public void pullSled() {

  }

  public void bark() {
  	System.out.println(this+" says: Woof Woof!");
  }

  public void hug() {
  	System.out.println(this+" is so huggable!");
  }
}