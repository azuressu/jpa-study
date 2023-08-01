package me.whitebear.jpastudy.mention;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.whitebear.jpastudy.userChannel.UserChannelId;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ThreadMentionId implements Serializable {

    @Column(name = "user_id")
    private Long userId;   // UserChannel의 user 필드명과 동일해야 함

    @Column(name = "thread_id")
    private Long threadId; // UserChannel의 channel 필드명과 동일해야 함

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o==null || getClass() != o.getClass()) return false;
        ThreadMentionId mentionId = (ThreadMentionId) o;
        return Objects.equals(getUserId(), mentionId.getUserId()) && Objects.equals(getThreadId(), mentionId.getThreadId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getThreadId());
    }


}
