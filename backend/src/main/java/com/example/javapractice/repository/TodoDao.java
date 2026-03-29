package com.example.javapractice.repository;

import com.example.javapractice.entity.Todo;
import java.util.List;
import java.util.Optional;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface TodoDao {

    @Select
    List<Todo> selectAll();

    @Select
    Optional<Todo> selectById(Long id);

    @Insert
    int insert(Todo todo);

    @Update
    int update(Todo todo);

    @Delete
    int delete(Todo todo);
}
