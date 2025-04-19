package ak288.com.example.demo.bootstrap;

import ak288.com.example.demo.dao.CustomerRepository;
import ak288.com.example.demo.dao.DivisionRepository;
import ak288.com.example.demo.entities.Customer;
import ak288.com.example.demo.entities.Division;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class BootstrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;
    private static final Logger logger = LoggerFactory.getLogger(BootstrapData.class);


    public BootstrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (customerRepository.count() <= 1) {
            logger.info("Adding sample customers...");

            Division andyDiv = divisionRepository.findById(32L).orElseThrow(
                    () -> new IllegalStateException("Division ID 6 - CT - is not found"));

            Division gatDiv = divisionRepository.findById(30L).orElseThrow(
                    () -> new IllegalStateException("Division ID 30 - NM - not found"));

            Division tonyDiv = divisionRepository.findById(29L).orElseThrow(
                    () -> new IllegalStateException("Division ID 29 - NJ - is not found"));

            Division spongeDiv = divisionRepository.findById(4L).orElseThrow(
                    () -> new IllegalStateException("Division ID 4 - CA - not found"));

            Division goatDiv = divisionRepository.findById(12L).orElseThrow(
                    () -> new IllegalStateException("Division ID 12 - IL - is not found"));

            Customer andy = new Customer();
            andy.setFirstName("Andy");
            andy.setLastName("Kohli");
            andy.setAddress("90 Leela Way");
            andy.setPostal_code("06084");
            andy.setPhone("(123)456-7890");
            andy.setDivision_id(andyDiv);
            customerRepository.save(andy);

            Customer gat = new Customer();
            gat.setFirstName("El");
            gat.setLastName("Gat");
            gat.setAddress("24 Los Pollos Avenue");
            gat.setPostal_code("87101");
            gat.setPhone("(124)356-7890");
            gat.setDivision_id(gatDiv);
            customerRepository.save(gat);

            Customer tony = new Customer();
            tony.setFirstName("Tony");
            tony.setLastName("Soprano");
            tony.setAddress("23 Bing Way");
            tony.setPostal_code("08400");
            tony.setPhone("(123)465-7890");
            tony.setDivision_id(tonyDiv);
            customerRepository.save(tony);

            Customer sponge = new Customer();
            sponge.setFirstName("Spongebob");
            sponge.setLastName("Squarepants");
            sponge.setAddress("1 Pineapple Street");
            sponge.setPostal_code("90263");
            sponge.setPhone("(111)356-7890");
            sponge.setDivision_id(spongeDiv);
            customerRepository.save(sponge);

            Customer goat = new Customer();
            goat.setFirstName("Chloe");
            goat.setLastName("Goat");
            goat.setAddress("39 Chiraq Blvd");
            goat.setPostal_code("60007");
            goat.setPhone("(123)666-7890");
            goat.setDivision_id(goatDiv);
            customerRepository.save(goat);

            logger.info("Added {} sample customers.", customerRepository.count() - 1);
        } else {
            logger.info("Customers already exist. Skipping insertion.");
        }
    }
}
