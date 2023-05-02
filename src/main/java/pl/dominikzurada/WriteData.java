package pl.dominikzurada;


import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class WriteData {

    public static ArrayList<String> WriteQuestionAndAnswer(FileWriter fileWriter, Long numberOfQuestions) {
        Scanner writeAnswer = new Scanner(System.in);
        Scanner writeQuestion = new Scanner(System.in);
        String question;
        do {
            numberOfQuestions++;
            System.out.println("-> Write the question. It'll be question number: " + numberOfQuestions + ". NOW you can exit by typing \"exit\"!");
            question = writeQuestion.nextLine();
        } while (question.isEmpty());
        ArrayList<String> dataToReturn = new ArrayList<>();
        dataToReturn.add(0, question);
        dataToReturn.add(1, numberOfQuestions.toString());
        if (question.contains("exit")) {
            return dataToReturn;
        }
        String answer;
        do {
            System.out.println("-> Write answer to above question:");
            answer = writeAnswer.nextLine();
        } while (answer.isEmpty());
        HashMap<String, String> qAndA = new HashMap<>();
        qAndA.put("question", question);
        qAndA.put("answer", answer);
        HashMap<Long, HashMap<String, String>> qAndAWithWithNumberOfQuestion = new HashMap<>();
        qAndAWithWithNumberOfQuestion.put(numberOfQuestions, qAndA);
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        yaml.dump(qAndAWithWithNumberOfQuestion, fileWriter);
        return dataToReturn;
    }

    public static ArrayList<String> WriteQuestionAndAnswerToEdit(Long numberOfQuestions) {
        Scanner writeAnswer = new Scanner(System.in);
        Scanner writeQuestion = new Scanner(System.in);
        String question;

        do {
            numberOfQuestions++;
            System.out.println("-> Write question. It'll be question number: " + numberOfQuestions + ". NOW you can exit by typing \"exit\"!");
            question = writeQuestion.nextLine();
        } while (question.isEmpty());

        if (question.contains("exit")) {
            return null;
        }
        String answer;
        do {
            System.out.println("-> Write answer to above question:");
            answer = writeAnswer.nextLine();
        } while (answer.isEmpty());
        ArrayList<String> dataToReturn = new ArrayList<>();
        dataToReturn.add(question);
        dataToReturn.add(answer);
        return dataToReturn;
    }
}