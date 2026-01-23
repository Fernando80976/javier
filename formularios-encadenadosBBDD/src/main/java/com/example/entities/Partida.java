package com.example.entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int puntuacion;

    private LocalDateTime fecha;

    private String categoria; 

    @ManyToOne
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;

    private String signo;
    private String aficciones;

    // Getters y setters
    public Long getId() { return id; }
    
    public void setId(Long id) { this.id = id; }
    
    public int getPuntuacion() { return puntuacion; }
    
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }
    
    public LocalDateTime getFecha() { return fecha; }
    
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    
    public String getCategoria() { return categoria; }
    
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public Jugador getJugador() { return jugador; }
    
    public void setJugador(Jugador jugador) { this.jugador = jugador; }
    
    public String getSigno() { return signo; }
    
    public void setSigno(String signo) { this.signo = signo;}
    
    public String getAficciones() { return aficciones; }
    
    
    public void setAficciones(String aficciones) { this.aficciones = aficciones; }
}

