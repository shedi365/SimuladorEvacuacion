package com.simulador.modelo;

public class ConexionSegura {
    String codigoAcceso;
    Espacio conectado;
    boolean accesoRestringido;
    String credencialNecesaria;
    
    ConexionSegura(Espacio destino, String clave) {
        this.conectado = destino;
        this.codigoAcceso = clave;
        this.accesoRestringido = false;
    }
    
    ConexionSegura(Espacio destino, String clave, String credencial) {
        this(destino, clave);
        this.accesoRestringido = true;
        this.credencialNecesaria = credencial;
    }
}
