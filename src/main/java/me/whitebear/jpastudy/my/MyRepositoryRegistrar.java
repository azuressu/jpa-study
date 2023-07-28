package me.whitebear.jpastudy.my;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class MyRepositoryRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        // 주입할 빈에 대해 프로그래밍 하는 부분
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        // 이 클래스에 대해서 Bean으로 등록하겠다 명시하는 부분
        beanDefinition.setBeanClass(MyRepository.class);
        // dataTable에다가 뒤의 Map.of 값을 넣어줄 수 있음
        beanDefinition.getPropertyValues().add("dataTable", Map.of(1L, "data"));
        // 여기까지 !

        // myRepository라는 Bean 이름으로 register 하게 됨
        registry.registerBeanDefinition("myRepository", beanDefinition);

    }
}
