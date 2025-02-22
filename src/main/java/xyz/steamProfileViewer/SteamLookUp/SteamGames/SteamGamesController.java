package xyz.steamProfileViewer.SteamLookUp.SteamGames;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/games")
public class SteamGamesController {

    @Autowired
    SteamGamesService service;
    @GetMapping("/getGame/{appId}")
    public ResponseEntity<SteamGame> getGame(@PathVariable long appId) throws Exception{
        SteamGame steamGame = service.findById(appId);
        if (steamGame.getAppId() == -1){
            return new ResponseEntity<>(steamGame, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(steamGame, HttpStatus.OK);
    }
}
