package com.kimbaro.app.kimbaro.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Data
@Document(collection = "room")
public class DB_Object_Room { //채널 생성

    @Id
    String id;
    @Indexed
    String groupcode;
    @Indexed
    int type;

    public DB_Object_Room() {

    }

    public DB_Object_Room(String groupcode, int type) {
        this.groupcode = groupcode;
        this.type = type;
    }

}
