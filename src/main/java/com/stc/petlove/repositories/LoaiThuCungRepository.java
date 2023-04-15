package com.stc.petlove.repositories;

import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.entities.TaiKhoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoaiThuCungRepository extends MongoRepository<LoaiThuCung, String> {
    boolean existsByMaLoaiThuCung(String maLoaiThuCung);
    Page<LoaiThuCung> findByMaLoaiThuCungContainingOrTenLoaiThuCungContainingAllIgnoreCase(String ma, String ten, Pageable pageable);
}
