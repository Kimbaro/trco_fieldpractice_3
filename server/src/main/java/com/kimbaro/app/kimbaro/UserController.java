package com.kimbaro.app.kimbaro;

import com.kimbaro.app.kimbaro.domain.DB_Object_Data;
import com.kimbaro.app.kimbaro.domain.DB_Object_Room;
import com.kimbaro.app.kimbaro.domain.DB_Object_User;
import com.kimbaro.app.kimbaro.repo.DB_Repo_Data;
import com.kimbaro.app.kimbaro.repo.DB_Repo_Room;
import com.kimbaro.app.kimbaro.repo.DB_Repo_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.*;

@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    @Qualifier("user")
    DB_Repo_User db_repo_user;

    @Autowired
    @Qualifier("room")
    DB_Repo_Room db_repo_room;

    @Autowired
    @Qualifier("data")
    DB_Repo_Data db_repo_data;

    String userId = "";

    @GetMapping("/user_create")
    public Mono<DB_Object_User> user_create(@RequestParam HashMap<String, String> data) {
        System.out.println("response data : " + data.toString());
        //회원정보 저장 후 모니터링사용자채널 생성,
        //사용자 채널은 Array 타입으로 사용자 고유 아이디 저장.


        return Mono.just(new DB_Object_User(data)).flatMap(db_repo_user::save);
//        Mono.just(new DB_Object_User(data)).flatMap(db_repo_user::save).subscribe(
//                db_object_user -> {
//                    String groupcode = db_object_user.getId().substring(db_object_user.getId().length() - 6, db_object_user.getId().length());
//                    Mono.just(new DB_Object_Room(groupcode, new String[]{db_object_user.getId()}))
//                            .flatMap(db_repo_room::save);
//                }
//        );
    }

    //개인그룹 생성하기
    //개인그룹은 클라이언트단에서 groupcode를 생성한다.
    @GetMapping("/user_join")
    public Mono<DB_Object_Room> user_join(@RequestParam HashMap<String, String> data) {
        //모니터링채널 생성
        Mono.just(new DB_Object_Data(data.get("groupcode"), null)).flatMap(db_repo_data::save).subscribe();
        return Mono.just(new DB_Object_Room(data.get("groupcode"), Integer.valueOf(data.get("type")))).flatMap(db_repo_room::save);
    }


    //단체그룹 생성하기
    @GetMapping("/user_group_create")
    public Mono<DB_Object_Room> user_group_create() {
        return Mono.just(new DB_Object_Room()).flatMap(db_repo_room::save);
    }

    //단체그룹의 groupcode를 생성 및 update, 이후 모니터링채널 생성
    @GetMapping("/user_group_set")
    public Mono<DB_Object_Room> user_group_set(@RequestParam(value = "id") String id) {
        //채널명 저장
        return db_repo_room.findById(id).flatMap(db_object_room -> {
            int leng = db_object_room.getId().length();
            db_object_room.setType(2);
            db_object_room.setGroupcode(db_object_room.getId().substring(leng - 6, leng));

            //모니터링채널 생성
            Mono.just(new DB_Object_Data(db_object_room.getGroupcode(), null)).flatMap(db_repo_data::save).subscribe();

            return db_repo_room.save(db_object_room);
        });
    }

    //    http://192.168.0.3:8080/input_data?groupId=23401d&userId=5de6f06dfdc50f3c7864774f&rate=110
    //사용자 측정 데이터 처리
    @GetMapping("/input_data")
    public Mono<DB_Object_Data> input_data(@RequestParam HashMap<String, String> data) {
        return db_repo_data.findByGroupcode(data.get("groupId")).flatMap(db_object_data -> {

            List list = db_object_data.getData();
            if (list == null) { //list 자체가 null 인경우
                List<String> userDataList = new ArrayList<String>();
                userDataList.add(data.get("userId"));
                userDataList.add(data.get("rate"));
                userDataList.add(data.get("timer"));
                userDataList.add(data.get("min_rate"));
                userDataList.add(data.get("max_rate"));
                userDataList.add(data.get("min_strength"));
                userDataList.add(data.get("max_strength"));
                userDataList.add(data.get("name"));

                list = new ArrayList();
                list.add(userDataList);
                db_object_data.setData(list);
            } else if (!list.isEmpty()) {
                boolean check = true; //채널에 이미 존재하는지 여부를 체크 , 존재한 경우 false
                for (int x = 0; x < list.size(); x++) {
                    List list2 = (List) list.get(x);
                    if (list2.get(0).equals(data.get("userId"))) { //채널에 저장된 userId를 가져옴, 요청된 사용자 id 비교, 해당하는 유저 검색
                        check = false;
                        System.out.println(data.toString());
                        System.out.println("접근 ===============");
                        list2.set(1, data.get("rate")); //해당하는 유저가 존재하여 심박수 정보 업데이트
                        list2.set(2, data.get("timer"));
                        list2.set(3, data.get("min_rate"));
                        list2.set(4, data.get("max_rate"));
                        list2.set(5, data.get("min_strength"));
                        list2.set(6, data.get("max_strength"));

                        list.set(x, list2);
                        db_object_data.setData(list);
                        break;
                    }
                }
                if (check) {
                    List<String> userDataList = new ArrayList<String>();
                    userDataList.add(data.get("userId"));
                    userDataList.add(data.get("rate"));
                    userDataList.add(data.get("timer"));
                    userDataList.add(data.get("min_rate"));
                    userDataList.add(data.get("max_rate"));
                    userDataList.add(data.get("min_strength"));
                    userDataList.add(data.get("max_strength"));
                    userDataList.add(data.get("name"));

                    list.add(userDataList);
                    db_object_data.setData(list);
                }
            }

            return db_repo_data.save(db_object_data);
        });
    }

    @GetMapping("/find_data")
    public Mono<DB_Object_Data> find_data(@RequestParam("groupId") String groupId) {
    System.out.println("find_data 진입");
        return db_repo_data.findByGroupcode(groupId);
//        return db_repo_data.findByGroupcode(groupId).subscribe(db_object_data -> {
//            List list = db_object_data.getData();
//            List list2 = (List) list.get(0);
//            System.out.println(list2.get(0));
//            System.out.println(list2.get(1));
//        });
    }
}
