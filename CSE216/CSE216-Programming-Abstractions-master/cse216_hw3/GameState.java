package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GameState {
    private String word;
    private ArrayList<Character> alreadyGuessedChars;
    private int remainingGuesses;

    public GameState(){}

    public GameState(String word, ArrayList<Character> alreadyGuessedChars, int remainingGuesses) {
        this.word = word;
        this.alreadyGuessedChars = alreadyGuessedChars;
        this.remainingGuesses = remainingGuesses;
    }

    public String getWord(){
        return this.word;
    }

    public ArrayList<Character> getAlreadyGuessedChars(){
        return this.alreadyGuessedChars;
    }

    public int getRemainingGuesses(){
        return this.remainingGuesses;
    }



    public void writeToFile(String fileName) {
        File file = new File(fileName);

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            writer.write(this.word);
            writer.append(",");
            //writer.print(this.remainingGuesses);
            //writer.append(",");
            for (int i = 0; i < this.alreadyGuessedChars.size() - 1; i++) {
                writer.print(alreadyGuessedChars.get(i));
                writer.append(",");
            }

            writer.print(alreadyGuessedChars.get(alreadyGuessedChars.size() - 1));

            writer.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static GameState loadFromFile(File file){
        String word = "";
        ArrayList<Character> guessedChars = new ArrayList<>();
        int remainingGuesses = 0;

        try{
            Scanner reader = new Scanner(file);

            while(reader.hasNext()){

                String[] line = reader.nextLine().split(",");
                word = line[0];
                //remainingGuesses = Integer.parseInt(line[1]);
                for(int i = 1; i < line.length; i++){
                    guessedChars.add(line[i].toCharArray()[0]);
                }
            }

        } catch(IOException ioe){
            ioe.printStackTrace();
        }

        printList(guessedChars);
        return new GameState(word, guessedChars, remainingGuesses);
    }


    public static void printList(ArrayList<Character> list){
        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }


}
