package ru.lepescin.studentbookslibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.lepescin.studentbookslibrary.model.Author;

@Transactional(readOnly = true)
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Author a WHERE a.id=:id")
    int delete(@Param("id") int id);
}
