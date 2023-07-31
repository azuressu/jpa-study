package me.whitebear.jpastudy.channel;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.whitebear.jpastudy.thread.Thread;
import me.whitebear.jpastudy.user.User;
import me.whitebear.jpastudy.userChannel.UserChannel;

import java.util.LinkedHashSet;
import java.util.Set;

// lombok
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

// jpa
@Entity
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 컬럼 - 연관관계 컬럼을 제외한 컬럼을 정의합니다.
     */
    private String name;

    /* @Enumerated Enum 매핑용도로 쓰이며 실무에서는 @Enumerated(EnumType.STRING)으로 사용 권장
    * Default 타입인 ORDINAL은 0, 1, 2 값으로 들어가기 때문에 추후 순서가 바뀔 가능성이 높음*/
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        PUBLIC, PRIVATE; // 각각 0, 1로 저장함 (따라서 STRING으로 저장하도록 해야 함)
    }

    /**
     * 생성자 - 약속된 형태로만 생성가능하도록 합니다.
     */
    @Builder
    public Channel(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    /**
     * 연관관계 - Foreign Key 값을 따로 컬럼으로 정의하지 않고 연관 관계로 정의합니다.
     */

    /* cascade = CascadeType.ALL, orphanRemoval = true
    * 자식 엔티티의 라이프 사이클이 부모 엔티티와 동일해짐
    * 직접 자식 엔티티의 생명주기를 관리할 수 있게 되므로 자식 엔티티의 Repository 조차 필요하지 않게 됨
    * => 매핑 테이블에서 많이 쓰임 */
    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true) // CascadeType.ALL: 저장을 위해서 !
    private Set<Thread> threads = new LinkedHashSet<>();

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true) // userChannel에 전이됨
    private Set<UserChannel> userChannels = new LinkedHashSet<>(); // 중복 제거 및 불러오는 시점의 순서 보장

    /**
     * 연관관계 편의 메소드 - 반대쪽에는 연관관계 편의 메소드가 없도록 주의합니다.
     */
     public void addThread(Thread thread) {
         this.threads.add(thread);
     }

     public UserChannel joinUser(User user) {
         var userChannel = UserChannel.builder().user(user).channel(this).build();
         this.userChannels.add(userChannel);
         user.getUserChannels().add(userChannel);
         return userChannel;
     }

    /**
     * 서비스 메소드 - 외부에서 엔티티를 수정할 메소드를 정의합니다. (단일 책임을 가지도록 주의합니다.)
     */
}
