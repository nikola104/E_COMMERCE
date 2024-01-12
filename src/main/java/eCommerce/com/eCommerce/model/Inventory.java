package eCommerce.com.eCommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "inventory")
//za posle bydirectional endless loop
//@JsonIgnoreProperties({"product"})
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long quantity;
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;





}
