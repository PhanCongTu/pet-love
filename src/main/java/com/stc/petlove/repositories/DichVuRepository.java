package com.stc.petlove.repositories;

import com.stc.petlove.entities.DichVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DichVuRepository extends MongoRepository<DichVu, String> {
    boolean existsByMaDichVu(String maDichVu);

    Page<DichVu> findByMaDichVuContainingOrTenDichVuContainingAllIgnoreCase(String ma, String ten, Pageable pageable);
}
