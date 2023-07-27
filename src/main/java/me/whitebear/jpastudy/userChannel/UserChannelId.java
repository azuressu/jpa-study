package me.whitebear.jpastudy.userChannel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChannelId implements Serializable {
    private Long user;   // UserChannel의 user 필드명과 동일해야 함
    private Long channel; // UserChannel의 channel 필드명과 동일해야 함

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChannelId userChannelId = (UserChannelId) o;

        return Objects.equals(getUser(), userChannelId.getUser()) && Objects.equals(getChannel(), userChannelId.getChannel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getChannel());
    }
}
