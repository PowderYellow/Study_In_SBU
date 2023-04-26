package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.Node;
import javafx.collections.ObservableList;

public class Hangman extends Application {
    BorderPane entireborderPane;
    Button startButton = new Button("", new ImageView(new Image("file:./New.png")));
    Button loadButton = new Button("", new ImageView(new Image("file:./Load.png")));
    Button saveButton = new Button("", new ImageView(new Image("file:./Save.png")));
    Button exitButton = new Button("", new ImageView(new Image("file:./Exit.png")));
    HBox toolBar;
    Button startPlayingButton = new Button("Start Playing");
    HBox bottomBar;
    BorderPane middleBorderPane;
    Text hangManText;
    VBox leftVBox;
    Text remainingGuessesText;
    HBox remainingGuessesHBox;
    int remainingGuesses = 10;
    HBox correctWord;
    GridPane alphabetLetters;
    ArrayList<String> words;
    String word;
    boolean isPlaying = false;
    Group shapesGroup;
    Pane shapesPane;
    String fileName = "";
    ArrayList<Character> alreadyGuessedChars = new ArrayList<>();
    boolean isLoaded = false;
    boolean wonOrLost = false;


    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        entireborderPane = new BorderPane();
        Scene newScene = new Scene(entireborderPane, 900, 600);

        primaryStage.setTitle("Hangman");

        createToolBar();

        startButton.setOnMouseClicked(e -> {
            if(isPlaying || wonOrLost){
                showNewSavePopUp();

            } else{
                createBottom();
            }

        });

        saveButton.setOnMouseClicked(e -> saveCurrentGame());

        loadButton.setOnMouseClicked(e -> loadGameFromFile());


        exitButton.setOnMouseClicked(e -> {
            if(isPlaying || wonOrLost) {
                showExitSavePopUp();
            }
        });


        startPlayingButton.setOnMouseClicked(e -> {
                saveButton.setDisable(false);
                isPlaying = true;
                createMiddle();
                });


        newScene.setOnKeyPressed(e -> {

            if(isPlaying) {
                updateAlphabet(e.getCode());
                updateCorrectWord(e.getCode());
            }

        });


        primaryStage.setScene(newScene);
        primaryStage.show();
    }


    public void showNewSavePopUp(){
        Stage savePopUp = new Stage();
        VBox vbox = new VBox();
        Text text = new Text();
        text.setText("Do you want to save your current game?");
        Button yesBtn = new Button("Yes");
        Button noBtn = new Button("No");
        Button cancelBtn = new Button("Cancel");
        yesBtn.setOnMouseClicked(e -> saveCurrentGame());
        noBtn.setOnMouseClicked(e -> createNewGame());
        cancelBtn.setOnMouseClicked(e -> savePopUp.close());
        vbox.getChildren().addAll(text, yesBtn, noBtn, cancelBtn);
        Scene newScene = new Scene(vbox, 300, 300);
        savePopUp.setScene(newScene);
        savePopUp.show();
    }

    public void saveCurrentGame(){
        Stage newStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(fileName);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HNG Files", "*.hng"));
        File savedFile = fileChooser.showSaveDialog(newStage);
        newStage.show();
        if (savedFile != null) {
            GameState gameState = new GameState(this.word, alreadyGuessedChars, this.remainingGuesses);
            gameState.writeToFile(savedFile.getAbsolutePath());
        }
        newStage.close();
    }


    public void createNewGame(){
        correctWord  = null;
        alphabetLetters = null;
        remainingGuessesHBox = null;
        remainingGuesses = 10;
        createMiddle();
        isPlaying = true;
        wonOrLost = false;
    }


    public void showExitSavePopUp(){
        Stage savePopUp = new Stage();
        VBox vbox = new VBox();
        Text text = new Text();
        text.setText("Do you want to save your current game?");
        Button yesBtn = new Button("Yes");
        Button noBtn = new Button("No");
        Button cancelBtn = new Button("Cancel");
        yesBtn.setOnMouseClicked(e -> {
            saveCurrentGame();
            System.exit(1);
        });
        noBtn.setOnMouseClicked(e -> System.exit(1));
        cancelBtn.setOnMouseClicked(e -> savePopUp.close());
        vbox.getChildren().addAll(text, yesBtn, noBtn, cancelBtn);
        Scene newScene = new Scene(vbox, 300, 300);
        savePopUp.setScene(newScene);
        savePopUp.show();
    }


    public void loadGameFromFile(){
        Stage newStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HNG Files", "*.hng"));
        File loadedFile = fileChooser.showOpenDialog(newStage);
        if(loadedFile != null) {
            GameState gameState = GameState.loadFromFile(loadedFile);
            this.word = gameState.getWord();
            this.alreadyGuessedChars = gameState.getAlreadyGuessedChars();

            this.isLoaded = true;
            correctWord = null;
            this.word = gameState.getWord();
            alphabetLetters = null;
            remainingGuessesHBox = null;
            remainingGuesses = 10;
            createMiddle();

            for (int i = 0; i < alreadyGuessedChars.size(); i++) {
                updateAlphabet(KeyCode.getKeyCode(alreadyGuessedChars.get(i) + ""));
                updateCorrectWord(KeyCode.getKeyCode(alreadyGuessedChars.get(i) + ""));
            }

            this.isLoaded = false;
        }

        isPlaying = true;
        wonOrLost = false;
        newStage.close();
    }


    public void createToolBar(){
        saveButton.setDisable(true);
        toolBar = new HBox();
        toolBar.getChildren().addAll(startButton, loadButton, saveButton, exitButton);
        toolBar.setStyle("-fx-padding: 17px;" +
                         "-fx-background-color: black;");
        entireborderPane.setTop(toolBar);
    }


    public void createBottom(){

        bottomBar = new HBox();
        bottomBar.getChildren().add(startPlayingButton);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setStyle("-fx-padding: 17px;");
        entireborderPane.setBottom(bottomBar);
    }


    public void createMiddle(){
        startPlayingButton.setDisable(true);
        middleBorderPane = new BorderPane();
        HBox hangManTextHBox = new HBox();
        hangManText = new Text("Hangman");
        hangManText.setStyle("-fx-color: black;" +
                             "-fx-font-size: 40px;");
        hangManTextHBox.getChildren().add(hangManText);
        hangManTextHBox.setAlignment(Pos.CENTER);
        middleBorderPane.setTop(hangManTextHBox);
        leftVBox = new VBox();
        updateNumGuesses();
        createCorrectWord();
        createAlphabetLetters();
        shapesGroup = new Group();
        Line line1 = new Line();
        line1.setStartX(50); line1.setStartY(350); line1.setEndX(350); line1.setEndY(350);
        line1.setStrokeWidth(10);
        shapesGroup.getChildren().add(line1);
        shapesPane = new Pane(shapesGroup);
        middleBorderPane.setLeft(shapesPane);
        middleBorderPane.setRight(leftVBox);
        entireborderPane.setCenter(middleBorderPane);
    }

    public void updateNumGuesses(){
        if(remainingGuessesHBox != null){
            leftVBox.getChildren().remove(0);
            Text guesses = (Text)remainingGuessesHBox.getChildren().get(0);
            guesses.setText("Remaining Guesses: " + this.remainingGuesses);
            remainingGuessesHBox.getChildren().remove(0);
            remainingGuessesHBox.getChildren().add(guesses);
            leftVBox.getChildren().add(0, remainingGuessesHBox);
            if(remainingGuesses == 0){
                createLosingWindow();
            }

        }
        else {
            remainingGuessesHBox = new HBox();
            remainingGuessesText = new Text("Remaining Guesses: " + this.remainingGuesses);
            remainingGuessesHBox.getChildren().add(remainingGuessesText);
            remainingGuessesHBox.setStyle("-fx-padding: 18px;");
            leftVBox.getChildren().add(remainingGuessesHBox);
        }
    }



    public void createWinningWindow(){
        isPlaying = false;
        wonOrLost = true;
        for(int i = 0; i < word.length(); i++)
            correctWord.getChildren().get(i).setStyle("-fx-background-color: white;" +
                    "-fx-padding: 13px;" +
                    "-fx-border-color: white;");
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle("Game Over");
        alert.setContentText("You Won!");
        alert.getButtonTypes().add(ButtonType.CLOSE);
        alert.showAndWait();
    }



    public void createLosingWindow(){
        isPlaying = false;
        wonOrLost = true;
        for(int i = 0; i < word.length(); i++)
            correctWord.getChildren().get(i).setStyle("-fx-background-color: white;" +
                    "-fx-padding: 13px;" +
                    "-fx-border-color: white;");
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle("Game Over");
        alert.setContentText("You lost (the word was '" + word + "')");
        alert.getButtonTypes().add(ButtonType.CLOSE);
        alert.showAndWait();
    }


    public void createAlphabetLetters(){
        if(alphabetLetters != null){
            leftVBox.getChildren().remove(alphabetLetters);
            leftVBox.getChildren().add(alphabetLetters);
        }

        else {
            alphabetLetters = new GridPane();
            char letter = 'A';
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 7; j++) {
                    if (letter > 'Z')
                        break;
                    Rectangle letterTile = new Rectangle();
                    Text letterText = new Text("" + letter);
                    letterText.setFill(Color.WHITE);
                    StackPane stack = new StackPane();
                    stack.getChildren().addAll(letterTile, letterText);
                    stack.setStyle("-fx-background-color: green;" +
                            "-fx-padding: 10px;" +
                            "-fx-border-color: white;");

                    alphabetLetters.add(stack, j, i);
                    letter++;
                }
            }

            alphabetLetters.setStyle("-fx-padding: 18px;");
            leftVBox.getChildren().add(alphabetLetters);
        }
    }


    public void createCorrectWord(){
        if(correctWord != null){
            leftVBox.getChildren().remove(1);
            leftVBox.getChildren().add(1, correctWord);
        }

        else {
            if(!this.isLoaded) {
                word = pickRandomWord();
                System.out.println(word);
            }

            correctWord = new HBox();

            for (int i = 0; i < word.length(); i++) {
                Rectangle letterTile = new Rectangle();
                Text letterText = new Text("" + word.charAt(i));
                StackPane stack = new StackPane();
                stack.setStyle("-fx-background-color: black;" +
                        "-fx-padding: 13px;" +
                        "-fx-border-color: white;");
                stack.getChildren().addAll(letterTile, letterText);
                correctWord.getChildren().add(stack);
            }

            correctWord.setStyle("-fx-padding: 18px;");
            leftVBox.getChildren().add(correctWord);
        }
    }

    public void createHangmanShapes(int numShapes){
        HangmanShapes.initialize();

        shapesGroup.getChildren().add(HangmanShapes.shapes.get(numShapes));
    }


    public String pickRandomWord(){
        File file = new File("words.txt");
        words = new ArrayList<>();
        Scanner reader;
        try {
            reader = new Scanner(file);
            while(reader.hasNextLine()){
                words.add(reader.nextLine());
            }

        } catch(Exception e){
            System.out.println(e.getStackTrace());
        }

        return words.get((int)(Math.random()*words.size()));
    }


    public void updateAlphabet(KeyCode keyCode) {
        if(alphabetLetters == null)
            return;
        char code = ' ';
        if (keyCode.getName().toCharArray().length == 1){
            code = keyCode.getName().toCharArray()[0];
            if (code >= 'A' && code <= 'Z') {
            //System.out.println(code);
            int[] rowColArray = getRowColIndex(keyCode.getName());
            StackPane stack = (StackPane)getNodeByRowColumnIndex(rowColArray[0], rowColArray[1], alphabetLetters);
            alphabetLetters.getChildren().remove(stack);
            stack.setStyle("-fx-background-color: lime;" +
                           "-fx-padding: 10px;" +
                           "-fx-border-color: white;");
            if(!isLoaded) {
                if(!alreadyGuessedChars.contains(code))
                    alreadyGuessedChars.add(code);
            }
            alphabetLetters.add(stack, rowColArray[1], rowColArray[0]);
            createAlphabetLetters();
            }
        }

    }


    public void updateCorrectWord(KeyCode keyCode){
        if(correctWord == null)
            return;
        char code = ' ';
        if(keyCode.getName().toCharArray().length == 1){
            code = keyCode.getName().toCharArray()[0];
            if(code >= 'A' && code <= 'Z'){

                if(word.toUpperCase().contains("" + code) ){

                    ArrayList<Integer> list = occurencesOfLetter(word.toUpperCase(), code);
                    for(int i = 0; i < list.size(); i++) {
                        int index = list.get(i);
                        StackPane stack = (StackPane) (correctWord.getChildren().get(index));
                        correctWord.getChildren().remove(stack);
                        stack.setStyle("-fx-background-color: white;" +
                                "-fx-padding: 13px;" +
                                "-fx-border-color: white;");
                        correctWord.getChildren().add(index, stack);
                        //numCorrectGuesses++;
                        GameState.printList(alreadyGuessedChars);
                    }


                    createCorrectWord();

                    boolean isDone = true;

                    for(int i = 0; i < correctWord.getChildren().size(); i++ ) {
                        isDone = isDone && correctWord.getChildren().get(i).getStyle().contains("-fx-background-color: white;");
                    }

                    if(isDone) {
                        createWinningWindow();
                    }


                }

                else {
                    remainingGuesses--;

                    int numIncorrectGuesses = 9 -  remainingGuesses;
                    //incorrectGuesses++;
                    createHangmanShapes(numIncorrectGuesses);
                    updateNumGuesses();
                }

            }
        }
    }


    public ArrayList occurencesOfLetter(String s, char c){
        ArrayList<Integer> list = new ArrayList();
        int index = s.indexOf(c);
        while(index >= 0){
            list.add(index);
            index = s.indexOf(c, index + 1);
        }
        return list;
    }


    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }


    public int[] getRowColIndex(String code){
        int keyCode = code.toCharArray()[0];
        int singleNum = keyCode - 65;
        int colNum = singleNum % 7;
        int rowNum = singleNum / 7;

        int[] rowAndCol = new int[2];
        rowAndCol[0] = rowNum;
        rowAndCol[1] = colNum;

        return rowAndCol;
    }


    public static void main(String[] args) {
        launch(args);
    }


}
