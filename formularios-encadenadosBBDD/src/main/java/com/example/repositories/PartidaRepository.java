package com.example.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entities.Partida;

public interface PartidaRepository extends JpaRepository<Partida, Long> {
    public List<Partida> findByOrderByAficcionesDesc();//ordenar por aficciones descendentemente
        public List<Partida> findByOrderByAficcionesAsc();//ordenar por aficciones ascendentemente

    public List<Partida> findByOrderBySignoAsc();//ordenar por signo ascendentemente
        public List<Partida> findByOrderBySignoDesc();//ordenar por signo descendentemente

    public List<Partida> findByOrderByFechaDesc();//ordenar por signo descendentemente
    public List<Partida> findByOrderByFechaAsc();//ordenar por signo ascendentemente

    public List<Partida> findByOrderByJugadorNombreAsc();//ordenar por aficciones descendentemente
    public List<Partida> findByOrderByJugadorNombreDesc();//ordenar por aficciones descendentemente

}
