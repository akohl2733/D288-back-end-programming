package ak288.com.example.demo.dao;

import ak288.com.example.demo.entities.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(path = "divisions")
public interface DivisionRepository extends JpaRepository<Division, Long> {
}