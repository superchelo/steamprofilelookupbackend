package xyz.steamProfileViewer.SteamLookUp.OwnedGames;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface OwnsGameRepository extends JpaRepository<OwnsGame,OwnsGameId>{
    @Query("SELECT o FROM OwnsGame o WHERE o.id.userId = :userId")
    Optional<List<OwnsGame>> findByUserId(long userId);

}
