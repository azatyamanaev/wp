package ru.itis.workproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.workproject.dto.InformationDto;
import ru.itis.workproject.models.User;

public interface UsersJpaRepository extends JpaRepository<User, Long> {
    // JPQL
    @Query("select new ru.itis.workproject.dto.InformationDto(user.login, (sum(document.size) / 1024 / 1024) ) from User user left join user.documents " +
            "as document where user.id = :userId group by user.id")
    InformationDto getInformationByUser(@Param("userId") Long userId);
}
