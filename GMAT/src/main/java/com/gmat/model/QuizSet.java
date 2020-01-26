package com.gmat.model;

public class QuizSet {

	private int noOfQuestions;
	private int arrayOfLevels[];
	private int arrayOfTags[];

	public int getNoOfQuestions() {
		return noOfQuestions;
	}

	public void setNoOfQuestions(int noOfQuestions) {
		this.noOfQuestions = noOfQuestions;
	}

	public int[] getArrayOfLevels() {
		return arrayOfLevels;
	}

	public void setArrayOfLevels(int[] arrayOfLevels) {
		this.arrayOfLevels = arrayOfLevels;
	}

	public int[] getArrayOfTags() {
		return arrayOfTags;
	}

	public void setArrayOfTags(int[] arrayOfTags) {
		this.arrayOfTags = arrayOfTags;
	}

}
