package com.challengeone.demo.repository;

import com.challengeone.demo.model.db.UsersEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UsersRepository extends JpaRepository<UsersEntity, Long>, JpaSpecificationExecutor<UsersEntity> {

    List<UsersEntity> findAllByLastname(String lastname, Pageable pageable);
    List<UsersEntity> findAllByAge(int age, Pageable pageable);

}
