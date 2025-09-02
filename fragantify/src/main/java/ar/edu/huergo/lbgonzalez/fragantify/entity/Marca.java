package ar.edu.huergo.lbgonzalez.fragantify.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @OneToMany(mappedBy = "marca")
    private List<Perfume> perfumes;

    // Constructors
    public Marca() {}

    public Marca(String nombre) {
        this.nombre = nombre;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Perfume> getPerfumes() { return perfumes; }
    public void setPerfumes(List<Perfume> perfumes) { this.perfumes = perfumes; }
}
