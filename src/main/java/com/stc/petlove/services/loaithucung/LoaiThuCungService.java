package com.stc.petlove.services.loaithucung;

import com.stc.petlove.dtos.LoaiThuCungDto;
import com.stc.petlove.entities.LoaiThuCung;
import org.springframework.data.domain.Page;

public interface LoaiThuCungService {


    LoaiThuCung getLoaiThuCung(String id);

    LoaiThuCung create(LoaiThuCungDto dto);

    LoaiThuCung update(String id, LoaiThuCungDto dto);

    Page<LoaiThuCung> filter(String search, int page, int size, String sort, String column);

    LoaiThuCung changeStatus(String id);
}
