package com.project;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

public class Controller0 {
    Random random = new Random();
        

    @FXML
    private Button button0, boton1, boton2, boton3;
    @FXML
    private AnchorPane container;
    @FXML
    private Label p1, p2 , p3;
    @FXML
    private ProgressBar var1, var2 , var3;

    private int progress1 ;
    private int progress2 ;
    private int progress3 ;
    
    private ExecutorService executor = Executors.newFixedThreadPool(3);
    private Future<?> tarea1 ,tarea2,tarea3;
    private boolean task1 = false;
    private boolean task2 = false;
    private boolean task3 = false;


    

    @FXML
    private void animateToView1(ActionEvent event) {
        UtilsViews.setViewAnimating("View1");
    }

    @FXML
    private void runtareas(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        if (sourceButton== boton1){
            if (task1 == true){
                boton1.setText("iniciar");
                tarea1.cancel(true);
                task1 = false;
            }else {
                boton1.setText("Stop");
                tarea1 = backgroundTask(0, progress1);
                task1 = true;
            }
            
        }
        if (sourceButton== boton2){
            if (task2 == true){
                boton2.setText("iniciar");
                tarea2.cancel(true);
                task2 = false;
            }else {
                boton2.setText("Stop");
                tarea2 = backgroundTask(1, progress2);
                task2 = true;
            }
        }
        if (sourceButton== boton3){
            if (task3 == true){
                boton3.setText("iniciar");
                tarea3.cancel(true);
                task3 = false;
            }else {
                boton3.setText("Stop");
                tarea3 = backgroundTask(2, progress3);
                task3 = true;
            }
        }
    }


    private Future<?> backgroundTask(int index, int numprogreso) {
        final int finalvalor = numprogreso;

        return executor.submit(() -> {
            int valor = finalvalor;
            Random random = new Random();
            while (valor < 100) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                valor++;
                if (valor > 100) {
                    valor = 100;
                }
                final int currentValue;
                if (valor < 100){
                    currentValue = valor;
                }else {
                    currentValue = 0; 
                }
                if (index == 0) {
                    Platform.runLater(() -> {
                        progress1=currentValue;
                        p1.setText(String.valueOf(currentValue) + "%");                            
                        var1.setProgress(currentValue/100.0);
                        progress1=currentValue;
                        if (progress1 == 99) {
                            progress1 = 0;
                            boton1.setText("Activar");
                        } 
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (index == 1) {
                    int randomNumber = random.nextInt(3) + 2; 
                    int numeroAleatorio = (random.nextInt(3) + 3);
                    numeroAleatorio = numeroAleatorio*1000;
                    int numero = valor+randomNumber;
                    Platform.runLater(() -> {
                        progress2=currentValue;
                        p2.setText(String.valueOf(numero) + "%");
                        var2.setProgress(numero/100.0);
                        if (progress2 == 99){
                            stopExecutor();
                            boton2.setText("Activar");
                        }
                    });
                    try {
                        Thread.sleep(numeroAleatorio);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } 
                }
                if (index == 2) {
                    int randomNumber = random.nextInt(3) + 4;
                    int numero = currentValue+randomNumber;
                    final int finalnumero = numero+randomNumber;
                    System.err.println("current value = "+currentValue);
                    System.out.println("aleatorio= "+randomNumber);
                    System.out.println("resultado =" +numero);
                    int numeroAleatorio = random.nextInt(6) * 1000 + 3000;
                    Platform.runLater(() -> {
                        progress3=currentValue;
                        p3.setText(String.valueOf(finalnumero) + "%");
                        var3.setProgress(numero/100.0);
                        if (progress3 == 99){
                            stopExecutor();
                            boton3.setText("Activar");
                        }
                    });
                    try {
                        Thread.sleep(numeroAleatorio);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } 
                }
            }
        });
    }
    public void stopExecutor() {
        executor.shutdown();
    }

}