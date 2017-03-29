package edu.info448.review;

import java.util.*;

/**
 * A basic class for running and testing the Dog class
 */
public class Tester {

	public static void main(String[] args){
		Dog dog = new Dog();
		dog.bark();
		dog.walk();

		Dog[] pups = Dog.createPuppies(3);
		System.out.println(Arrays.toString(pups));

		// Dog.bark(); // doesn't work
		Husky husky = new Husky();
		//husky.bark();

		husky.pullSled();	// can compile + run
		//dog.pullSled();	// doesn't work

		husky.bark();
		husky.hug();

		// testing Huggable
		TeddyBear teddy = new TeddyBear();
		teddy.hug();

		Dog v1 = new Husky();	// compiles!
		Husky v2 = (Husky)v1;	// legal casting

		// Husky v2 = new Dog();	// doesn't compile
		//Huggable v2 = new Husky();	// compiles!
		Huggable v3 = new TeddyBear();	// compiles!
		// Husky v4 = new TeddyBear();	// doesn't compile

		v1.bark();	// Husky version

		System.out.println();

		ArrayList<Huggable> hugList = new ArrayList<Huggable>(); //a list of huggable things
		hugList.add(new Husky()); //a Husky is Huggable
		hugList.add(new TeddyBear()); //so are Teddybears!

		//enhanced for loop ("foreach" loop)
		//read: "for each Huggable in the hugList"
		for(Huggable thing : hugList) {
    		thing.hug();
		}

		TeddyBear teddybear = new TeddyBear();
		GiftBox giftbox = new GiftBox<Husky>(teddybear);



	}
}