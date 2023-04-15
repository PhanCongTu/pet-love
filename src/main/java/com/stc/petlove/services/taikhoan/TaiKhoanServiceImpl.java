package com.stc.petlove.services.taikhoan;

import com.stc.petlove.dtos.RegisterDto;
import com.stc.petlove.dtos.UpdateTaiKhoanDto;
import com.stc.petlove.entities.TaiKhoan;
import com.stc.petlove.exceptions.DuplicateKeyException;
import com.stc.petlove.exceptions.NotFoundException;
import com.stc.petlove.repositories.TaiKhoanRepository;
import com.stc.petlove.utils.EnumRole;
import com.stc.petlove.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/15/23
 * Time      : 9:02 AM
 * Filename  : UserServiceImpl
 */
@Slf4j
@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {
    private final TaiKhoanRepository taiKhoanRepository;

    public TaiKhoanServiceImpl(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }


    @Override
    public Page<TaiKhoan> filter(String search, int page, int size,
                                 String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return taiKhoanRepository.findByNameContainingOrEmailContainingAllIgnoreCase(search,search, pageable);
    }

    @Override
    public TaiKhoan getUser(String id) {
        return taiKhoanRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("User with id %s does not exist",id)));
    }

    @Override
    public TaiKhoan getUserByEmail(String email) {
        return taiKhoanRepository.getUser(email)
                .orElseThrow(()-> new NotFoundException(String.format("User with email %s does not exist",email)));
    }


    @Override
    public TaiKhoan update(String id, UpdateTaiKhoanDto dto, Principal principal) {
        Optional<TaiKhoan> taiKhoan = taiKhoanRepository.findById(id);
        if(!taiKhoan.isPresent())
            throw new NotFoundException(String.format("Tài khoản có id %s không tồn tại", id));
        taiKhoan.get().setName(dto.getName());
//        taiKhoan.get().setEmail(dto.getEmail());
        taiKhoan.get().setPassword(dto.getPassword());
        taiKhoan.get().setDienThoai(dto.getDienThoai());
        return taiKhoanRepository.save(taiKhoan.get());
    }

    @Override
    public TaiKhoan changeStatus(String id) {
        Optional<TaiKhoan> taiKhoan = taiKhoanRepository.findById(id);
        if(!taiKhoan.isPresent())
               throw new NotFoundException(String.format("Tài khoản có id %s không tồn tại", id));
        taiKhoan.get().setTrangThai(!taiKhoan.get().isTrangThai());
        return taiKhoanRepository.save(taiKhoan.get());
    }

    @Override
    public TaiKhoan signup(RegisterDto registerDto) {
        TaiKhoan taiKhoan = new TaiKhoan();
        if(taiKhoanRepository.existsByEmail(registerDto.getEmail()))
            throw new DuplicateKeyException(String.format("User có email %s đã tồn tại", registerDto.getEmail()));
        taiKhoan.setName(registerDto.getName());
        taiKhoan.setEmail(registerDto.getEmail());
        taiKhoan.setRoles(Arrays.asList(EnumRole.ROLE_USER.name()));
        taiKhoan.setTrangThai(true);
        taiKhoan.setPassword(registerDto.getPassword());
        taiKhoan.setDienThoai(registerDto.getDienThoai());
        return taiKhoanRepository.save(taiKhoan);
    }
}
