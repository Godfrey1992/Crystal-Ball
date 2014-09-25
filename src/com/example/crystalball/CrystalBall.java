package com.example.crystalball;

import java.util.Random;

public class CrystalBall {
	
	public String[] mAnswers = {
    		"It is certain",
    		"It is decidedly so",
    		"All signs say YES",
    		"The stars are not aligned",
    		"My reply is no",
    		"It is doubtful", 
    		"Better not to tell you now",
    		"Concentrate and ask again",
    		"Unable to answer now" };

	public String getAnswer(){
			
			String answer = "";
			
			// Randomly Select
			Random randomGen = new Random();
			int randomNum = randomGen.nextInt(mAnswers.length);
			
			answer = mAnswers[randomNum];
			
			return answer;
	}
}
