package hibernateproject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Service
{
    private ServiceRegistry registry;
    private SessionFactory sf;
    private Session session;
    private Transaction transaction;

    private List<CustomerRoles> listRoles;
    public static void main( String[] args )
    {
        Service service = new Service();
        service.setUpTheEntities();
    }

    /**
     * Inside this method you establish the connection to the database and begin a session and a transaction inside
     * that session
     *
     */
    public void setUpTheEntities()
    {
        registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        listRoles = new ArrayList<>();

        usingSave();
        usingPersist();
        usingMerge();
        detachingAndReattaching();
        outputCustomerAndCustomerRoles();
        usingRefresh();
        session.close();

    }

    public void usingSave()
    {
        session = sf.openSession();

        //Good to create a new variable in which you can store your transaction
        transaction = session.beginTransaction();

        //Adding customers
        Customer customer = new Customer();
        customer.setName("Jim");

        //Adding roles
        CustomerRoles role = new CustomerRoles();
        role.setDescription("Engineer");

        CustomerRoles role2 = new CustomerRoles();
        role2.setDescription("Jacker");

        listRoles.add(role);
        listRoles.add(role2);

        //Using the getters and setters
        customer.setRoles(listRoles);

        session.save(customer);

        transaction.commit();
    }

    public void usingPersist()
    {
        session = sf.openSession();
        transaction = session.beginTransaction();
        listRoles = new ArrayList<>();

        Customer customer2 = new Customer();
        customer2.setName("Mike");

        CustomerRoles role3 = new CustomerRoles();
        role3.setDescription("Driver");
        listRoles.add(role3);

        customer2.setRoles(listRoles);

        session.persist(customer2);

        transaction.commit();
    }

    public void usingRefresh()
    {
        session = sf.openSession();
        transaction = session.beginTransaction();

        Customer customer7 = new Customer();
        customer7.setName("Coconut");

        CustomerRoles role4 = new CustomerRoles();
        role4.setDescription("Maker");
        customer7.setRoles(Arrays.asList(role4));

        session.persist(customer7);
        transaction.commit();

        customer7.setName("Joke");
        role4.setDescription("WTF");

        session = sf.openSession();
        transaction = session.beginTransaction();
        System.out.println("Before refresh:");
        System.out.println(customer7.getName()+" : "+role4.getDescription());

        session.refresh(customer7);

        System.out.println("After refresh:");
        System.out.println(customer7.getName()+" : "+role4.getDescription());
        transaction.commit();

    }

    public void usingMerge()
    {

        session = sf.openSession(); //Do I have to open a session every time I have to commit a transaction?
        transaction = session.beginTransaction();
        Customer customer3 = new Customer();
        customer3.setName("Merge");

        CustomerRoles role4 = new CustomerRoles();
        role4.setDescription("MergeRole");
        customer3.setRoles(Arrays.asList(role4));
        session.persist(customer3);
        //in order to generate an Id the data first has to be persisted into the DB.
        Long roleId = role4.getRoleId();
        transaction.commit();

        session = sf.openSession();
        transaction = session.beginTransaction();
        CustomerRoles savedRole = session.find(CustomerRoles.class, roleId);
        Customer savedCustomerEntity = savedRole.getCustomer().get(0);
        savedCustomerEntity.setName("Merger");
        savedRole.setDescription("MergerRole");
        session.merge(savedCustomerEntity);
        transaction.commit();

        usingRemove(customer3);

    }

    public void usingRemove(Customer customer)
    {
        transaction = session.beginTransaction();

        Customer savedCustomerEntity = session.find(Customer.class,customer.getCustomerId());
        session.remove(savedCustomerEntity);

        transaction.commit();
    }

    public void detachingAndReattaching()
    {
        session = sf.openSession();
        transaction = session.beginTransaction();

        Customer customer5 = new Customer();
        customer5.setName("Boyle");

        CustomerRoles role6 = new CustomerRoles();
        role6.setDescription("Engineer");
        customer5.setRoles(Arrays.asList(role6));

        session.persist(customer5);

        //detaching the object from the Persistent state
        session.detach(customer5);
        customer5.setName("Detach");
        role6.setDescription("Joker");

        //reattaching the object in order for it to be back in Persistent state
        session.merge(customer5);
        transaction.commit();

    }

    public void outputCustomerAndCustomerRoles()
    {
        transaction = session.beginTransaction();

        Query q1 = session.createQuery("from Customer");
        List<Customer> customers = q1.list();

        System.out.println("These are the customer names:");
        for(Customer c: customers)
        {
            System.out.println(c.getName());
        }

        Query q2 = session.createQuery("from CustomerRoles");
        List<CustomerRoles> roles = q2.list();

        System.out.println("These are the roles:");
        for(CustomerRoles role: roles)
        {
            System.out.println(role.getDescription());
        }

        transaction.commit();
    }



}
