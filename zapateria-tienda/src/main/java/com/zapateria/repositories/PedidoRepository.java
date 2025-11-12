package com.zapateria.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zapateria.models.Pedido;

@Repository
public interface PedidoRepository extends MongoRepository<Pedido, String> {
    // Buscar pedidos por cliente
    List<Pedido> findByClienteId(String clienteId);

    // Buscar pedidos por estado
    List<Pedido> findByEstado(String estado);

    // Buscar pedido por número
    Pedido findByNumeroPedido(String numeroPedido);
}