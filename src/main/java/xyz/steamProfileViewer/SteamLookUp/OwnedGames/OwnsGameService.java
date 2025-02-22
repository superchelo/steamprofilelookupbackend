package xyz.steamProfileViewer.SteamLookUp.OwnedGames;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.steamProfileViewer.SteamLookUp.SteamApi;
import xyz.steamProfileViewer.SteamLookUp.SteamGames.SteamGamesService;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class OwnsGameService {
    @Autowired
    private OwnsGameRepository repository;
    @Autowired
    private SteamGamesService steamGamesService;

    public List<OwnsGame> getGames(long userId) throws Exception{
        return repository.findByUserId(userId).get();
    }
    public Optional<OwnsGame> gameExists(long userId, long appId){
        OwnsGameId ownsGameId = new OwnsGameId();
        ownsGameId.setUserId(userId);
        ownsGameId.setAppId(appId);

        return  repository.findById(ownsGameId);
    }
    public void addGame(long userId, long appId) throws Exception{
        OwnsGame game = new OwnsGame();
        game.setUserIdappId(userId, appId);
        repository.save(game);
    }
    public boolean addGames(long userId) throws Exception {
        JsonNode games = SteamApi.getGamesOwned(userId);
        if (games.isEmpty()){
            return false;
        }
        Iterator<JsonNode> appIds = games.get("games").iterator();
        while(appIds.hasNext()){
            long appId = appIds.next().get("appid").asLong();
            if(gameExists(userId,appId).isEmpty()) {
                steamGamesService.findById(appId);
                addGame(userId, appId);
            }
        }
        return true;
    }
    public boolean addGames(long userId, JsonNode game) throws Exception {

        if (game.isEmpty()){
            return false;
        }
        Iterator<JsonNode> appIds = game.get("games").iterator();
        while(appIds.hasNext()){
            long appId = appIds.next().get("appid").asLong();
            if(gameExists(userId,appId).isEmpty()) {
                steamGamesService.findById(appId);
                addGame(userId, appId);
            }
        }
        return true;
    }
    public float getPrices(long steamId) throws Exception {
        List<OwnsGame> games = getGames(steamId);
        float sum = 0;

        for(int i = 0; i < games.size(); i++){
            sum += (float) steamGamesService.findById(games.get(i).getAppId()).getPrice();
        }
        return Math.round(sum*100.0)/(float)100.0;
    }
}
