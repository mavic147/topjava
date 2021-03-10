package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    int deleteByIdAndUserId(int id, int userId);

    Meal findByIdAndUserId(int id, int userId);

    @Query(name = Meal.ALL_SORTED)
    List<Meal> finAllByUserId(@Param("userId") int userId);

    @Query(name = Meal.GET_BETWEEN)
    List<Meal> findAllBetween(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime,
                              @Param("userId") int userId);

}
