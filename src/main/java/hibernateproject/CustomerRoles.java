package hibernateproject;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_roles")
public class CustomerRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    //Use generation type SEQUENCE and do not use TABLE because it is the slowest way of generating id's
    private long roleId;

    @ManyToOne
    private Customer customer;

    private String description;

    //You need a default constructor in order to be able to query values from the database
    public CustomerRoles() {}

    public CustomerRoles (String description)
    {
        this.description = description;
    }

    public long getRoleId()
    {
        return roleId;
    }
    public void setRoleId(long roleId)
    {
        this.roleId = roleId;
    }


    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }


    public String getDescription() { return description; }
    public void setDescription(String description) { this.description=description; }

}
