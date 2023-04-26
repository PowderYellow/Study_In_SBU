package sample;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import java.util.ArrayList;

public class HangmanShapes {
    public static ArrayList<Shape> shapes = new ArrayList<>();

    public static void initialize(){
        //Line line1 = new Line();
        //line1.setStartX(50); line1.setStartY(350); line1.setEndX(350); line1.setEndY(350);
        //line1.setStrokeWidth(10);
        //shapes.add(line1);
        Line line2  = new Line();
        line2.setStartX(50); line2.setStartY(350); line2.setEndX(50); line2.setEndY(75);
        line2.setStrokeWidth(10);
        shapes.add(line2);
        Line line3 = new Line();
        line3.setStartX(50); line3.setStartY(75); line3.setEndX(225); line3.setEndY(75);
        line3.setStrokeWidth(10);
        shapes.add(line3);
        Line line4 = new Line();
        line4.setStartX(225); line4.setStartY(75); line4.setEndX(225); line4.setEndY(125);
        line4.setStrokeWidth(10);
        shapes.add(line4);
        Circle circle = new Circle();
        circle.setRadius(25); circle.setCenterX(225); circle.setCenterY(150);
        circle.setStrokeWidth(10);
        shapes.add(circle);
        Line line5 = new Line();
        line5.setStartX(225); line5.setStartY(175); line5.setEndX(225); line5.setEndY(200);
        line5.setStrokeWidth(10);
        shapes.add(line5);
        Line line6 = new Line();
        line6.setStartX(225); line6.setStartY(200); line6.setEndX(190); line6.setEndY(215);
        line6.setStrokeWidth(10);
        shapes.add(line6);
        Line line7 =  new Line();
        line7.setStartX(225); line7.setStartY(200); line7.setEndX(260); line7.setEndY(215);
        line7.setStrokeWidth(10);
        shapes.add(line7);
        Line line8 = new Line();
        line8.setStartX(225); line8.setStartY(200); line8.setEndX(225); line8.setEndY(250);
        line8.setStrokeWidth(10);
        shapes.add(line8);
        Line line9 =  new Line();
        line9.setStartX(225); line9.setStartY(250); line9.setEndX(190); line9.setEndY(280);
        line9.setStrokeWidth(10);
        shapes.add(line9);
        Line line10 = new Line();
        line10.setStartX(225); line10.setStartY(250); line10.setEndX(260); line10.setEndY(280);
        line10.setStrokeWidth(10);
        shapes.add(line10);
    }


}
