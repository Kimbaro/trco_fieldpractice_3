package com.kimbaro.app.kimbaro.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "data")
public class DB_Object_Data {
    @Id
    String id;

    @Indexed
    String groupcode;

    @Indexed
    List data;

    public DB_Object_Data() {
    }

    public DB_Object_Data(String groupcode, List data) {
        this.groupcode = groupcode;
        this.data = data;
    }
}
