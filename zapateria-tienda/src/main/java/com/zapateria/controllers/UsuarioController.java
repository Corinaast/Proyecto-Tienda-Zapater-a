package com.zapateria.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zapateria.models.Usuario;
import com.zapateria.repositories.UsuarioRepository;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // Login
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Usuario loginData) {
        Map<String, Object> response = new HashMap<>();
        
        Usuario usuario = usuarioRepository.findByUsernameAndPassword(
            loginData.getUsername(),
            loginData.getPassword()
        );
        
        if (usuario != null) {
            response.put("success", true);
            response.put("mensaje", "Login exitoso");
            response.put("usuario", usuario);
        } else {
            response.put("success", false);
            response.put("mensaje", "Credenciales incorrectas");
        }
        
        return response;
    }
    
    // Registro de clientes
    @PostMapping("/registro-cliente")
    public Map<String, Object> registroCliente(@RequestBody Usuario nuevoUsuario) {
        Map<String, Object> response = new HashMap<>();
        
        if (usuarioRepository.findByUsername(nuevoUsuario.getUsername()) != null) {
            response.put("success", false);
            response.put("mensaje", "El usuario ya existe");
            return response;
        }
        
        if (usuarioRepository.findByEmail(nuevoUsuario.getEmail()) != null) {
            response.put("success", false);
            response.put("mensaje", "El email ya está registrado");
            return response;
        }
        
        nuevoUsuario.setRol("CLIENTE");
        Usuario guardado = usuarioRepository.save(nuevoUsuario);
        
        response.put("success", true);
        response.put("mensaje", "Registro exitoso");
        response.put("usuario", guardado);
        
        return response;
    }
    
    // Registro de administradores (con código secreto)
    @PostMapping("/registro-admin")
    public Map<String, Object> registroAdmin(@RequestBody Map<String, String> data) {
        Map<String, Object> response = new HashMap<>();
        
        //String codigoSecreto = data.get("codigoSecreto");
        
        // Código secreto para crear administradores
        /*if (!"ADMIN2024".equals(codigoSecreto)) {
            response.put("success", false);
            response.put("mensaje", "Código secreto incorrecto");
            return response;
        }*/
        
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(data.get("nombre"));
        nuevoUsuario.setApellido(data.get("apellido"));
        nuevoUsuario.setEmail(data.get("email"));
        nuevoUsuario.setUsername(data.get("username"));
        nuevoUsuario.setPassword(data.get("password"));
        nuevoUsuario.setRol("ADMINISTRADOR");
        
        if (usuarioRepository.findByUsername(nuevoUsuario.getUsername()) != null) {
            response.put("success", false);
            response.put("mensaje", "El usuario ya existe");
            return response;
        }
        
        Usuario guardado = usuarioRepository.save(nuevoUsuario);
        
        response.put("success", true);
        response.put("mensaje", "Administrador registrado exitosamente");
        response.put("usuario", guardado);
        
        return response;
    }
    
    // Listar todos los usuarios
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
    
    // Obtener usuario por ID
    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable String id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    
    // Actualizar usuario
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }
    
    // Eliminar usuario
    @DeleteMapping("/{id}")
    public Map<String, Object> eliminarUsuario(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        usuarioRepository.deleteById(id);
        response.put("success", true);
        response.put("mensaje", "Usuario eliminado");
        return response;
    }
}