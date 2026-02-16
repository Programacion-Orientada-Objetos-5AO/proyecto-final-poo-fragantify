package ar.edu.huergo.lbgonzalez.fragantify.entity.mascota;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="mascota")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class MascotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre ;
    
    private String  tipo ;
    
    private Integer edad ;
    
    private boolean adoptado ;



    
}
