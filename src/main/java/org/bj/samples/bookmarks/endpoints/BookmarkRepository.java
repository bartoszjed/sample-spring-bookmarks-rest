package org.bj.samples.bookmarks.endpoints;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by Bartosz Jedrzejczak on 02/03/2017.
 */
public interface BookmarkRepository extends JpaRepository<Bookmark, Long>{
    Collection<Bookmark> findByAccountUsername(String username);

}
