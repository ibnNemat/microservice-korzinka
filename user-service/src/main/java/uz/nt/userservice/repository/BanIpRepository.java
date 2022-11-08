package uz.nt.userservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import uz.nt.userservice.entity.BanIp;
@Component
public interface BanIpRepository extends CrudRepository<BanIp,String> {
}
