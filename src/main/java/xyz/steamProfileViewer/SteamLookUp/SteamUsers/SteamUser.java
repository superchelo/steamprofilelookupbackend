package xyz.steamProfileViewer.SteamLookUp.SteamUsers;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "steam_user")
public class SteamUser {
    @Id
    private long steamId;
    @Column(name = "profile_name")
    private String profileName;
    @Column(name = "num_games")
    private int numGames;
    @Column(name = "last_accessed")
    private String lastAccessed;
    @Column(name = "avg_worth")
    private float avgWorth;
    @Column(name = "vanity_name")
    private String vanityName;
    @Column(name = "pfp_src")
    private String pfpSrc;

    public String getPfpSrc(){
        return pfpSrc;
    }
    public long getSteamId(){
        return this.steamId;
    }
    public String getProfileName(){
        return this.profileName;
    }
    public int getNumGames(){
        return this.numGames;
    }
    public String getLastAccessed(){
        return this.lastAccessed;
    }
    public float getAvgWorth(){
        return this.avgWorth;
    }
    public String getVanityName(){return this.vanityName;}
    public void setSteamId(long steamId) throws Exception{
        if (-1 == steamId || (long)Math.log10(steamId)+1 == 17) {
            this.steamId = steamId;
        } else{
            throw new Exception("Invalid SteamId");
        }
    }
    public void setProfileName(String profileName){
        this.profileName = profileName;
    }
    public void setNumGames(int numGames){
        this.numGames = numGames;
    }
    public void setLastAccessed(String lastAccessed){
        this.lastAccessed = lastAccessed;
    }
    public void setAvgWorth(float avgWorth){
        this.avgWorth = avgWorth;
    }
    public void setVanityName(String vanityName){this.vanityName = vanityName;}
    public void setPfpSrc(String pfpSrc){this.pfpSrc = pfpSrc;}
}
