package hibernateproject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    private long customerId;
    public long getCustomerId() { return customerId; }
    public void setCustomerId(long customerId) { this.customerId = customerId; }

    private String name;
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }


    @OneToMany(mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch =FetchType.EAGER)
    private List<CustomerRoles> roles = new ArrayList<>();
    public List<CustomerRoles> getRoles()
    {
        return roles;
    }

    public void setRoles(List<CustomerRoles> roles) {
        this.roles = roles;
    }

    /**
     * This method adds the roles created specifically for this customer.
     * @param role is used to store the object of the newly created role for the customer
     */
    public void addRoles(CustomerRoles role)
    {
        roles.add(role);
        //This line uses the setter method from the CustomerRoles class to establish the relationship between
        //Customer and CustomerRoles for a specific customerId
        role.setCustomer(this);
    }

}
