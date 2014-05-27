package thompson.eric.set;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import thompson.eric.question.Question;

public class QuestionSet implements Serializable {
	
	private ArrayList<Question> questions;
	private String name;
	
	public QuestionSet(String name) {
		questions = new ArrayList<Question>();
		this.name = name;
	}
	
	public ArrayList<Question> getAllQuestions() {
		return questions;
	}

	/* Removes a Card from the top of the deck and returns it */
	public Question getTopCard(){    
		return questions.remove(0);
	}

	/* Returns the number of cards in the deck */
	public int getNumQuestions(){  
		return questions.size();
	}

	/* Shuffles the deck.
	 * Algorithm: Steps through the ArrayList, swapping every element 
	 * with another element at a randomly chosen index */
	public void shuffle() {
		for(int x=0; x<questions.size();x++){
			Random r = new Random(); //Using the built-in class Random.
			int k = r.nextInt(questions.size()); 
			Question temp = questions.get(k); //Need a temporary Card object as the original value will be replaced.
			int p = r.nextInt(questions.size());
			questions.set(k, questions.get(p)); //Swapping the elements
			questions.set(p, temp);        //Swapping the elements
		}
	}
}
