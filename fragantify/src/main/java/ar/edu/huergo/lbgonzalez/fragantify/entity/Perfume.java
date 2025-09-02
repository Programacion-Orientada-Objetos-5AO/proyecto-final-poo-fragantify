package ar.edu.huergo.lbgonzalez.fragantify.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Perfume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String tipo;
    private String familiaOlfativa;
    private Double precio;
    private Boolean favorito = false;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    // Constructors
    public Perfume() {}

    public Perfume(String nombre, String tipo, String familiaOlfativa, Double precio, Marca marca) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.familiaOlfativa = familiaOlfativa;
        this.precio = precio;
        this.marca = marca;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFamiliaOlfativa() { return familiaOlfativa; }
    public void setFamiliaOlfativa(String familiaOlfativa) { this.familiaOlfativa = familiaOlfativa; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Marca getMarca() { return marca; }
    public void setMarca(Marca marca) { this.marca = marca; }

    public Boolean getFavorito() { return favorito; }
    public void setFavorito(Boolean favorito) { this.favorito = favorito; }
}
