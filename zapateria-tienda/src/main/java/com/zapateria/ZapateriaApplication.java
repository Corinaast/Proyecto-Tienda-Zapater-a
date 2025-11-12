package com.zapateria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZapateriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZapateriaApplication.class, args);

        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║   Sistema de Zapatería - Versión Simple      ║");
        System.out.println("║   Servidor iniciado correctamente            ║");
        System.out.println("║   URL: http://localhost:8080                  ║");
        System.out.println("╚═══════════════════════════════════════════════╝\n");

        System.out.println("📚 Endpoints disponibles:");
        System.out.println("  • POST /api/usuarios/login         - Login");
        System.out.println("  • POST /api/usuarios/registro      - Registro");
        System.out.println("  • GET  /api/productos              - Listar productos");
        System.out.println("  • POST /api/productos              - Crear producto");
        System.out.println("  • GET  /api/clientes               - Listar clientes");
        System.out.println("  • POST /api/pedidos                - Crear pedido");
        System.out.println();
    }
}