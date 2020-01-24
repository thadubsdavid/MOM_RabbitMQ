package com.example;

import java.util.Random;

class TemperaturSensor{
    private int _temperatur;
    private String _ort;

    public TemperaturSensor(int randomNumberOne, String ort){
        _temperatur = randomNumberOne;
        _ort = ort;
    }

	public String ToString(){
        return "    In " + _ort + " hat es " + Integer.toString(_temperatur) + "C";
    }

    public void getTemperatureLocationA() {
        Random dummytemperatureA = new Random();
        
        if (dummytemperatureA.nextInt(1) == 0){
             _temperatur = _temperatur + dummytemperatureA.nextInt(2);
        }
        else {
             _temperatur = _temperatur - dummytemperatureA.nextInt(2);
        }
    }
    public void getTemperatureLocationB() {
        Random dummytemperatureB = new Random();
        
        if (dummytemperatureB.nextInt(1) == 0){
             _temperatur = _temperatur + dummytemperatureB.nextInt(4);
        }
        else {
             _temperatur = _temperatur - dummytemperatureB.nextInt(3);
        }
    }
}
