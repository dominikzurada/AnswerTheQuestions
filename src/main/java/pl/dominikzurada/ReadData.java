package pl.dominikzurada;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ReadData {

    public static ArrayList<Integer> AskQuestionAndSendAnswer(HashMap<Integer, HashMap<String, String>> listOfQuestionsAndAnswers) {
        ArrayList<String> questions = new ArrayList<>();
        ArrayList<String> answers = new ArrayList<>();
        for (int i = 0; i < listOfQuestionsAndAnswers.size(); ++i) {
            HashMap<String, String> questionAndAnswer = listOfQuestionsAndAnswers.get(i + 1);
            questions.add(i, questionAndAnswer.get("question"));
            answers.add(i, questionAndAnswer.get("answer"));
        }
        int correctAnswers = 0;
        Random random = new SecureRandom();
        ArrayList<Integer> indicesOfAskedQuestions = new ArrayList<>();
        for (int i = 0; i < questions.size(); ++i) {
            String isAnswerWasCorrect;
            int numberFromIndexOfTheNextQuestion;
            do {
                numberFromIndexOfTheNextQuestion = random.nextInt(questions.size());
            } while (indicesOfAskedQuestions.contains(numberFromIndexOfTheNextQuestion));
            String question = questions.get(numberFromIndexOfTheNextQuestion);
            String answer = answers.get(numberFromIndexOfTheNextQuestion);
            indicesOfAskedQuestions.add(i, numberFromIndexOfTheNextQuestion);
            System.out.println("-> Please answer for the following question: " + question);
            Scanner writeAnswer = new Scanner(System.in);
            String userAnswer = writeAnswer.nextLine();
            if (userAnswer.equalsIgnoreCase(answer)) {
                System.out.println("-> -----------------------------");
                System.out.println("-> Answer is correct! (checked by program)");
                System.out.println("-> Question: " + question);
                System.out.println("-> Your answer: " + userAnswer);
                System.out.println("-> Correct answer: " + answer);
                System.out.println("-> -----------------------------");
                correctAnswers++;
                continue;
            }
            System.out.println("-> -----------------------------");
            System.out.println("-> Please check if your answer is correct!");
            System.out.println("-> Question: " + question);
            System.out.println("-> Your answer: " + userAnswer);
            System.out.println("-> Correct answer: " + answer);
            System.out.println("-> -----------------------------");
            System.out.println("-> Did you answer correctly? Write \"yes\" or \"no\":");
            Scanner writeIsAnswerWasCorrect = new Scanner(System.in);
            do {
                if (!(isAnswerWasCorrect = writeIsAnswerWasCorrect.nextLine().toLowerCase()).contains("yes")) continue;
                correctAnswers++;
                break;
            } while (!isAnswerWasCorrect.toLowerCase().contains("no"));
        }
        ArrayList<Integer> dataToReturn = new ArrayList<>();
        dataToReturn.add(0, correctAnswers);
        dataToReturn.add(1, answers.size());
        return dataToReturn;
    }
}