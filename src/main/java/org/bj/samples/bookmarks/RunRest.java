package org.bj.samples.bookmarks;

import org.bj.samples.bookmarks.endpoints.Account;
import org.bj.samples.bookmarks.endpoints.Bookmark;
import org.bj.samples.bookmarks.endpoints.AccountRepository;
import org.bj.samples.bookmarks.endpoints.BookmarkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * https://spring.io/guides/tutorials/bookmarks/#_getting_started
 */

@SpringBootApplication
public class RunRest {

    public static void main(String[] args) {
        SpringApplication.run(RunRest.class, args);
    }

    @Bean
    CommandLineRunner init (AccountRepository accountRepository,
                            BookmarkRepository bookmarkRepository){
        return (evt) -> Arrays.asList(
                "bj,user1,user2,user3".split(","))
                .forEach(
                        a -> {
                            Account account = accountRepository.save(new Account(a, "password"));
                            bookmarkRepository.save(new Bookmark(account,
                                    "http://bookmark.com/1/"+a, "A description 1 "));
                            bookmarkRepository.save(new Bookmark(account,
                                    "http://bookmark.com/2/"+a, "A description 2 "));

                        }
                );
    }
}
