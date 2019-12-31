package com.kimbaro.app.kimbaro.repo;

import com.kimbaro.app.kimbaro.domain.DB_Object_Room;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@Qualifier("room")
public interface DB_Repo_Room extends ReactiveCrudRepository<DB_Object_Room, String> {
}
