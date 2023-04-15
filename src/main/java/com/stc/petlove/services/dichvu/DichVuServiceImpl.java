package com.stc.petlove.services.dichvu;

import com.stc.petlove.dtos.DichVuDto;
import com.stc.petlove.entities.DichVu;
import com.stc.petlove.entities.embedded.GiaDichVu;
import com.stc.petlove.exceptions.DuplicateKeyException;
import com.stc.petlove.exceptions.NotFoundException;
import com.stc.petlove.repositories.DichVuRepository;
import com.stc.petlove.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DichVuServiceImpl implements DichVuService{
    private final DichVuRepository dichVuRepository;
    @Override
    public DichVu getDichVu(String id){
        return dichVuRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Dịch vụ có id là %s không tồn tại",id)));

    }
    @Override
    public DichVu create(DichVuDto dto){
        if (dichVuRepository.existsByMaDichVu(dto.getMaDichVu()))
            throw new DuplicateKeyException(String.format("Mã dịch vụ %s đã tồn tại", dto.getMaDichVu()));
        DichVu dichVu = new DichVu();
        dichVu.setMaDichVu(dto.getMaDichVu());
        dichVu.setTenDichVu(dto.getTenDichVu());
        dichVu.setNoiDung(dto.getNoiDung());
        dichVu.setTrangThai(true);
        return dichVuRepository.save(dichVu);
    }

    @Override
    public DichVu addGiaDichVu(String idDichVu, GiaDichVu giaDichVu){
        Optional<DichVu> dichVu = dichVuRepository.findById(idDichVu);
        if (!dichVu.isPresent())
            throw new NotFoundException(String.format("Dịch vụ có id là %s không tồn tại", idDichVu));
        dichVu.get().getGiaDichVus().add(giaDichVu);
        return dichVuRepository.save(dichVu.get());
    }
    @Override
    public DichVu update(String id, DichVuDto dto){
        Optional<DichVu> dichVu = dichVuRepository.findById(id);
        if (!dichVu.isPresent())
            throw new NotFoundException(String.format("Dịch vụ có id là %s không tồn tại", id));
        if (!dichVu.get().getMaDichVu().equals(dto.getMaDichVu())){
            // Nếu mã không giống mã cũ thì kiểm tra mã mới đã tồn tại trong database hay chưa
            if (dichVuRepository.existsByMaDichVu(dto.getMaDichVu())){
                throw new DuplicateKeyException(String.format("Mã dịch vụ %s đã tồn tại", dto.getMaDichVu()));
            }
            dichVu.get().setMaDichVu(dto.getMaDichVu());
        }
        dichVu.get().setTenDichVu(dto.getTenDichVu());
        dichVu.get().setNoiDung(dto.getNoiDung());
        return dichVuRepository.save(dichVu.get());
    }
    @Override
    public DichVu changeStatus(String id) {
        Optional<DichVu> dichVu = dichVuRepository.findById(id);
        if(!dichVu.isPresent())
            throw new NotFoundException(String.format("Dịch vụ có id %s không tồn tại", id));
        dichVu.get().setTrangThai(!dichVu.get().isTrangThai());
        return dichVuRepository.save(dichVu.get());
    }
    @Override
    public Page<DichVu> filter(String search, int page, int size,
                               String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return dichVuRepository.findByMaDichVuContainingOrTenDichVuContainingAllIgnoreCase(search,search, pageable);
    }
}
