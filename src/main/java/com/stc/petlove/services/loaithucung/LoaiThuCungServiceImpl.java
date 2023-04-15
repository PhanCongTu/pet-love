package com.stc.petlove.services.loaithucung;

import com.stc.petlove.dtos.LoaiThuCungDto;
import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.exceptions.DuplicateKeyException;
import com.stc.petlove.exceptions.NotFoundException;
import com.stc.petlove.repositories.LoaiThuCungRepository;
import com.stc.petlove.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoaiThuCungServiceImpl implements LoaiThuCungService {

    private final LoaiThuCungRepository loaiThuCungRepository;

    @Override
    public LoaiThuCung getLoaiThuCung(String id){
        return loaiThuCungRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Loại thú cưng có id là %s không tồn tại",id)));

    }

    @Override
    public LoaiThuCung create(LoaiThuCungDto dto){
        LoaiThuCung newLTC = new LoaiThuCung();
        newLTC.setTenLoaiThuCung(dto.getTenLoaiThuCung());
        newLTC.setMaLoaiThuCung(dto.getMaLoaiThuCung());
        newLTC.setTrangThai(true);
        if (loaiThuCungRepository.existsByMaLoaiThuCung(dto.getMaLoaiThuCung()))
            throw new DuplicateKeyException(String.format("Mã loại thú cưng %s đã tồn tại", dto.getMaLoaiThuCung()));
        return loaiThuCungRepository.save(newLTC);
    }

    @Override
    public LoaiThuCung update(String id, LoaiThuCungDto dto){
        Optional<LoaiThuCung> loaiThuCung = loaiThuCungRepository.findById(id);
        if (!loaiThuCung.isPresent())
            throw new NotFoundException(String.format("Loại thú cưng có id là %s không tồn tại", id));
        if (!loaiThuCung.get().getMaLoaiThuCung().equals(dto.getMaLoaiThuCung())){
            // Nếu mã không giống mã cũ thì kiểm tra mã mới đã tồn tại trong database hay chưa
            if (loaiThuCungRepository.existsByMaLoaiThuCung(dto.getMaLoaiThuCung())){
                throw new DuplicateKeyException(String.format("Mã loại thú cưng %s đã tồn tại", dto.getMaLoaiThuCung()));
            }
            loaiThuCung.get().setMaLoaiThuCung(dto.getMaLoaiThuCung());
        }
        loaiThuCung.get().setTenLoaiThuCung(dto.getTenLoaiThuCung());
        return loaiThuCungRepository.save(loaiThuCung.get());
    }
    @Override
    public Page<LoaiThuCung> filter(String search, int page, int size,
                                 String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return loaiThuCungRepository.findByMaLoaiThuCungContainingOrTenLoaiThuCungContainingAllIgnoreCase(search,search, pageable);
    }

    @Override
    public LoaiThuCung changeStatus(String id) {
        Optional<LoaiThuCung> loaiThuCung = loaiThuCungRepository.findById(id);
        if(!loaiThuCung.isPresent())
            throw new NotFoundException(String.format("Loại thú cưng có id %s không tồn tại", id));
        loaiThuCung.get().setTrangThai(!loaiThuCung.get().isTrangThai());
        return loaiThuCungRepository.save(loaiThuCung.get());
    }
}
