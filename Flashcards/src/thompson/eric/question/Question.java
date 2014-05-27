package thompson.eric.question;

import java.io.Serializable;

public class Question implements Serializable {
	
	private String answer;
	private String question;
	
	public Question(String answer, String question) {
		this.answer = answer;
		this.question = question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public String getQuestion() {
		return question;
	}
}
