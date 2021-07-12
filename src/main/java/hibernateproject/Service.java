package hibernateproject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;


public class Service
{
    private Customer customer;

    public static void main( String[] args )
    {
        Service entities = new Service();
        entities.createCustomer();
        setUpTheEntities(entities.getCustomer());
    }

    /**
     * Inside this method you establish the connection to the database and begin a session and a transaction inside
     * that session
     * @param customer used to be saved inside the database. Since I am using cascadeType inside the relational mapping
     * I do not need to have a second object being saved sepparately inside the databse for CustomerRoles. Instead this
     *                 procedure is done inside the createCustomer() class.
     */
    public static void setUpTheEntities(Customer customer)
    {
        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sf.openSession();

        session.beginTransaction();

        session.save(customer);
        outputCustomerAndCustomerRoles(session);

        session.getTransaction().commit();
    }

    /**
     * Inside this method I instanciate new object from Customer and add 3 roles to that Customer. The roles are added
     * as new CustomerRoles objects.
     */
    public void createCustomer()
    {

        customer = new Customer();
        customer.setName("James");
        customer.addRoles(new CustomerRoles("Engineer"));
        customer.addRoles(new CustomerRoles("Mechanic"));
        customer.addRoles(new CustomerRoles("Manager"));

    }

    /**
     * This method is used to query the data from the database. Currently without any exception catching only works
     * if a valid Id is queried.
     * @param session used to connect to the Database and extract the necessary data from both entities
     */
    public static void outputCustomerAndCustomerRoles(Session session)
    {

        Customer cust = session.get(Customer.class,Long.valueOf(1));
        System.out.println("Customer name: "+cust.getName());
        System.out.println("The roles this customer can take:");
        for(CustomerRoles role: cust.getRoles())
        {
            System.out.println(role.getDescription());
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}
