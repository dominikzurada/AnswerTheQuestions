package pl.dominikzurada;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ReadData {

    public static ArrayList<Integer> AskQuestionAndSendAnswer(HashMap<Integer, HashMap<String, String>> listOfQuestionsAndAnswers, boolean isAnswerByQuestions) {
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
            int numberFromIndexOfTheNextQuestion;
            do {
                numberFromIndexOfTheNextQuestion = random.nextInt(questions.size());
            } while (indicesOfAskedQuestions.contains(numberFromIndexOfTheNextQuestion));
            String question = questions.get(numberFromIndexOfTheNextQuestion);
            String answer = answers.get(numberFromIndexOfTheNextQuestion);
            indicesOfAskedQuestions.add(i, numberFromIndexOfTheNextQuestion);

            if (isAnswerByQuestions) {
                System.out.println("-> Please answer for the following question: " + question);



                String userAnswer = writeAnswer();
                if (userAnswer.equalsIgnoreCase(answer)) {
                    System.out.println("-> -----------------------------");
                    System.out.println("-> Your answer is correct! (checked by program)");
                    System.out.println("-> -----------------------------");
                    correctAnswers++;
                } else {
                    System.out.println("-> -----------------------------");
                    System.out.println("-> Check if your answer is correct!");
                    System.out.println("-> Your answer: " + userAnswer);
                    System.out.println("-> Correct answer: " + answer);
                    System.out.println("-> -----------------------------");
                    if (didUserAnswerCorrectly()) {
                        correctAnswers++;
                    }
                }




            } else {
                System.out.println("-> Please answer for the following question: " + answer);
                String userAnswer = writeAnswer();
                if (userAnswer.equalsIgnoreCase(question)) {
                    System.out.println("-> -----------------------------");
                    System.out.println("-> Your question to answer is correct! (checked by program)");
                    System.out.println("-> -----------------------------");
                    correctAnswers++;
                } else {
                    System.out.println("-> -----------------------------");
                    System.out.println("-> Check if your question to answer is correct!");
                    System.out.println("-> Your question: " + userAnswer);
                    System.out.println("-> Correct question: " + question);
                    System.out.println("-> -----------------------------");

                    if (didUserAnswerCorrectly()) {
                        correctAnswers++;
                    }
                }
            }
        }


        ArrayList<Integer> dataToReturn = new ArrayList<>();
        dataToReturn.add(0, correctAnswers);
        dataToReturn.add(1, answers.size());
        if (isAnswerByQuestions) {
            dataToReturn.add(2, 1);
        } else {
            dataToReturn.add(2, 0);
        }

        return dataToReturn;
    }

    private static boolean didUserAnswerCorrectly() {

        Scanner didUserAnswerCorrectly = new Scanner(System.in);

        do {
            System.out.println("-> Did you answer correctly? Write \"yes\" or \"no\":");
            String userChoice = didUserAnswerCorrectly.nextLine();
            if (userChoice.toLowerCase().contains("yes")) {
                return true;
            }
            if (userChoice.toLowerCase().contains("no")) {
                return false;
            }
        } while (true);
    }

    private static String writeAnswer() {
        Scanner writeAnswer = new Scanner(System.in);
        String answer = "";

        while (answer.isEmpty()) {
            answer = writeAnswer.nextLine();
        }
        return answer;
    }
}