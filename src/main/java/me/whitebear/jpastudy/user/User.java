package me.whitebear.jpastudy.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// lombok
@Getter
// AccessLevel.PROTECTED: 실제 object mapping을 제외하고 외부에서 빈 생성자를 생성하는 것을 막기 위해서
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString

// jpa
@Entity
@Table(name = "users")
public class User {
    /**
     * 컬럼 - 연관관계 컬럼을 제외한 컬럼 정의
     */

    /* Clss에 @Entity가 붙어 있으면 자동으로 필드들에 @Column이 붙음
    * @Column은 String, Date, Boolean과 같은 타입들에 공통적으로 사이즈를 제한할 용도로 쓰임 */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 25)
    private String username;

    @Column(length = 25)
    private String password;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column=@Column(name = "home_street"))
    })
    private Address address;

    /**
     * 생성자 - 약속된 형태로만 생성 가능하게 함
     */

    /**
     * 연관관계 - Forign Key 갓ㅂ을 따로 컬럼으로 정의하지 않고 연관 관계로 정의
     */

    /**
     * 연관관계 편의 메소드 - 반대쪽에서는 연관관계 편의 메소드가 없도록 주의
     */

    /**
     * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의 (단일 책임을 갖도록 주의하기)
     */

}
