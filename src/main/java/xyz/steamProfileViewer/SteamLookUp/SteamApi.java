package xyz.steamProfileViewer.SteamLookUp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

public class SteamApi {
    private static final String KEY = System.getenv("STEAMKEY");

    //Gets JSON of games owned by user from steam API
    public static JsonNode getGamesOwned(long userId) throws JsonProcessingException {
        String url = "https://api.steampowered.com/IPlayerService/GetOwnedGames/v1/?key=" +
                KEY +
                "&steamid=" +
                userId +
                "&include_appinfo=true&include_played_free_games=true&include_free_sub=true&include_extended_appinfo=false";

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        String gamesOwned = restTemplate.getForObject(url, String.class);

        return mapper.readTree(gamesOwned).get("response"); // response
    }
    //Gets JSON of player summary from steamAPI
    public static JsonNode getPlayerSummery(long userId) throws JsonProcessingException {
        String url = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/?key=" + KEY + "&steamids=" + userId;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        String playerSummary = restTemplate.getForObject(url, String.class);
        JsonNode response = mapper.readTree(playerSummary).get("response");

        return response.get("players"); //response
    }
    //Gets JSON of vanity name of user
    public static JsonNode getVanityName(String vanityName) throws JsonProcessingException {
        String url ="https://api.steampowered.com/ISteamUser/ResolveVanityURL/v1/?key=" +
                KEY + "&vanityurl=" + vanityName;

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        String vanityN = restTemplate.getForObject(url, String.class);

        return mapper.readTree(vanityN).get("response"); // response
    }
    public static JsonNode getAppInfo(long appId) throws JsonProcessingException {
        String url = "https://store.steampowered.com/api/appdetails?appids=" + appId;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        String appInfo = restTemplate.getForObject(url, String.class);

        return mapper.readTree(appInfo).get(Long.toString(appId)); // response
    }

}