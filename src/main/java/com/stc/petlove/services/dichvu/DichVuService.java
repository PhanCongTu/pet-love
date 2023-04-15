package com.stc.petlove.services.dichvu;

import com.stc.petlove.dtos.DichVuDto;
import com.stc.petlove.entities.DichVu;
import com.stc.petlove.entities.embedded.GiaDichVu;
import org.springframework.data.domain.Page;

public interface DichVuService {

    DichVu getDichVu(String id);

    DichVu create(DichVuDto dto);
    DichVu addGiaDichVu(String maDichVu, GiaDichVu giaDichVu);


    DichVu update(String id, DichVuDto dto);

    DichVu changeStatus(String id);

    Page<DichVu> filter(String search, int page, int size,
                        String sort, String column);

}
