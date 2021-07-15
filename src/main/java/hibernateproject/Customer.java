package hibernateproject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId")
    private Long customerId;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_roles",
                joinColumns = {@JoinColumn(name = "customerId")},
                inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<CustomerRoles> roles;

//    constructor
//    public Customer(){}

//    Getters and Setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }


    public List<CustomerRoles> getRoles() { return roles; }
    public void setRoles(List<CustomerRoles> roles) { this.roles = roles;}

    /**
     * This method adds the roles created specifically for this customer.
     * @param role is used to store the object of the newly created role for the customer
     */
//    public void addRoles(CustomerRoles role)
//    {
//        roles.add(role);
////        //This line uses the setter method from the CustomerRoles class to establish the relationship between
////        //Customer and CustomerRoles for a specific customerId
////        role.setCustomer(this);
//    }

}
