package com.stc.petlove.controllers;

import com.stc.petlove.dtos.DatChoDto;
import com.stc.petlove.dtos.DichVuDto;
import com.stc.petlove.entities.DatCho;
import com.stc.petlove.entities.DichVu;
import com.stc.petlove.entities.embedded.GiaDichVu;
import com.stc.petlove.entities.embedded.ThongTinDatCho;
import com.stc.petlove.services.datcho.DatChoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/dat-cho")
public class DatChoController {
    private final DatChoService datChoService;
    private final int size = 10;
    private final String sort = "asc";
    private final String column = "email";

    public DatChoController(DatChoService datChoService) {
        this.datChoService = datChoService;
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<DatCho>> getAllDatCho(@RequestParam(defaultValue = "") String search,
                                                     @RequestParam(defaultValue = "0") int page) {
        return new ResponseEntity<>(datChoService.filter(search, page, size, sort, column), HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DatCho> create(@RequestBody DatChoDto dto) {
        return new ResponseEntity<>(datChoService.create(dto), HttpStatus.CREATED);
    }

    // Thêm giá dịch vụ vào một dịch vụ đã có sẵn
    @PostMapping("/thong-tin-dat-cho/{idDatCho}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DatCho> createGiaDichVu(@PathVariable String idDatCho,
                                                  @RequestBody ThongTinDatCho thongTinDatCho) {
        return new ResponseEntity<>(datChoService.addThongTinDatCho(idDatCho, thongTinDatCho), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DatCho> update(@PathVariable String id,
                                         @RequestBody DatChoDto dto) {
        return new ResponseEntity<>(datChoService.update(id, dto), HttpStatus.OK);
    }

    @PostMapping("/change-status")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> changeStatus(@RequestParam String id) {
        datChoService.changeStatus(id);
        DatCho datCho = datChoService.getDatCho(id);
        return new ResponseEntity<>(String.format("Cho da dat co email nguoi dat la %s da duoc thay doi trang thai thanh %s"
                , datCho.getEmail(), datCho.isTrangThai()), HttpStatus.OK);
    }

    @PutMapping("/update-trang-thai/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<DatCho> updateTrangThai(@PathVariable String id,
                                                  @RequestParam int statusNumber) {
//        0 (hoac khac 1,2,3) : HUY
//        1 : DAT_CHO
//        2 : DANG_THUC_HIEN
//        3 : HOAN_THANH
        return new ResponseEntity<>(datChoService.updateTrangThai(id, statusNumber), HttpStatus.OK);
    }
}
