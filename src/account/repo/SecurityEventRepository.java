package account.repo;

import account.data.entities.SecurityEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityEventRepository extends JpaRepository<SecurityEventEntity, Integer> {
}
