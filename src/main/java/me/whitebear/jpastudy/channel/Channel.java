package me.whitebear.jpastudy.channel;

import jakarta.persistence.*;

@Entity
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    /* @Enumerated Enum 매핑용도로 쓰이며 실무에서는 @Enumerated(EnumType.STRING)으로 사용 권장
    * Default 타입인 ORDINAL은 0, 1, 2 값으로 들어가기 때문에 추후 순서가 바뀔 가능성이 높음*/
    @Enumerated(EnumType.STRING)
    private Type type;

    private enum Type {
        PUBLIC, PRIVATE; // 각각 0, 1로 저장함 (따라서 STRING으로 저장하도록 해야 함)
    }
}
