package com.stc.petlove.services.datcho;

import com.stc.petlove.dtos.DatChoDto;
import com.stc.petlove.entities.DatCho;
import com.stc.petlove.entities.embedded.ThongTinDatCho;
import com.stc.petlove.exceptions.NotFoundException;
import com.stc.petlove.repositories.DatChoRepository;
import com.stc.petlove.utils.EnumTrangThaiDatCho;
import com.stc.petlove.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatChoServiceImpl implements DatChoService {
    private final DatChoRepository datChoRepository;

    @Override
    public DatCho getDatCho(String id) {
        return datChoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Chỗ đã đặt có id là %s không tồn tại", id)));

    }

    @Override
    public DatCho create(DatChoDto dto) {
        DatCho datCho = new DatCho();
        datCho.setEmail(dto.getEmail());
        datCho.setThoiGian(dto.getThoiGian());
        datCho.setCanDan(dto.getCanDan());
        datCho.setTrangThai(true);
        datCho.setTrangThaiDatCho(EnumTrangThaiDatCho.DAT_CHO.name());
        return datChoRepository.save(datCho);
    }

    @Override
    public DatCho addThongTinDatCho(String idDatCho, ThongTinDatCho thongTinDatCho) {
        Optional<DatCho> datCho = datChoRepository.findById(idDatCho);
        if (!datCho.isPresent())
            throw new NotFoundException(String.format("Chỗ đã đặt có id là %s không tồn tại", idDatCho));
        datCho.get().getThongTinDatChos().add(thongTinDatCho);
        return datChoRepository.save(datCho.get());
    }

    @Override
    public DatCho update(String id, DatChoDto dto) {
        Optional<DatCho> datCho = datChoRepository.findById(id);
        if (!datCho.isPresent())
            throw new NotFoundException(String.format("Chỗ đã đặt có id là %s không tồn tại", id));
        datCho.get().setEmail(dto.getEmail());
        datCho.get().setThoiGian(dto.getThoiGian());
        datCho.get().setCanDan(dto.getCanDan());
        return datChoRepository.save(datCho.get());
    }

    @Override
    public DatCho updateTrangThai(String id, int statusNumber) {
        Optional<DatCho> datCho = datChoRepository.findById(id);
        if (!datCho.isPresent())
            throw new NotFoundException(String.format("Chỗ đã đặt có id là %s không tồn tại", id));
        switch (statusNumber){
            case 1:
                datCho.get().setTrangThaiDatCho(EnumTrangThaiDatCho.DAT_CHO.name());
                break;
            case 2:
                datCho.get().setTrangThaiDatCho(EnumTrangThaiDatCho.DANG_THUC_HIEN.name());
                break;
            case 3:
                datCho.get().setTrangThaiDatCho(EnumTrangThaiDatCho.HOAN_THANH.name());
                break;
            default:
                datCho.get().setTrangThaiDatCho(EnumTrangThaiDatCho.HUY.name());
        }
        return datChoRepository.save(datCho.get());
    }

    @Override
    public DatCho changeStatus(String id) {
        Optional<DatCho> datCho = datChoRepository.findById(id);
        if(!datCho.isPresent())
            throw new NotFoundException(String.format("Chỗ đã đặt có id %s không tồn tại", id));
        datCho.get().setTrangThai(!datCho.get().isTrangThai());
        return datChoRepository.save(datCho.get());
    }

    @Override
    public Page<DatCho> filter(String search, int page, int size,
                               String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return datChoRepository.findByEmailContainingIgnoreCase(search, pageable);
    }
}
