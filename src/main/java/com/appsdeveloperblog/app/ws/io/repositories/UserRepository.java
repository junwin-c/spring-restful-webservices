package com.appsdeveloperblog.app.ws.io.repositories;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);

    @Query(value = "select * from Users u where u.Email_Verification_Status = 'ture'",
            countQuery = "select count(*) from Users u where u.Email_Verification_Status = 'ture'",
            nativeQuery = true)
    Page<UserEntity> findAllUserWithConfirmedEmailAddress(Pageable pageableRequest);

    @Query(value = "select * from users u where u.first_name = ?1", nativeQuery = true)
    List<UserEntity> findUserByFirstName(String firstName);

    @Query(value = "select * from users u where u.last_name = :lastName", nativeQuery = true)
    List<UserEntity> findUserByLastName(@Param("lastName") String lastName);

    @Query(value = "SELECT * FROM users u WHERE u.first_name LIKE %:keyword% OR u.last_name LIKE %:keyword%", nativeQuery = true)
    List<UserEntity> findUserByKeyword(@Param("keyword") String keyword);

    @Query(value = "SELECT u.first_name, u.last_name FROM users u WHERE u.first_name LIKE %:keyword% OR u.last_name LIKE %:keyword%", nativeQuery = true)
    List<Object[]> findUserFirstNameAndLastNameByKeyword(@Param("keyword") String keyword);
}
