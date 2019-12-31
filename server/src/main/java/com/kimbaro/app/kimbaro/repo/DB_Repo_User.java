package com.kimbaro.app.kimbaro.repo;

import com.kimbaro.app.kimbaro.domain.DB_Object_User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Qualifier("user")
public interface DB_Repo_User extends ReactiveCrudRepository<DB_Object_User, String> {

}
