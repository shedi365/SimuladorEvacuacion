package com.simulador;

import com.simulador.modelo.*;
import java.util.*;

public class MecanismoEvasion {
    private Espacio ubicacionActual;
    private Set<String> recursos;
    private boolean misionCompleta;
    
    public MecanismoEvasion() {
        this.recursos = new HashSet<>();
        this.misionCompleta = false;
        configurarInstalacion();
    }
    
    private void configurarInstalacion() {
        Espacio zonaEntrada = new Espacio("Sector Alpha", "Área principal con daños estructurales visibles");
        Espacio centroControl = new Espacio("Sector Beta", "Sala de monitoreo con equipos dañados");
        Espacio labPrincipal = new Espacio("Sector Gamma", "Laboratorio principal con contenedores rotos");
        Espacio labSecundario = new Espacio("Sector Delta", "Área de experimentación secundaria");
        Espacio almacenamiento = new Espacio("Sector Epsilon", "Depósito de materiales peligrosos");
        Espacio oficinaJefe = new Espacio("Sector Zeta", "Oficina del director de investigación");
        Espacio salidaEmergencia = new Espacio("Sector Omega", "Vía de evacuación principal");
        salidaEmergencia.marcarSalida();
        
        centroControl.agregarObjeto("tarjeta magnética");
        labPrincipal.agregarObjeto("generador portátil");
        almacenamiento.agregarObjeto("dispositivo de autenticación");
        oficinaJefe.agregarObjeto("código maestro");
        
        zonaEntrada.establecerConexion(centroControl, "Puerta blindada A1");
        centroControl.establecerConexion(zonaEntrada, "Puerta blindada A2");
        centroControl.establecerConexion(labPrincipal, "Compuerta de seguridad B1");
        centroControl.establecerConexionSegura(labSecundario, "Puerta reforzada B2", "tarjeta magnética");
        labPrincipal.establecerConexion(centroControl, "Compuerta de seguridad B3");
        labPrincipal.establecerConexion(almacenamiento, "Túnel de servicio C1");
        labSecundario.establecerConexionSegura(oficinaJefe, "Puerta de alta seguridad D1", "dispositivo de autenticación");
        almacenamiento.establecerConexion(labPrincipal, "Túnel de servicio C2");
        oficinaJefe.establecerConexionSegura(salidaEmergencia, "Salida de contingencia E1", "código maestro");
        
        ubicacionActual = zonaEntrada;
    }
    
    public boolean verificarRutaEvacuacion() {
        Set<Espacio> revisados = new HashSet<>();
        Queue<Espacio> colaVerificacion = new ArrayDeque<>();
        
        colaVerificacion.add(ubicacionActual);
        revisados.add(ubicacionActual);
        
        while (!colaVerificacion.isEmpty()) {
            Espacio actual = colaVerificacion.poll();
            
            if (actual.puntoLiberacion) {
                return true;
            }
            
            for (ConexionSegura conexion : actual.vias) {
                if (!revisados.contains(conexion.conectado)) {
                    revisados.add(conexion.conectado);
                    colaVerificacion.add(conexion.conectado);
                }
            }
        }
        
        return false;
    }
    
    public void mostrarInterfaz() {
        System.out.println("\n>> " + ubicacionActual.identificador + " <<");
        System.out.println(ubicacionActual.detalles);
        
        if (ubicacionActual.puntoLiberacion) {
            System.out.println("\n¡PUNTO DE EVACUACIÓN LOCALIZADO!");
            System.out.println("Ingrese 'SALIR' para finalizar la misión");
            return;
        }
        
        System.out.println("\nVías disponibles:");
        int contador = 1;
        for (ConexionSegura via : ubicacionActual.vias) {
            System.out.println(contador++ + ". " + via.codigoAcceso + " -> " + via.conectado.identificador);
        }
        
        System.out.println("\nObjetos detectados:");
        if (ubicacionActual.objetos.isEmpty()) {
            System.out.println("Ningún objeto de interés identificado");
        } else {
            for (String obj : ubicacionActual.objetos) {
                System.out.println("- " + obj);
            }
            System.out.println("Ingrese 'TOMAR' para recoger objeto");
        }
        
        System.out.println("\nRecursos disponibles: " + recursos);
        System.out.println("\nOpciones: [1-" + (contador-1) + "] moverse, [TOMAR] recoger, [INV] revisar recursos, [FIN] abortar misión");
    }
    
    public boolean isMisionCompleta() {
        return misionCompleta;
    }
}

public void procesarComando(String entrada) {
    if (entrada.equalsIgnoreCase("FIN")) {
        misionCompleta = true;
        System.out.println("Misión abortada por el operador");
        return;
    }
    
    if (entrada.equalsIgnoreCase("INV")) {
        System.out.println("Recursos actuales: " + recursos);
        return;
    }
    
    if (entrada.equalsIgnoreCase("TOMAR")) {
        if (!ubicacionActual.objetos.isEmpty()) {
            String objeto = ubicacionActual.objetos.remove(0);
            recursos.add(objeto);
            System.out.println("Objeto adquirido: " + objeto);
        } else {
            System.out.println("No hay objetos disponibles");
        }
        return;
    }
    
    if (ubicacionActual.puntoLiberacion && entrada.equalsIgnoreCase("SALIR")) {
        System.out.println("\n¡EVACUACIÓN EXITOSA! Has completado la misión");
        misionCompleta = true;
        return;
    }
    
    try {
        int seleccion = Integer.parseInt(entrada) - 1;
        if (seleccion >= 0 && seleccion < ubicacionActual.vias.size()) {
            ConexionSegura viaSeleccionada = ubicacionActual.vias.get(seleccion);
            
            if (viaSeleccionada.accesoRestringido) {
                if (recursos.contains(viaSeleccionada.credencialNecesaria)) {
                    ubicacionActual = viaSeleccionada.conectado;
                    System.out.println("Credencial " + viaSeleccionada.credencialNecesaria + " verificada");
                } else {
                    System.out.println("Acceso denegado. Se requiere: " + viaSeleccionada.credencialNecesaria);
                }
            } else {
                ubicacionActual = viaSeleccionada.conectado;
            }
        } else {
            System.out.println("Opción no válida");
        }
    } catch (NumberFormatException ex) {
        System.out.println("Entrada no reconocida");
    }
}
