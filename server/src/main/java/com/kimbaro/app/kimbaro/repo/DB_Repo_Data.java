package com.kimbaro.app.kimbaro.repo;

import com.kimbaro.app.kimbaro.domain.DB_Object_Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

@Qualifier("data")
public interface DB_Repo_Data extends ReactiveCrudRepository<DB_Object_Data, String> {
    Mono<DB_Object_Data> findByGroupcode(String groupcode);
}
