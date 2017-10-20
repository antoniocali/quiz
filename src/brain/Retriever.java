/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import models.QuestionBuilder;
import models.QuestionBuilder.Question;
import utility.ReturnValue;

/**
 *
 * @author ac186112
 */
public class Retriever {

    String path;
    ArrayList<Question> questions = new ArrayList<>();

    public Retriever(String path) {
        this.path = path;
    }

    public ReturnValue<ArrayList<Question>> runRetriever() {
        ReturnValue<ArrayList<Question>> retValue = new ReturnValue<>();
        QuestionBuilder builder = new QuestionBuilder("", -1);
        long rowNumber = 0;
        try {
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                boolean startQuestion = false;
                int choicesAvabile = 0;

                String currentLine = null;
                while ((currentLine = br.readLine()) != null) {
                    currentLine = currentLine.trim();
                    rowNumber++;
                    if (!startQuestion && currentLine.startsWith("&")) {
                        startQuestion = true;
                        builder = new QuestionBuilder(currentLine.substring(1), questions.size() + 1);
                    } else if (startQuestion && currentLine.startsWith("+")) {
                        choicesAvabile++;
                        builder = builder.addChoice(currentLine.substring(1), true);
                    } else if (startQuestion && currentLine.startsWith("-")) {
                        choicesAvabile++;
                        builder = builder.addChoice(currentLine.substring(1), false);
                    } else if (startQuestion && currentLine.isEmpty()) {
                        if (choicesAvabile < 2) {
                            // do nothing
                        } else {
                            ReturnValue<Question> retValueQ = builder.build();
                            if (retValueQ.isSuccess()) {
                                questions.add(retValueQ.getObject());
                            } else {
                                System.out.println("ERROR IN QUESTION");
                                System.out.println(retValueQ.getObject().getQuestion(true));
                            }
                            startQuestion = false;
                            choicesAvabile = 0;
                        }
                    } else if (startQuestion && currentLine.startsWith("&")) {
                        ReturnValue<Question> retValueQ = builder.build();
                        if (retValueQ.isSuccess()) {
                            questions.add(retValueQ.getObject());
                        } else {
                            System.out.println("ERROR IN QUESTION");
                            System.out.println(retValueQ.getObject().getQuestion(true));
                        }
                        choicesAvabile = 0;
                        builder = new QuestionBuilder(currentLine.substring(1), questions.size() + 1);
                        startQuestion = true;

                    } else {
                        throw new Exception("Error with line (" + rowNumber + "): " + currentLine);
                    }
                }
                if (startQuestion) {
                    ReturnValue<Question> retValueQ = builder.build();
                    if (retValueQ.isSuccess()) {
                        questions.add(retValueQ.getObject());
                    }
                }
                br.close();
                fr.close();
                retValue.setSuccess(true);
                retValue.setObject(questions);
            } else {
                throw new Exception(path + " do not exist or not file");
            }

        } catch (Exception e) {
            retValue.setSuccess(false);
            retValue.setMessage(e.getMessage());
        }
        return retValue;
    }
}
