package com.simulador;

import java.util.Scanner;

public class SimuladorEvacuacion {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  SIMULADOR DE EVACUACIÓN - INSTALACIÓN SECRETA  ");
        System.out.println("=================================================");
        System.out.println("\nSISTEMA INICIADO: Protocolo de evacuación activado");
        System.out.println("ALERTA: Falla crítica detectada en instalación");
        System.out.println("OBJETIVO: Localizar punto de evacuación y escapar");
        System.out.println("\nINSTRUCCIONES:");
        System.out.println("- Ingrese el número correspondiente para moverse");
        System.out.println("- Escriba 'TOMAR' para recoger objetos");
        System.out.println("- Escriba 'INV' para revisar sus recursos");
        System.out.println("- Escriba 'FIN' para abortar la misión");
        System.out.println("- Escriba 'SALIR' cuando encuentre el punto de evacuación");
        System.out.println("\nINICIANDO SIMULACIÓN...\n");
        
        MecanismoEvasion simulador = new MecanismoEvasion();
        Scanner scanner = new Scanner(System.in);
        
        if (!simulador.verificarRutaEvacuacion()) {
            System.out.println("ERROR: No se detecta ruta de evacuación posible.");
            System.out.println("Abortando simulación...");
            return;
        }
        
        while (!simulador.isMisionCompleta()) {
            simulador.mostrarInterfaz();
            System.out.print("\n> ");
            String entrada = scanner.nextLine().trim();
            simulador.procesarComando(entrada);
        }
        
        System.out.println("\n=================================================");
        System.out.println("        SIMULACIÓN FINALIZADA - DESCONECTANDO     ");
        System.out.println("=================================================");
        
        scanner.close();
    }
}
