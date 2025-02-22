package xyz.steamProfileViewer.SteamLookUp.OwnedGames;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "owns_game")
public class OwnsGame {
    @EmbeddedId
    private OwnsGameId userIdappId;

    public void setUserIdappId(long userId, long appId) {
        if (userIdappId == null){
            userIdappId = new OwnsGameId();
        }
        userIdappId.setUserId(userId);
        userIdappId.setAppId((appId));
    }
    public long getAppId(){
        return userIdappId.getAppId();
    }
    public long getUserId(){
        return userIdappId.getUserId();
    }
}

