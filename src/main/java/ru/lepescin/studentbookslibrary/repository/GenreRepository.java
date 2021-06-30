package ru.lepescin.studentbookslibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.lepescin.studentbookslibrary.model.Genre;

@Transactional(readOnly = true)
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Genre g WHERE g.id=:id")
    int delete(@Param("id") int id);
}
