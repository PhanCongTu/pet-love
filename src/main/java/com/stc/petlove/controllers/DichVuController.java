
package com.stc.petlove.controllers;

import com.stc.petlove.dtos.DichVuDto;
import com.stc.petlove.entities.DichVu;
import com.stc.petlove.entities.embedded.GiaDichVu;
import com.stc.petlove.services.dichvu.DichVuService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/dich-vu")
public class DichVuController {
    private final DichVuService dichVuService;
    private final int size = 10;
    private final String sort = "asc";
    private final String column = "tenDichVu";
    public DichVuController(DichVuService dichVuService) {
        this.dichVuService = dichVuService;
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<DichVu>> getAllDichVu(@RequestParam(defaultValue = "") String search,
                                                     @RequestParam(defaultValue = "0") int page){
        return new ResponseEntity<>(dichVuService.filter(search,page,size,sort,column), HttpStatus.OK);
    }
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DichVu> create(@RequestBody DichVuDto dto){
        return new ResponseEntity<>(dichVuService.create(dto), HttpStatus.CREATED);
    }

    // Thêm giá dịch vụ vào một dịch vụ đã có sẵn
    @PostMapping("/gia-dich-vu/{maDichVu}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DichVu> createGiaDichVu(@PathVariable String maDichVu,
                                                  @RequestBody GiaDichVu giaDichVu){
        return new ResponseEntity<>(dichVuService.addGiaDichVu(maDichVu, giaDichVu), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DichVu> update(@PathVariable String id,
                                         @RequestBody DichVuDto dto){
        return new ResponseEntity<>(dichVuService.update(id, dto), HttpStatus.OK);
    }
    @PostMapping("/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatus(@RequestParam String id){
        dichVuService.changeStatus(id);
        DichVu dichVu = dichVuService.getDichVu(id);
        return new ResponseEntity<>(String.format("Dich vu %s da duoc thay doi trang thai thanh %s"
                , dichVu.getTenDichVu(), dichVu.isTrangThai()), HttpStatus.OK);
    }
}
