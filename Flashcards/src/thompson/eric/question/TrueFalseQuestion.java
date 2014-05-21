package thompson.eric.question;

public class TrueFalseQuestion extends Question {

	private String question;
	private String answer;
	
	public TrueFalseQuestion(String answer, String question) {
		super(answer, question);
		this.answer = answer;
		this.question = question;
	}
}
