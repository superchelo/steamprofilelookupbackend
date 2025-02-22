package xyz.steamProfileViewer.SteamLookUp.OwnedGames;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ownsGame")
public class OwnsGameController {

    @Autowired
    OwnsGameService service;
    @GetMapping("/getGames/{steamId}")
    public ResponseEntity<List<OwnsGame>> getGame(@PathVariable long steamId) throws Exception{
        List<OwnsGame> gameList = service.getGames(steamId);
        if (gameList.isEmpty()){
            return new ResponseEntity<>(gameList,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(gameList,HttpStatus.OK);
    }
}
