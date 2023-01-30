package account.repo;

import account.data.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, Long> {

    public AccountEntity findByEmailIgnoreCase(String email);

    public boolean existsRoleByEmailIgnoreCase(String email);

    @Query("DELETE FROM AccountEntity a WHERE LOWER(a.email) = LOWER(?1)")
    public void deleteByEmailIgnoreCase(String email);
}
