package com.hyva.bsfms.bs.bsrespositories;

import com.hyva.bsfms.bs.bsentities.User;
import org.springframework.data.repository.CrudRepository;


public interface BsUserRepository extends CrudRepository<User, Long> {

    User findByUserNameAndAndPasswordUser(String  userName, String Password);

    User findByEmail(String Email);

    User findByUserName(String name);
}
