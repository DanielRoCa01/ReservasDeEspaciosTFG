package com.example.reservasdeespaciostfg;

import Modelo.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        AccesoSQL ac=new AccesoSQL();




       // ac.escribirEspacio(new Espacio(2,"laboratorio","xl",new Time(9000000),new Time(1000000),"aupa",ac.consultarInstalacion(2)));
        ac.escribirReserva(new Reserva(3,ac.consultarEspacio(1),ac.consultarUsuario(4),new Time(10000),new Time(1200000),new Date(5000000000000L),"Prueba","siuu"));

        //ac.escribirUsuario(new Usuario(2,"Juan","Usuario","Centro",ac.consultarInstalacion(2)));
        for(Instalacion instalacion:ac.leerInstalaciones()){
            System.out.println(instalacion.toString());
        }
        for(Usuario instalacion:ac.leerUsuarios(1)){
            System.out.println(instalacion.toString());
        }
        for(Espacio instalacion:ac.leerEspaciosLibres(1,new Date(1000000000),new Time(1200000),new Time(1500000))){
            System.out.println(instalacion.toString());
        }
        for(Reserva instalacion:ac.leerReservas(new Date(5000000000000L),null,null,null,null,"TODOS")){
            System.out.println(instalacion.toString());
        }

        System.out.println(ac.comprobarNombreEspacio("Juan"));
    }
}
