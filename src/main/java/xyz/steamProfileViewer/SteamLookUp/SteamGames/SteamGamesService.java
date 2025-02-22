package xyz.steamProfileViewer.SteamLookUp.SteamGames;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.money.CurrencyUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.steamProfileViewer.SteamLookUp.CurrencyConverter;
import xyz.steamProfileViewer.SteamLookUp.SteamApi;
import xyz.steamProfileViewer.SteamLookUp.SteamUsers.SteamUser;

import java.util.Currency;
import java.util.Optional;

@Service
public class SteamGamesService {
    @Autowired
    private SteamGamesRepository repo;

    public SteamGame findById(long appId) throws Exception {
        Optional<SteamGame> steamGame = repo.findById(appId);

        if (appId == -1){
            SteamGame game = new SteamGame();
            game.setAppId(-1);
            return game;
        }
        else if (steamGame.isEmpty()){

            JsonNode appSummary = SteamApi.getAppInfo(appId);
            if (!appSummary.get("success").asBoolean()){
                SteamGame emptyGame = new SteamGame();
                emptyGame.setAppId(appId);
                emptyGame.setAppName(null);
                emptyGame.setPrice(0);
                return emptyGame;
            }
            SteamGame newGame = new SteamGame();

            String appName = appSummary.get("data").get("name").asText();
            double price = 0;
            if(appSummary.get("data").get("is_free").asBoolean() || !appSummary.get("data").has("price_overview") ){
                price = 0;
            }else {
                CurrencyConverter currencyConverter = new CurrencyConverter();
                double p = appSummary.get("data").get("price_overview").get("final").asInt() / 100.0;
                String currency = appSummary.get("data").get("price_overview").get("currency").asText();
                price = currencyConverter.convertToUSD(currency, p);
                price = Math.round(price*100.0)/100.0;
            }

            newGame.setAppId(appId);
            newGame.setPrice(price);
            newGame.setAppName(appName);
            repo.save(newGame);
            return newGame;

        }

        return steamGame.get();
    }
}
