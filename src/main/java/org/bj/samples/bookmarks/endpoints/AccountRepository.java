package org.bj.samples.bookmarks.endpoints;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Bartosz Jedrzejczak on 02/03/2017.
 */

public interface AccountRepository extends JpaRepository<Account, Long>{

    Optional<Account> findByUsername(String username);

}
