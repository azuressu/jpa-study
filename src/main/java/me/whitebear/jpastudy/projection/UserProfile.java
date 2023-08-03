package me.whitebear.jpastudy.projection;


import org.springframework.beans.factory.annotation.Value;

public interface UserProfile {

    /* get() 메소드를 사용하는 것
    *  정의한 필드만 조회하기 때문에 Closed 프로젝션 이라고 함
    *  쿼리를 줄이므로 최적화 할 수 있고, 메소드 형태이므로 JAVA8의 메소드를 사용해 연산이 가능하다.
    *  select username, password from user; */

    String getUsername();
    String getPassword();

    @Value("#{target.password != null}")
    boolean hasPassword();

    default String getUserInfo() {
        return getUsername() + " " + (hasPassword() ? getPassword() : "");
    }

}
