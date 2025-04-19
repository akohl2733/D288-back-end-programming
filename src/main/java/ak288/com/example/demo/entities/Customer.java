package ak288.com.example.demo.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
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
    private Division division_id;

    @OneToMany(mappedBy = "customer")
    private Set<Cart> carts;

    public Customer() {
    }

    public Customer(Long id, String firstName, String lastName, String address, String postal_code, String phone, Date create_date, Date last_update, Division division) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.create_date = create_date;
        this.last_update = last_update;
        this.division_id = division;
    }

    public void addCart(Cart cart) {
        if (cart != null) {
            if (carts == null) {
                carts = new HashSet<>();
            }
            carts.add(cart);
            cart.setCustomer(this);
        }
    }

}
