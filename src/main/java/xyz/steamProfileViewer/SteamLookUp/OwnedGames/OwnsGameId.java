package xyz.steamProfileViewer.SteamLookUp.OwnedGames;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class OwnsGameId implements Serializable {
    @Column(name = "user_id")
    private long userId;
    @Column(name = "app_id")
    private long appId;


    public void setUserId(long userId) {
        this.userId = userId;
    }
    public void setAppId(long appId) {
        this.appId = appId;
    }
    public long getUserId() {
        return userId;
    }
    public long getAppId() {
        return appId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OwnsGameId ownsGameId)) return false;
        return userId == ownsGameId.userId && appId == ownsGameId.appId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, appId);
    }
}

