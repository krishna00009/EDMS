package com.hyva.bsfms.bs.bsrespositories;

import com.hyva.bsfms.bs.bsentities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface BsUserRepository extends JpaRepository<User, Long> {

    User findByUserNameAndPasswordUser(String  userName, String Password);

    User findByEmail(String Email);

    User findByUserName(String name);
}
