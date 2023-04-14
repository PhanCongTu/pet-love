package com.stc.petlove.repositories;

import com.stc.petlove.entities.TaiKhoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/15/23
 * Time      : 8:49 AM
 * Filename  : UserRepository
 */
public interface UserRepository extends MongoRepository<TaiKhoan, String> {

    Optional<TaiKhoan> findByEmail(String email);

    @Query(value = "{'email': ?0}")
    Optional<TaiKhoan> getUser(String email);

    @Query(value = "{'email': ?0}", exists = true)
    boolean checkEmail(String email);

    boolean existsByEmail(String email);

    Optional<TaiKhoan> findById(String id);


    @Query(value = "{'trangThai': true}")
    List<TaiKhoan> getAlls();

    @Query(value = "{$or: [ {'name': ?0}, {'email': ?0}]}",
            sort = "{'trangThai': -1, 'name': 1}")
    Page<TaiKhoan> filter(String search, Pageable pageable);
}
