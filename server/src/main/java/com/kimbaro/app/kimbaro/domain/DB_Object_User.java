package com.kimbaro.app.kimbaro.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Data
@Document(collection = "user")
public class DB_Object_User { //사용자 정보 생성
    @Id
    String id;

    @Indexed
    String name;
    @Indexed
    String age;
    @Indexed
    String stablerate;
    @Indexed
    String valueA;
    @Indexed
    String valueB;

    public DB_Object_User() {
    }

    public DB_Object_User(HashMap<String, String> data) {
        this.name = data.get("name");
        this.age = data.get("age");
        this.stablerate = data.get("stablerate");
        this.valueA = data.get("valueA");
        this.valueB = data.get("valueB");
    }

}
