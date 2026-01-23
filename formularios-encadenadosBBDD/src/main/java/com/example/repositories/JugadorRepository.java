package com.example.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entities.Jugador;
import java.util.Optional;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    Optional<Jugador> findByNombre(String nombre);
}

