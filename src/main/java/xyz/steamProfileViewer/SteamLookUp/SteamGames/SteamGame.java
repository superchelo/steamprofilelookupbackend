package xyz.steamProfileViewer.SteamLookUp.SteamGames;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "steam_games")
public class SteamGame {
    @Id
    private long appId;
    @Column(name = "app_name")
    private String appName;
    @Column(name = "price")
    private double price;

    public long getAppId(){
        return appId;
    }
    public String getAppName(){
        return appName;
    }
    public double getPrice(){
        return price;
    }

    public void setAppId(long id) throws Exception {
        this.appId = id;

    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
    public void setPrice(double price) {
        this.price = price;
    }

}
