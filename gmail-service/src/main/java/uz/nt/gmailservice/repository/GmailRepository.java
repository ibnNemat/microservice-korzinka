package uz.nt.gmailservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.nt.gmailservice.entity.GmailRedis;

@Repository
public interface GmailRepository extends CrudRepository<GmailRedis,Long> {
}
