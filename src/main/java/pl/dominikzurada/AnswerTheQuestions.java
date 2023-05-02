package pl.dominikzurada;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Stream;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import static pl.dominikzurada.ReadData.AskQuestionAndSendAnswer;
import static pl.dominikzurada.WriteData.WriteQuestionAndAnswer;
import static pl.dominikzurada.WriteData.WriteQuestionAndAnswerToEdit;

public class AnswerTheQuestions {
    public static void main(String[] args) {
        StartApp();
    }


    public static void StartApp() {
        String fileName;
        int firstOptionChoice = SelectFirstOption();
        if (firstOptionChoice == 0) {
            String returnedAnswer;
            File fileWithQAndA;
            fileWithQAndA = WriteNameOfFileToBeCreated();
            System.out.println("-> If you want to finish and exit, write: \"exit\".");
            FileWriter fileWriter = null;
            try {
                assert fileWithQAndA != null;
                fileWriter = new FileWriter(fileWithQAndA, false);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            long numberOfQuestions = 0L;
            do {
                ArrayList<String> returnedData = WriteQuestionAndAnswer(fileWriter, numberOfQuestions);
                returnedAnswer = returnedData.get(0);
                numberOfQuestions = Long.parseLong(returnedData.get(1));
            } while (!returnedAnswer.equalsIgnoreCase("exit"));
        }
        if (firstOptionChoice == 1) {
            fileName = AskForFileName();
            FileReader reader = null;
            Yaml yaml = new Yaml();
            try {
                reader = new FileReader(fileName + ".yml");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            HashMap<Integer, HashMap<String, String>> dataOfFile = yaml.load(reader);
            ArrayList<Integer> returnedData = AskQuestionAndSendAnswer(dataOfFile);
            int correctAnswers = returnedData.get(0);
            int allAnswers = returnedData.get(1);
            System.out.println();
            System.out.println();
            System.out.println("-> -------------------------------");
            System.out.println("-> SUMMARY:");
            System.out.println("-> You answered correctly for " + correctAnswers + "/" + allAnswers + " questions!");
            System.out.println("-> -------------------------------");
        }
        if (firstOptionChoice == 2) {
            ArrayList<String> returnedData;
            fileName = WriteNameOfFileThatYouWantEdit();
            DumperOptions options = new DumperOptions();
            options.setIndent(2);
            options.setPrettyFlow(true);
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            long numberOfLines = 0;
            try (Stream<String> stream = Files.lines(Path.of(fileName + ".yml"), StandardCharsets.UTF_8)) {
                numberOfLines = stream.count();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Long numberOfQuestions = numberOfLines / 3;
            FileReader reader = null;
            try {
                reader = new FileReader(fileName + ".yml");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            HashMap<Long, HashMap<String, String>> dataOfFile = yaml.load(reader);


            do {
                HashMap<String, String> qAndA = new HashMap<>();
                if ((returnedData = WriteQuestionAndAnswerToEdit(numberOfQuestions++)) == null) continue;
                qAndA.put("question", returnedData.get(0));
                qAndA.put("answer", returnedData.get(1));
                dataOfFile.put(numberOfQuestions, qAndA);
            } while (returnedData != null);
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(fileName + ".yml", false);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            yaml.dump(dataOfFile, fileWriter);
        }
    }



    public static Integer SelectFirstOption() {
        System.out.println("-> Type \"0\" if you want to create file with new questions and answers.");
        System.out.println("-> Type \"1\" if you want to be quizzed from selected file.");
        System.out.println("-> Type \"2\" if you want to add new questions and answers to an existing file.");

        Scanner selectOption = new Scanner(System.in);
        return selectOption.nextInt();
    }

    public static File WriteNameOfFileToBeCreated() {
        System.out.println("-> Write the name of the file in that you'll be saving questions with answers:");
        Scanner writeNameOfFile = new Scanner(System.in);
        String fileName = writeNameOfFile.nextLine();
        File newFileWithQAndA = new File(fileName + ".yml");

        if (!newFileWithQAndA.exists()) {
            try {
                newFileWithQAndA.createNewFile();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println("-> A file with that name already exists! Do you want to overwrite it? Write \"yes\" or \"no\":");
            Scanner whetherToOverwriteFile = new Scanner(System.in);
            if (whetherToOverwriteFile.nextLine().equalsIgnoreCase("no")) {
                System.out.println("-> So please write the name of the file again!");
                return null;
            }
        }
        return newFileWithQAndA;
    }



    public static String AskForFileName() {
        System.out.println("-> Please write name of the file from that you'll be quizzed:");
        Scanner writeFileName = new Scanner(System.in);
        return writeFileName.nextLine();
    }



    public static String WriteNameOfFileThatYouWantEdit() {
        System.out.println("-> Write the name of the file you want to edit!:");
        Scanner writeFileName = new Scanner(System.in);
        return writeFileName.nextLine();
    }
}