/*

SortedFileWords.java
SortedFileWords

Written by Joey deVilla 2015-11-23.
Copyright © 2015 Joey deVilla. All rights reserved.
MIT License. See the end of the file for the gory details.

My solution for a friend's Java homework assignment. Yes, even at this age,
I'm still doing people's computer homework for them.

The assignment: write a Java program that reads the following text from
a file:

    Four score and seven years ago our fathers brought forth on this 
    continent a new nation conceived in liberty and dedicated to the 
    proposition that all men are created equal Now we are engaged in 
    a great civil war testing whether that nation or any nation so 
    conceived and so dedicated can long endure We are met on a great 
    battlefield of that war We have come to dedicate a portion of 
    that field as a final resting-place for those who here gave their 
    lives that that nation might live

and then displays its words in alphabetical order, using a GUI implemented
using JavaFX.

This is my solution, and from what I hear, my friend got full marks.

*/


package SortedFileWords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class SortedFileWords extends Application {
    
TextField filenameTextField;
TextArea resultsTextArea;
    
    @Override
    public void start(Stage primaryStage) {
        Label enterFilenameLabel = new Label("Enter the filename:");
        
        filenameTextField = new TextField();
        
        Button goButton = new Button();
        goButton.setText("Display file's words, sorted");
        goButton.setOnAction(new EventHandler<ActionEvent>() {    
            @Override
            public void handle(ActionEvent event) {
                String filename = filenameTextField.getText();
                if (filename.length() > 0) {
                    displaySortedFileWords(filename);
                }
                else {
                    resultsTextArea.setText("Please enter a filename.");
                }
            }
        });
        
        resultsTextArea = new TextArea();
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(5);
        grid.setVgap(5);
        grid.add(enterFilenameLabel, 0, 0);
        grid.add(filenameTextField, 0, 1);
        grid.add(goButton, 0, 2);
        grid.add(resultsTextArea, 0, 3);
        
        Scene scene = new Scene(grid, 300, 250);
        
        primaryStage.setTitle("Sorted File Words");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    /**
     * Given a filename, attempts to read its contents, and if successful,
     * displays all the words that appear in the file (including duplicates)
     * sorted in alphabetical order.
     * 
     * @param filename  The name of the file whose words are to be displayed
     *                  in alphabetical order.
     */
    private void displaySortedFileWords(String filename) {
        List<String> wordList = null;
        try {
            wordList = getFileContents(filename);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        if (wordList != null) {
            resultsTextArea.setText(sortedWords(wordList));
        }
        else {
            resultsTextArea.setText("File not found.");
        }
    }
    
    /**
     * Given a filename, returns the words (strings in the file delimited by
     * white space) in the order in which they appear as a list.
     * 
     * @param filename      The name of the file whose contents are to be
     *                      turned into a list of words.
     * @throws IOException  If the file can't be read for some reason.
     */
    private List<String> getFileContents(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("The input file you specified either does not exist or is not in the designated location.");
            return null;
        }
        
        BufferedReader charRead;
        List<String> wordList = new ArrayList<>();
        charRead = new BufferedReader(new FileReader(filename));
        String nextSentence;
        while ((nextSentence = charRead.readLine()) != null) {
          wordList.addAll(Arrays.asList(nextSentence.split("\\b+")));
        }
        charRead.close();
        
        return wordList;
    }
    
    /**
     * Given a list of words, returns a string containing the words
     * sorted case-insensitively in ascending order, each word on its
     * own line. The result is intended to be used in a textarea.
     *
     * @param wordList  The list to be sorted.
     */
    private String sortedWords(List<String> wordList) {
        Collections.sort(wordList, new CaseInsensitiveSort());
        
        StringBuilder outputBuilder = new StringBuilder(); 
        for (String word: wordList) {
            if (word != null && !word.equals("") ) {
                if (Character.isLetter(word.charAt(0))) {
                    outputBuilder.append(word).append("\n");
                }
            }
        }
        return new String(outputBuilder);
    }
    
    /**
     * This comparator class is used by sortedWords().
     */
    private class CaseInsensitiveSort implements Comparator<String> {
        @Override
        public int compare(String string1, String string2) {
            return string1.toLowerCase().compareTo(string2.toLowerCase());
        }
    }
    
}

/*

This code is released under the MIT license.
Simply put, you're free to use this in your own projects, both
personal and commercial, as long as you include the copyright notice below.
It would be nice if you mentioned my name somewhere in your documentation
or credits.

MIT LICENSE
-----------
(As defined in https://opensource.org/licenses/MIT)

Copyright © 2015 Joey deVilla. All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom
the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

*/