package xyz.steamProfileViewer.SteamLookUp.SteamUsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
public class SteamUserController {

    @Autowired
    SteamUserService service;

    @GetMapping("/getUser/{steamId}")
    public ResponseEntity<SteamUser> getUser(@PathVariable String steamId) throws Exception{
        SteamUser steamUser = service.findById(steamId);
        if (steamUser.getSteamId() == -1){
            return new ResponseEntity<>(steamUser, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(steamUser, HttpStatus.OK);
    }

}
