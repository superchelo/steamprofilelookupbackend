package xyz.steamProfileViewer.SteamLookUp.SteamGames;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface SteamGamesRepository extends JpaRepository<SteamGame,Long> {
    Optional<SteamGame> findById(long appId);
}
