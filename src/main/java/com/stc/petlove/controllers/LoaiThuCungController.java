package com.stc.petlove.controllers;

import com.stc.petlove.dtos.LoaiThuCungDto;
import com.stc.petlove.entities.LoaiThuCung;
import com.stc.petlove.services.loaithucung.LoaiThuCungService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/loai-thu-cung")
public class LoaiThuCungController {
    private final LoaiThuCungService loaiThuCungService;

    private final int size = 10;
    private final String sort = "asc";
    private final String column = "tenLoaiThuCung";
    public LoaiThuCungController(LoaiThuCungService loaiThuCungService) {
        this.loaiThuCungService = loaiThuCungService;
    }
    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<LoaiThuCung>> getAllLoaiThuCung(@RequestParam(defaultValue = "") String search,
                                                     @RequestParam(defaultValue = "0") int page){
        return new ResponseEntity<>(loaiThuCungService.filter(search,page,size,sort,column), HttpStatus.OK);
    }
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LoaiThuCung> create(@RequestBody LoaiThuCungDto dto){
        return new ResponseEntity<>(loaiThuCungService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LoaiThuCung> update(@PathVariable String id,
                                              @RequestBody LoaiThuCungDto dto){
        return new ResponseEntity<>(loaiThuCungService.update(id, dto), HttpStatus.OK);
    }

    @PostMapping("/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(@RequestParam String id){
        loaiThuCungService.changeStatus(id);
        LoaiThuCung loaiThuCung = loaiThuCungService.getLoaiThuCung(id);
        return new ResponseEntity<>(String.format("Loại thú cưng  %s da duoc thay doi trang thai thanh %s"
                , loaiThuCung.getTenLoaiThuCung(), loaiThuCung.isTrangThai()), HttpStatus.OK);
    }
}
