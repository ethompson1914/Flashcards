package thompson.eric.factories;

import thompson.eric.set.QuestionSet;

public class QuestionSetFactoryImpl implements Factory {

	@Override
	public QuestionSet createBlankQuestionSet() {
		return new QuestionSet("New Set");
	}

}
