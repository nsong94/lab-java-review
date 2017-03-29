package edu.info448.review;

/**
 * A simple model class representing a Dog
 * @author Joel Ross
 */
public class Dog extends Animal
{
	

	public static final String BEST_BREED = "Husky";

	private String name;
	private String breed;

	private String smell;

	public Dog(String name, String breed) {
		this.name = name;
		this.breed = breed;
	}

	public Dog(String name){
		this(name, "mutt");
	}

	public Dog() {
		this("Fido");
	}


	/*public class Smell {
		public Smell(String name) {
			this.name = name;
			this.smell = name;
		}
	}*/


	public void bark() {
		System.out.println(this+" says: Bark Bark!");
	}

	public void wagTail(int times){
		for(int i=0; i<times; i++){
			System.out.println(this+" wags his tail");
		}
	}

	public String toString() {
		return this.name+" the " +this.breed;
	}

	public void speak() {
		//System.out.println("hello?");
		this.bark();
	}

	public static Dog[] createPuppies(int number) {
		Dog[] dogs = new Dog[number];
		for(int i=0; i<dogs.length; i++){
			dogs[i] = new Dog("Puppy-"+(i+1), "Dalmation");
		}
		return dogs;
	}

}