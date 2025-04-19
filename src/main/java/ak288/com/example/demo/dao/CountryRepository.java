package ak288.com.example.demo.dao;

import ak288.com.example.demo.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource
public interface CountryRepository extends JpaRepository<Country, Long> {
}
