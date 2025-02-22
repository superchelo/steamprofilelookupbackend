package xyz.steamProfileViewer.SteamLookUp.SteamUsers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.steamProfileViewer.SteamLookUp.OwnedGames.OwnsGame;
import xyz.steamProfileViewer.SteamLookUp.OwnedGames.OwnsGameService;
import xyz.steamProfileViewer.SteamLookUp.SteamApi;

import java.time.LocalDate;

import java.util.Optional;

@Service
public class SteamUserService {
    @Autowired
    private SteamUserRepository repo;
    @Autowired
    private OwnsGameService ownsGameService;

    public SteamUser findById(@PathVariable String Id) throws Exception {
        long steamId = -1;
        String vanityName = null;

        // checks if id is 17 digit long
        try{
            steamId = Long.parseLong(Id);
        } catch (Exception e) {
            // if not long checks if Id is string of vanity name
            Optional<SteamUser> oSteamUser = repo.findByVanityName(Id);
            if(!oSteamUser.isEmpty()){
                steamId = oSteamUser.get().getSteamId();
            }
            else {
                JsonNode node = SteamApi.getVanityName(Id);

                if (node.get("success").asInt() != 42) {
                    steamId = SteamApi.getVanityName(Id).get("steamid").asLong();
                    vanityName = Id;
                    System.out.println(steamId);
                }
            }
        }

        if (steamId == -1){
            SteamUser e = new SteamUser();
            e.setSteamId(-1);
            return e;
        }

        Optional<SteamUser> oSteamUser = repo.findById(steamId);
        // if user not in DB fetches from steamApi
        if (oSteamUser.isEmpty()){

            JsonNode playerSummary = SteamApi.getPlayerSummery(steamId);
            if (playerSummary.isEmpty()){
                SteamUser emptyUser = new SteamUser();
                emptyUser.setSteamId(-1);
                return emptyUser;

            }
            SteamUser newUser = new SteamUser();
            // if getting games from user is unsuccessful exit out
            if(!ownsGameService.addGames(steamId)){
                     newUser.setSteamId(-1);
                     return  newUser;
            }
            //No user in db but api found something
            newUser.setSteamId(steamId);
            String name = playerSummary.get(0).get("personaname").asText();
            String pfpUrl = playerSummary.get(0).get("avatarfull").asText();
            newUser.setPfpSrc(pfpUrl);
            newUser.setProfileName(name);
            newUser.setLastAccessed(LocalDate.now().toString());
            newUser.setNumGames(ownsGameService.getGames(steamId).size());
            newUser.setAvgWorth(ownsGameService.getPrices(steamId));
            newUser.setVanityName(vanityName);
            repo.save(newUser);
            return newUser;
        }

        SteamUser existingSteamUser = oSteamUser.get();
        System.out.println(existingSteamUser.getSteamId());
        //get user if in DB and info recently updated
        if (!isOutdated(existingSteamUser)) {
            return existingSteamUser;
        }
        // update user if not recently updated
        else {

            JsonNode gamesOwned = SteamApi.getGamesOwned(steamId);
            int game_count = gamesOwned.get("game_count").asInt();

            if (game_count == existingSteamUser.getNumGames()){
                existingSteamUser.setLastAccessed(LocalDate.now().toString());
                repo.save(existingSteamUser);
                return existingSteamUser;
            }
            else{
                //update games user has
                ownsGameService.addGames(steamId,gamesOwned);
                existingSteamUser.setNumGames(game_count);
                existingSteamUser.setAvgWorth(ownsGameService.getPrices(steamId));
                existingSteamUser.setLastAccessed(LocalDate.now().toString());
                repo.save(existingSteamUser);
                return existingSteamUser;
            }
        }
    }

    private boolean isOutdated(SteamUser user){
        LocalDate userDate = LocalDate.parse(user.getLastAccessed());
        LocalDate currDate = LocalDate.now();
        LocalDate compDate = currDate.minusDays(14);
        return userDate.isBefore(compDate);

    }

}
