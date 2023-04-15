package com.stc.petlove.services.taikhoan;

import com.stc.petlove.dtos.RegisterDto;
import com.stc.petlove.dtos.UpdateTaiKhoanDto;
import com.stc.petlove.entities.TaiKhoan;
import org.springframework.data.domain.Page;

import java.security.Principal;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/15/23
 * Time      : 9:01 AM
 * Filename  : UserService
 */
public interface TaiKhoanService {

    Page<TaiKhoan> filter(String search,
                      int page, int size, String sort, String column);

    TaiKhoan getUser(String id);

    TaiKhoan getUserByEmail(String email);

    TaiKhoan update(String id, UpdateTaiKhoanDto dto, Principal principal);

    TaiKhoan changeStatus(String id);

    TaiKhoan signup(RegisterDto registerDto);
}
