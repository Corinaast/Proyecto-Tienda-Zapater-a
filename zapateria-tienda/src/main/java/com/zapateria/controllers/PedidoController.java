package com.zapateria.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zapateria.models.Pedido;
import com.zapateria.models.Producto;
import com.zapateria.repositories.PedidoRepository;
import com.zapateria.repositories.ProductoRepository;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Listar todos los pedidos
    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    // Obtener pedido por ID
    @GetMapping("/{id}")
    public Pedido obtenerPedido(@PathVariable String id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    // Pedidos por cliente
    @GetMapping("/cliente/{clienteId}")
    public List<Pedido> pedidosPorCliente(@PathVariable String clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    // Pedidos por estado
    @GetMapping("/estado/{estado}")
    public List<Pedido> pedidosPorEstado(@PathVariable String estado) {
        return pedidoRepository.findByEstado(estado);
    }

    // Crear nuevo pedido
    @PostMapping
    public Map<String, Object> crearPedido(@RequestBody Pedido pedido) {
        Map<String, Object> response = new HashMap<>();

        // Generar número de pedido
        pedido.setNumeroPedido("PED-" + System.currentTimeMillis());

        // Calcular total
        double total = 0;
        for (Pedido.ItemPedido item : pedido.getItems()) {
            total += item.getPrecio() * item.getCantidad();

            // Actualizar stock del producto
            Producto producto = productoRepository.findById(item.getProductoId()).orElse(null);
            if (producto != null) {
                producto.setStock(producto.getStock() - item.getCantidad());
                productoRepository.save(producto);
            }
        }
        pedido.setTotal(total);

        Pedido guardado = pedidoRepository.save(pedido);
        response.put("success", true);
        response.put("mensaje", "Pedido creado exitosamente");
        response.put("pedido", guardado);
        return response;
    }

    // Actualizar estado del pedido
    @PutMapping("/{id}/estado")
    public Map<String, Object> actualizarEstado(@PathVariable String id, @RequestBody Map<String, String> data) {
        Map<String, Object> response = new HashMap<>();

        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido != null) {
            pedido.setEstado(data.get("estado"));
            pedidoRepository.save(pedido);
            response.put("success", true);
            response.put("mensaje", "Estado actualizado");
            response.put("pedido", pedido);
        } else {
            response.put("success", false);
            response.put("mensaje", "Pedido no encontrado");
        }

        return response;
    }

    // Eliminar pedido
    @DeleteMapping("/{id}")
    public Map<String, Object> eliminarPedido(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        pedidoRepository.deleteById(id);
        response.put("success", true);
        response.put("mensaje", "Pedido eliminado");
        return response;
    }
}