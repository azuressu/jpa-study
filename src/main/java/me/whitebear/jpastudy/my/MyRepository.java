package me.whitebear.jpastudy.my;

import lombok.Setter;

import java.util.HashMap;

@Setter
public class MyRepository {

    private HashMap<Long, String> dataTable; // DB 테이블을 의미

    public String find(Long id) {
        // find: DB 테이블에서 id 값으로 데이터를 가져오는 것 (key 가 id, String의 value)
        return dataTable.getOrDefault(id, "");
    }

    public Long save(String data) {
        // 새로운 id는 DB 테이블의 길이일 것 (id 값은 0부터 시작하니까 !)
        var newId = Long.valueOf(dataTable.size());
        // 새로운 id값과 데이터를 dataTable에 put 해줌
        this.dataTable.put(newId, data);
        return newId;
    }
}
