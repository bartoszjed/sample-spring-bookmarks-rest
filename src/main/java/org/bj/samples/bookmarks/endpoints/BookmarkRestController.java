package org.bj.samples.bookmarks.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

/**
 * Created by Bartosz Jedrzejczak on 02/03/2017.
 */
@RestController
@RequestMapping("{userId}/bookmarks")
public class BookmarkRestController {

    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository accountRepository;

    @Autowired
    BookmarkRestController(BookmarkRepository bookmarkRepository,
                           AccountRepository accountRepository) {

        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(
            method = RequestMethod.GET)
    Collection<Bookmark> readBookmarks(@PathVariable String userId) {
        this.validateUser(userId);
        return this.bookmarkRepository.findByAccountUsername(userId);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{bookmarkId}")
    Bookmark readBookmark(@PathVariable String userId,
                          @PathVariable Long bookmarkId) {
        this.validateUser(userId);
        return this.bookmarkRepository.findOne(bookmarkId);

    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {
        this.validateUser(userId);
        return  this.accountRepository
                .findByUsername(userId)
                .map(account -> {
                            Bookmark result = bookmarkRepository.save(new Bookmark(account, input.uri, input.description));
                            URI location = ServletUriComponentsBuilder
                                    .fromCurrentRequest().path("/{id}")
                                    .buildAndExpand(result.getId()).toUri();
                            return ResponseEntity.created(location).build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    //alternative to above, differs in the way the Response Entity is defined
//    @RequestMapping(
//            method = RequestMethod.POST)
//    ResponseEntity<?> add2(@PathVariable String userId, @RequestBody Bookmark input) {
//        this.validateUser(userId);
//        return this.accountRepository.findByUsername(userId)
//                .map(
//                        account -> {
//                            Bookmark result = bookmarkRepository.save(new Bookmark(account, input.uri, input.description));
//
//                            HttpHeaders httpHeaders = new HttpHeaders();
//                            httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                                    .buildAndExpand(result.getId())
//                                    .toUri());
//                            return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
//                        }
//                ).get();
//    }


    private void validateUser(String userId) {
        this.accountRepository
                .findByUsername(userId)
                .orElseThrow(
                        () -> new UserNotFoundException(userId)
                );
    }

}
