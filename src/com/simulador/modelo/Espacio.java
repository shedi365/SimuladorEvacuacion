package com.simulador.modelo;

import java.util.*;

public class Espacio {
    String identificador;
    String detalles;
    List<ConexionSegura> vias;
    List<String> objetos;
    boolean puntoLiberacion;
    
    Espacio(String id, String info) {
        this.identificador = id;
        this.detalles = info;
        this.vias = new LinkedList<>();
        this.objetos = new ArrayList<>();
        this.puntoLiberacion = false;
    }
    
    void establecerConexion(Espacio destino, String clave) {
        vias.add(new ConexionSegura(destino, clave));
    }
    
    void establecerConexionSegura(Espacio destino, String clave, String credencial) {
        vias.add(new ConexionSegura(destino, clave, credencial));
    }
    
    void agregarObjeto(String objeto) {
        objetos.add(objeto);
    }
    
    void marcarSalida() {
        this.puntoLiberacion = true;
    }
}
