package com.stc.petlove.services.datcho;

import com.stc.petlove.dtos.DatChoDto;
import com.stc.petlove.entities.DatCho;
import com.stc.petlove.entities.embedded.ThongTinDatCho;
import org.springframework.data.domain.Page;

public interface DatChoService {
    DatCho getDatCho(String id);

    DatCho create(DatChoDto dto);

    DatCho addThongTinDatCho(String idDatCho, ThongTinDatCho thongTinDatCho);

    DatCho update(String id, DatChoDto dto);

    DatCho updateTrangThai(String id, int statusNumber);

    DatCho changeStatus(String id);

    Page<DatCho> filter(String search, int page, int size,
                        String sort, String column);
}
