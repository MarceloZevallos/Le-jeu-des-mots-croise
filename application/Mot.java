package application;

public class Mot {
	
	private char sens;
	private String questions;
	private String reponses;
	
	public Mot(char sens, String questions, String reponses) {
		this.sens = sens;
		this.questions = questions;
		this.reponses = reponses;
	}

	public char getSens() {
		return sens;
	}
	
	public String getQuestions() {
		return questions;
	}


	public String getReponses() {
		return reponses;
	}

}
