package com.stc.petlove.controllers;

import com.stc.petlove.dtos.user.UpdateUserDto;
import com.stc.petlove.entities.TaiKhoan;
import com.stc.petlove.services.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/15/23
 * Time      : 9:58 AM
 * Filename  : UserController
 */
@RestController
@RequestMapping("/rest/v1/user")
public class UserController {
    private final UserService userService;

    private final int size = 10;
    private final String sort = "asc";
    private final String column = "trangThai";

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(@RequestParam String id, Principal principal){
        userService.changeStatus(id, principal);

        TaiKhoan taiKhoan = userService.getUser(id);
        return new ResponseEntity<>(String.format("User %s da duoc thay doi trang thai thanh %s"
                , taiKhoan.getName(), taiKhoan.isTrangThai()), HttpStatus.OK);
    }

    @PostMapping("/updateUser/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<TaiKhoan> update(@PathVariable String id,
                                           @RequestBody UpdateUserDto dto,
                                           Principal principal){
        return new ResponseEntity<>(userService.update(id, dto, principal), HttpStatus.OK);
    }

    @GetMapping("/{search}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TaiKhoan>> getAllUser(@RequestParam String search,
                                                     @RequestParam int page,
                                                     Principal principal){
        return new ResponseEntity<>(userService.filter(search,page,size,sort,column), HttpStatus.OK);
    }
}
