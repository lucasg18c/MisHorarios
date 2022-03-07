package com.slavik.misHorariosApi.data;

import com.slavik.misHorariosApi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.passwd = :passwd")
    User findUser(@Param("email") String email,@Param("passwd") String passwd);
}
