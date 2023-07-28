//package me.whitebear.jpastudy.my;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//
///* Import를 따로 해주는 이유?
//*  SpringBootTest는 Spring에서 주입된 Bean들에 대해서 하는 테스트인데,
//*  MyRepositoryRegistrar은 Component로 등록되어있거나 하지는 않았기 때문에 import 해줌 !*/
//@Import(MyRepositoryRegistrar.class) // 빈 주입하기
//@SpringBootTest
//public class MyRepositoryRegistrarTest {
//
//    @Autowired
//    MyRepository myRepository;
//
//    @Test
//    void myRepositoryTest() {
//        // given
//        var newData = "NEW DATA";
//        var savedId = myRepository.save(newData);
//
//        // when
//        var savedData = myRepository.find(savedId);
//
//        // then
//        System.out.println(savedData);
//    }
//
//}
