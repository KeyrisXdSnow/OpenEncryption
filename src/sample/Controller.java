package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.regex.Pattern;

public class Controller {

    @FXML
    private TextArea text_q,text_p,text_b,textFile1,textFile2,textFile3,textFile4, Ltext,
            encryptFile;
    @FXML
    private Label l_bad,l_good,q_ok,p_ok,b_ok, q_bad, p_bad, b_bad;
    @FXML
    private Button b_go, b_fresh ;
    @FXML
    private AnchorPane pane;

    private Pattern pattern ;
    private String regEx = "\\d*";
    private int q_height = 21, p_height = 140, b_height = 250;
    private boolean q_simple = false, p_simple = false, b_simple = false ;
    private String path = "D:\\Java\\Projects\\TI\\Laba3\\" ;

    @FXML
    void initialize() {

        text_q.textProperty().addListener((observableValue, oldValue, newValue) -> {
            pattern = Pattern.compile(regEx) ;
            if (!pattern.matcher(newValue).matches() ) {
                text_q.setText(oldValue);
            } else {

                q_simple = showTextCondition ("q", text_q);

                if (text_q.getLength() != 0 && text_p.getLength() != 0) {
                    if (BigInteger.valueOf(Integer.parseInt(text_q.getText())).multiply(
                            BigInteger.valueOf(Integer.parseInt(text_p.getText()))).compareTo(
                            BigInteger.valueOf(256)) < 0) {
                        l_bad.setPrefHeight(b_height);
                        l_good.setPrefHeight(b_height);
                        b_simple = false;
                        l_good.setVisible(false);
                        l_bad.setVisible(true);
                        l_bad.setText(" q*p < 256 ");
                        text_b.setDisable(true);
                    }
                } text_b.setDisable(true);
            }
        } );
        text_q.setOnMouseEntered(mouseEvent -> {
            l_good.setVisible(false);
            l_bad.setVisible(false);
            l_bad.setPrefHeight(q_height);
            l_good.setPrefHeight(q_height);
            q_ok.setVisible(false);
            q_bad.setVisible(false);
        });
        text_q.setOnMouseExited(mouseEvent -> {
            if ( q_simple ) {
                l_good.setVisible(false);
                l_bad.setVisible(false);
                q_ok.setVisible(true);
            }
            else  if ( text_q.getLength() != 0) {
                q_bad.setVisible(true);
                l_good.setVisible(false);
                l_bad.setVisible(false);
            }

        });

        text_p.textProperty().addListener((observableValue, oldValue, newValue) -> {
            pattern = Pattern.compile(regEx) ;
            if (!pattern.matcher(newValue).matches() )
                text_p.setText(oldValue);
            else {
                p_simple = showTextCondition("p", text_p);

                if (text_q.getLength() != 0 && text_p.getLength() != 0) {
                    if (BigInteger.valueOf(Integer.parseInt(text_q.getText())).multiply(
                            BigInteger.valueOf(Integer.parseInt(text_p.getText()))).compareTo(
                            BigInteger.valueOf(256)) < 0) {
                        l_bad.setPrefHeight(b_height);
                        l_good.setPrefHeight(b_height);
                        b_simple = false;
                        l_good.setVisible(false);
                        l_bad.setVisible(true);
                        l_bad.setText(" q*p < 256 ");
                        l_bad.setVisible(true);
                        text_b.setDisable(true);
                    }
                }else  text_b.setDisable(true);
            }
        } );
        text_p.setOnMouseEntered(mouseEvent -> {
           l_good.setVisible(false);
           l_bad.setVisible(false);
           l_bad.setPrefHeight(p_height);
           l_good.setPrefHeight(p_height);
           p_bad.setVisible(false);
           p_ok.setVisible(false);
       });
        text_p.setOnMouseExited(mouseEvent -> {
            if ( p_simple ) {
                p_ok.setVisible(true);
                l_good.setVisible(false);
                l_bad.setVisible(false);

            }
            else  if ( text_p.getLength() != 0) {
                l_good.setVisible(false);
                l_bad.setVisible(false);
                p_bad.setVisible(true);
            }

        });

        text_b.textProperty().addListener((observableValue, oldValue, newValue) -> {
            pattern = Pattern.compile(regEx) ;
            if (!pattern.matcher(newValue).matches() )
                text_b.setText(oldValue);

            else {
                if (BigInteger.valueOf(Integer.parseInt(text_q.getText())).multiply(
                        BigInteger.valueOf(Integer.parseInt(text_p.getText()))).compareTo(
                        BigInteger.valueOf(Integer.parseInt(text_b.getText()))) > 0) {
                    l_bad.setVisible(false);
                    l_good.setVisible(true);
                    b_simple = true ;
                    l_good.setText(" b < n = q*p");
                } else {
                    b_simple = false ;
                    l_good.setVisible(false);
                    l_bad.setVisible(true);
                    l_bad.setText(" b > n = q*p");
                }
            }
        } );
        text_b.setOnMouseEntered(mouseEvent -> {
           if (!text_b.isDisable()) {
               l_good.setVisible(false);
               l_bad.setVisible(false);
               l_bad.setPrefHeight(b_height);
               l_good.setPrefHeight(b_height);
               b_ok.setVisible(false);
               b_bad.setVisible(false);
           }
        });
        text_b.setOnMouseExited(mouseEvent -> {
            if ( b_simple ) {
                l_good.setVisible(false);
                l_bad.setVisible(false);
                b_ok.setVisible(true);
            }
            else  if ( text_b.getLength() != 0) {
                l_good.setVisible(false);
                l_bad.setVisible(false);
                b_bad.setVisible(true);
            }
        });

        b_go.setOnAction(actionEvent -> {

            if ( q_ok.isVisible() && p_ok.isVisible() && b_ok.isVisible()) {

                RabinCypher rabinCypher = new RabinCypher(
                    new BigInteger (text_q.getText()),
                    new BigInteger(text_p.getText()),
                    new BigInteger(text_b.getText())) ;

                rabinCypher.encryptFile();
                encryptFile.setText(rabinCypher.getEncryptFile());
                rabinCypher.decryptFile();
                textFile1.setText(rabinCypher.getF1());
                textFile2.setText(rabinCypher.getF2());
                textFile3.setText(rabinCypher.getF3());
                textFile4.setText(rabinCypher.getF4());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Введены некорректные данные");
                alert.showAndWait();
            }

        });
        b_fresh.setOnAction(actionEvent -> {
           Ltext.setText(readText());
        });
    }

    private boolean showTextCondition (String value, TextArea textArea) {
        boolean simple = false ;
        if (textArea.getLength() != 0) {
            if (PrimeNumber.cheсkSimplicity( new BigInteger (textArea.getText())) ) {
                if (PrimeNumber.checkСondition( new BigInteger (textArea.getText())) ) {
                    l_bad.setVisible(false);
                    l_good.setVisible(true);
                    l_good.setText(" "+value+" простое и "+value+" mod 4 = 3");
                    simple = true ;
                } else {
                    l_good.setVisible(false);
                    l_bad.setVisible(true);
                    l_bad.setText(" "+value+" простое, но "+value+" mod 4 != 3");
                }
            } else {
                l_good.setVisible(false);
                l_bad.setVisible(true);
                l_bad.setText(" "+value+" не простое");
            }
        } else {
            l_bad.setVisible(false);
            l_good.setVisible(false);
        }
            if ((q_ok.isVisible() || p_ok.isVisible()) && l_good.isVisible()) text_b.setDisable(false);
                else text_b.setDisable(true);

        return  simple;
    }

    private String readText () {

        StringBuilder text = new StringBuilder();
        try (InputStreamReader fileReader = new InputStreamReader(
                new FileInputStream(path+"text.txt")) ) {

            int tbyte = fileReader.read();

            while (tbyte != -1 ){
                text.append(tbyte+" ") ;
                tbyte = fileReader.read();
            }
        } catch (IOException e ) {

        }

        return String.valueOf(text);
    }

    }


