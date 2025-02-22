package xyz.steamProfileViewer.SteamLookUp.SteamUsers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface SteamUserRepository extends JpaRepository<SteamUser, Long> {
    Optional<SteamUser> findById(long steamId);
    Optional<SteamUser> findByVanityName(String vanityName);
}
