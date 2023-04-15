package com.stc.petlove.controllers;

import com.stc.petlove.dtos.UpdateTaiKhoanDto;
import com.stc.petlove.entities.TaiKhoan;
import com.stc.petlove.services.taikhoan.TaiKhoanService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/15/23
 * Time      : 9:58 AM
 * Filename  : UserController
 */
@RestController
@RequestMapping("/rest/v1/tai-khoan")
public class TaiKhoanController {
    private final TaiKhoanService taiKhoanService;

    private final int size = 10;
    private final String sort = "asc";
    private final String column = "name";

    public TaiKhoanController(TaiKhoanService taiKhoanService) {
        this.taiKhoanService = taiKhoanService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TaiKhoan>> getAllUser(@RequestParam(defaultValue = "") String search,
                                                     @RequestParam(defaultValue = "0") int page){
        return new ResponseEntity<>(taiKhoanService.filter(search,page,size,sort,column), HttpStatus.OK);
    }

    @PostMapping("/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(@RequestParam String id){
        taiKhoanService.changeStatus(id);

        TaiKhoan taiKhoan = taiKhoanService.getUser(id);
        return new ResponseEntity<>(String.format("User %s da duoc thay doi trang thai thanh %s"
                , taiKhoan.getName(), taiKhoan.isTrangThai()), HttpStatus.OK);
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<TaiKhoan> update(@PathVariable String id,
                                           @RequestBody UpdateTaiKhoanDto dto,
                                           Principal principal){
        return new ResponseEntity<>(taiKhoanService.update(id, dto, principal), HttpStatus.OK);
    }


}
