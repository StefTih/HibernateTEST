package hibernateproject;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class CustomerRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleId")
    //Use generation type SEQUENCE and do not use TABLE because it is the slowest way of generating id's
    private Long roleId;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roles")
    private List<Customer> customer = new ArrayList<>();

    //You need a default constructor in order to be able to query values from the database
    public CustomerRoles() {}


//    getters and setters

    public Long getRoleId()
    {
        return roleId;
    }
    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }


    public List<Customer> getCustomer() { return customer; }
    public void setCustomer(List<Customer> customer) { this.customer = customer; }


    public String getDescription() { return description; }
    public void setDescription(String description) { this.description=description; }


//    public void addCustomers(Customer cust)
//    {
//        customer.add(cust);
////        //This line uses the setter method from the CustomerRoles class to establish the relationship between
////        //Customer and CustomerRoles for a specific customerId
////        role.setCustomer(this);
//    }
}
