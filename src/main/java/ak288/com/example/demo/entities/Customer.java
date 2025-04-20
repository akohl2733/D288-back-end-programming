package ak288.com.example.demo.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="customers")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name="address", nullable = false)
    private String address;

    @Column(name="create_date")
    @CreationTimestamp
    private Date create_date;

    @Column(name="last_update")
    @UpdateTimestamp
    private Date last_update;

    @Column(name="customer_first_name", nullable = false)
    private String firstName;

    @Column(name="customer_last_name", nullable = false)
    private String lastName;

    @Column(name="phone", nullable = false)
    private String phone;

    @Column(name="postal_code", nullable = false)
    private String postal_code;

    @ManyToOne
    @JoinColumn(name="division_id", nullable = false)
    private Division division;

    @OneToMany(mappedBy = "customer")
    private Set<Cart> carts;

    public Customer() {
    }
}
