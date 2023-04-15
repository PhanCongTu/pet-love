package com.stc.petlove.dtos;

import lombok.Data;

@Data
public class DichVuDto {
    // mã dịch vụ không được trùng
    private String maDichVu;

    private String tenDichVu;

    // nội dung là html
    private String noiDung;
}
