package com.stc.petlove.services.user;

import com.stc.petlove.dtos.RegisterDto;
import com.stc.petlove.dtos.user.UpdateUserDto;
import com.stc.petlove.entities.TaiKhoan;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/15/23
 * Time      : 9:01 AM
 * Filename  : UserService
 */
public interface UserService {

    Page<TaiKhoan> filter(String search,
                      int page, int size, String sort, String column);

    TaiKhoan getUser(String id);

    TaiKhoan getUserByEmail(String email);

    TaiKhoan update(String id, UpdateUserDto dto, Principal principal);

    TaiKhoan changeStatus(String id, Principal principal);

    TaiKhoan signup(RegisterDto registerDto);
}
