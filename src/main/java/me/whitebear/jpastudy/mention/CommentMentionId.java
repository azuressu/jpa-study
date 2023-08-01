package me.whitebear.jpastudy.mention;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.whitebear.jpastudy.comment.Comment;
import me.whitebear.jpastudy.userChannel.UserChannelId;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CommentMentionId implements Serializable {
    @Column(name = "user_id")
    private Long userId;   // UserChannel의 user 필드명과 동일해야 함

    @Column(name = "comment_id")
    private Long commentId; // UserChannel의 channel 필드명과 동일해야 함

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o==null || getClass() != o.getClass()) return false;
        CommentMentionId mentionId = (CommentMentionId) o;
        return Objects.equals(getUserId(), mentionId.getUserId()) && Objects.equals(getCommentId(), mentionId.getCommentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getCommentId());
    }


}
