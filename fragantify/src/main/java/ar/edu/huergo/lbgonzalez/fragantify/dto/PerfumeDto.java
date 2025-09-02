package ar.edu.huergo.lbgonzalez.fragantify.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PerfumeDto {

    public static class MostrarPerfumeDTO {
        private Long id;
        private String nombre;
        private String tipo;
        private String familiaOlfativa;
        private Double precio;
        private String marcaNombre;
        private Boolean favorito;

        // Constructors
        public MostrarPerfumeDTO() {}

        public MostrarPerfumeDTO(Long id, String nombre, String tipo, String familiaOlfativa, Double precio, String marcaNombre, Boolean favorito) {
            this.id = id;
            this.nombre = nombre;
            this.tipo = tipo;
            this.familiaOlfativa = familiaOlfativa;
            this.precio = precio;
            this.marcaNombre = marcaNombre;
            this.favorito = favorito;
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

        public String getMarcaNombre() { return marcaNombre; }
        public void setMarcaNombre(String marcaNombre) { this.marcaNombre = marcaNombre; }

        public Boolean getFavorito() { return favorito; }
        public void setFavorito(Boolean favorito) { this.favorito = favorito; }
    }

    public static class CrearActualizarPerfumeDTO {
        @NotBlank
        private String nombre;
        @NotBlank
        private String tipo;
        @NotBlank
        private String familiaOlfativa;
        @NotNull
        @Positive
        private Double precio;
        @NotNull
        private Long marcaId;

        // Constructors
        public CrearActualizarPerfumeDTO() {}

        public CrearActualizarPerfumeDTO(String nombre, String tipo, String familiaOlfativa, Double precio, Long marcaId) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.familiaOlfativa = familiaOlfativa;
            this.precio = precio;
            this.marcaId = marcaId;
        }

        // Getters and Setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }

        public String getFamiliaOlfativa() { return familiaOlfativa; }
        public void setFamiliaOlfativa(String familiaOlfativa) { this.familiaOlfativa = familiaOlfativa; }

        public Double getPrecio() { return precio; }
        public void setPrecio(Double precio) { this.precio = precio; }

        public Long getMarcaId() { return marcaId; }
        public void setMarcaId(Long marcaId) { this.marcaId = marcaId; }
    }
}
