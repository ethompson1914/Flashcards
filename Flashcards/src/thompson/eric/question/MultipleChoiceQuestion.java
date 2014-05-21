package thompson.eric.question;

public class MultipleChoiceQuestion extends Question {

	private String answer;
	private String question;
	private String[] choices;
	
	public MultipleChoiceQuestion(String answer, String question, String[] choices) {
		super(answer, question);
		this.answer = answer;
		this.question = question;
		this.choices = choices;
	}
	
	public char getAnswerLetter() {
		if(choices[0].equals(answer)) { return 'a'; }
		else if(choices[1].equals(answer)) { return 'b'; }
		else if(choices[2].equals(answer)) { return 'c'; }
		else if(choices[3].equals(answer)) { return 'd'; }
		else { return 'z'; }
	}
	
	public String[] getChoices() {
		return choices;
	}
}
